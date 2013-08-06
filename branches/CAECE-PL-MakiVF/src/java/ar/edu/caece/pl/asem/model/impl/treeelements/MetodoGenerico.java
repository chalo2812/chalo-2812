package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.Ambiente;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public abstract class MetodoGenerico extends AbstractElement{

	private int returnType;	// Tipo de dato de la respuesta de un metodo
	
	public int getReturnType() {
		return returnType;
	}
	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + returnType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetodoGenerico other = (MetodoGenerico) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		if (returnType != other.returnType)
			return false;
		return true;
	}
	
	/**3º) Guardar el base (frame) pointer y setea el nuevo
	 * 4º) //Guardar el estado del CPU, osea los registros {*}
	 * 5º) Alojar variables locales a la función (en el stack)
	 * 6º) (es el código de la función o procedimiento)
	 * (paso 1 y 2 son pasar parametros al stack de der a izq y llamar a la funcion)
	------------------------------------------------------------------------------------------
	{*} Guardar o no guardar el estado del CPU???:
	Ejemplo del estado del CPU:
			AX=0000 BX=0000 CX=0000 DX=0000 SP=FFEE BP=0000 SI=0000 DI=0000
			DS=1779 ES=1779 SS=1779 CS=1779 IP=0100 NV UP EI PL NZ NA PO NC
			La última secuencia decaracteres (NV UP EI PL NZ NA PO NC) muestra el contenido del registro de estado
	
		Si lo guardo es para poder restaurarlo cuando termina la función. 
		Cómo debo restaurarlo?
		Si mi función es matemática, esperaría que al ejecutarla ya me afecte el carry, overflow, Zero, etc, en lugar
		de tener que hacer un TEST a resultado. 
		"*resultado*" -> dónde lo guardo?
		Si lo guardo en el Stack, tengo que tener cuidado de no borrarlo cuando libere el frame de la función.
		Si lo guardo en AX, tengo que tener cuidado de no pisarlo si decido restaurar los registros del CPU.
		
		En los ejemplos que vi de las calling conventions, a veces devuelven el valor de la función en AX, pero
		AX es parte del estado del CPU; entonces:
		Por ahora decidimos no guardar los registros del CPU y no restaurarlos al final.
	------------------------------------------------------------------------------------------
	
	 * @param localVariablesNbr - NOTA:: Los arreglos se guardan completos en el stack
	 * => Se debe saber su tamaño en tiempo de compilación!! Ja!
	 * @return el código de inicialización de la llamada
	 */
	public void initStackFrame(StringBuilder sb) {
		sb.append(TAB).append("push bp").append(ENTER);		//paso 3º
		sb.append(TAB).append("mov bp, sp").append(ENTER);	//paso 3ºbis
		//.append("; push <registros_CPU:AX,BX,CX,DX,SP,BP,SI,DI,DS,ES,SS,CS,IP,NVUPEIPLNZNAPONC>"); //paso 4º
		//paso 5º -> Se hace en el nodo DECLARACIONES
		//paso 6º -> Se hace en el nodo CODIGO
	}
	
	/**
	 * Se investigaron los estandares de calling conventions llamados "_stdcall" y "_cdecl"
	 * Utilizaremos "_stdcall" donde la funcion limpia sus variables locales y los parámetros
	 * que guardó el llamador (así no se repite en n lugares donde se llame).
	 * Se dejará el resultado de las funciones en AX 
	 * 
	 * 6º) (es el código de la función o procedimiento)
	 * 7º) Desalojar variables locales a la función
	 * 8º) //Restaurar el estado del CPU {* ver arriba en el método de inicialización}
	 * 9º) Restaurar el base (frame) pointer
	 * 10º) Limpiar parámetros del stack
	 * 11º) Retornar de la función o procedimiento (Se modifica el instruction Pointer IP)
	 * 
	 * @param localVariablesNbr - NOTA:: Los arreglos se guardan completos en el stack
	 * @return
	 */
	public void finalizeStackFrame (StringBuilder sb) {
		
		Ambiente env = SymbolTable.getInstance().getAmbiente(this.getEnvName());
		
		int offset = 0;
		for(Arreglo arr : env.getArreglos().values()) {
			offset += REGISTER_SIZE * arr.getLength();
		}
		offset += REGISTER_SIZE * env.getVariables().values().size();
		
		if(offset > 0) {			
			sb.append(TAB).append("add sp, ").append(offset).append(ENTER);		//paso 7º
		}

		sb.append(TAB).append("pop bp").append(ENTER);							//paso 9º
		
		offset = REGISTER_SIZE * (env.getParametros().size());
		if(offset > 0) {
			sb.append(TAB).append("add sp, ").append(offset).append(ENTER);		//paso 10º
		}
		//Paso 11º (ret) se realiza después de dejar el valor de retorno en ax
	}
	
	@Override
	public void compile (StringBuilder sb) {
		if (ErrorManager.debugEnabled) sb.append(TAB + "; Proc-Init" + ENTER);
		
		/* this.getChildren().get(0) = Parametros
		 * this.getChildren().get(1) = Declaraciones
		 * this.getChildren().get(2) = ReturnType
		 * this.getChildren().get(3) = Cuerpo		*/
		
		String procedureName = this.getName();
		sb.append(procedureName + " proc near" + ENTER);
		
		if (!"main".equals(this.getName())) {
			this.initStackFrame(sb);
		}
		
		Parametros params = (Parametros) this.getChildren().get(0);
		params.compile(sb);
		
		Declaraciones declarations = (Declaraciones) this.getChildren().get(1);
		declarations.compile(sb);


		Cuerpo body = (Cuerpo) this.getChildren().get(3);
		body.compile(sb);
		
		//Para la función tengo que agarrar al último y guardar el resutlado en el SP
		if (!"main".equals(this.getName())) {
			this.finalizeStackFrame(sb);
			ReturnType ret = (ReturnType) this.getChildren().get(2);
			
			if(SymbolTable.VOID != ret.getType()) {
				// DeletedTemporalGenerator.getInstance().checkPointActualMethod(this); // Guardo el Metodo actual, para luego guardar el ambiente en la pila
				//¿ buscar el resultado y dejarlo en AX
				AbstractElement last = body.getChildren().get(body.getChildren().size()-1);
			}
			sb.append(TAB).append("ret ").append(ENTER);	//paso 11º
		} else {
			sb.append(TAB + "int 21h" + ENTER);
		}
		sb.append(this.getName() + " endp" + ENTER);
	}

}
