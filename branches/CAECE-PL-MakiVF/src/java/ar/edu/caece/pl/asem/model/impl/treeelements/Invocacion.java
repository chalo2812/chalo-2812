package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.temp.TemporalGenerator;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class Invocacion extends AbstractElement {

	public Invocacion() {
		this.label = "CALL";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Invocacion))
			return false;
		Invocacion other = (Invocacion) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {
		String parameterName = new String();
		this.place = TemporalGenerator.getInstance().getActualTemporal();
		
		// Guardo el Ambiente actual, en la Pila
		
		/*
		 * Estructura de un Procedimiento o Funcion:
		 * 
		 * this.getChildren().get(0) = Parametros 
		 * this.getChildren().get(1) = Declaraciones
		 * this.getChildren().get(2) = ReturnType
		 * this.getChildren().get(3) = Cuerpo
		 * 
		 */
		
		MetodoGenerico mg = TemporalGenerator.getInstance().getActualMethod();
		if (mg instanceof Funcion) {
			// TODO: 1) Guardar en la pila las variables temporales
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando variables temporales..." + ENTER);
			
			// TODO: 2) Guardar en la pila, las variables locales
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando variables locales..." + ENTER);
			
			
			// TODO: 3) Guardar en la pila, Estado de Maquina
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando Estado de maquina..." + ENTER);
			
			// TODO: 4) Guardar en la pila, Control Link (Referencia al llamador) // TOM: Lo tenia anotado en la carpeta, no le encuentro el sentido..
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando Control Link..." + ENTER);
			
			// TODO: 5) Guardar en la pila, los valores de Params Actuales
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando Valores de params actuales..." + ENTER);
			
		}else if (mg instanceof Procedimiento){
			// TODO: 1) Guardar en la pila las variables temporales
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando variables temporales..." + ENTER);
			
			// TODO: 2) Guardar en la pila, las variables locales
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando variables locales..." + ENTER);
			
			
			// TODO: 3) Guardar en la pila, Estado de Maquina
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando Estado de maquina..." + ENTER);
			
			// TODO: 4) Guardar en la pila, Control Link (Referencia al llamador) // TOM: Lo tenia anotado en la carpeta, no le encuentro el sentido..
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando Control Link..." + ENTER);
			
			// TODO: 5) Guardar en la pila, los valores de Params Actuales
			if(ErrorManager.debugEnabled) sb.append("; Call-Guardando Valores de params actuales..." + ENTER);
			
		}
		
		
		/*
		 * 6) Guardar en la pila, parametros de la llamada a invocar
		 * 
		 * Si llamo a un Parametro:
		 * this.getChildren().get(0) = Procedimiento
		 * this.getChildren().get(1) = Parametros
		 * 
		 * Si llamo a una Funcion:
		 * this.getChildren().get(0) = Funcion
		 * this.getChildren().get(1) = Parametros
		 */
		for (AbstractElement parametro : this.getChildren().get(1).getChildren()) {

			if (parametro instanceof NumeroInt){
				parameterName = String.valueOf(((NumeroInt) parametro).getValue());
			} else if (parametro instanceof NumeroNat) {
				parameterName = String.valueOf(((NumeroNat) parametro).getValue());
			} else if (parametro instanceof Parametro) {
				parameterName = "[bp+"+ ((Parametro) parametro).getMemoryInStack() +']';
			} else {
				parametro.compile(sb);
				parameterName = TemporalGenerator.getInstance().getActualTemporal();
			}
			// envia el parametro de la funcion por la pila
			sb.append(TAB + "push " + parameterName + ENTER);
			TemporalGenerator.getInstance().releaseTemporal();
		}

		// LLamada a la funcion
		String functionName = this.getChildren().get(0).getName();
		sb.append(TAB + "CALL " + functionName + ENTER);
		
		// Libera el espacio utilizado por las variables locales por el metodo
		SymbolTable.getInstance().getAmbiente(functionName).getVariablesSize();
		if(SymbolTable.getInstance().getAmbiente(functionName).getVariablesSize()>0){
			int spaceForLocalVariables = SymbolTable.getInstance().getAmbiente(functionName).getVariablesSize() * 2;
			sb.append("\t add sp, " + spaceForLocalVariables + "\n");
		}
		
		// Devolucion de la funcion
		if(this.getChildren().get(0) instanceof Funcion){
		sb.append(TAB + "mov " + this.place + ",ax" + ENTER);
		}
		
		// Recupero de ambiente
		
		mg = TemporalGenerator.getInstance().getActualMethod();
		if (mg instanceof Funcion) {
			// TODO: 1) Si llamé a un metodo, debo recuperar su valor retornado...
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Valor Retornado..." + ENTER);
			
			// TODO: 2) Recuperar Valores de Params actuales
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando variables locales..." + ENTER);
			
			// TODO: 3) Recuperar Cotrol Link
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Estado de maquina..." + ENTER);
			
			// TODO: 4) Recuperar Estado de Maquina
			if(ErrorManager.debugEnabled) sb.append("; CallBacl-Recuperando Control Link..." + ENTER);
			
			// TODO: 5) Recuperar Variables Locales
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Variables Locales..." + ENTER);
			
			// TODO: 6) Recuperar Temporales
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Temporales..." + ENTER);
			
		} else if (mg instanceof Procedimiento){
//			// TODO: 1) Si llamé a un metodo, debo recuperar su valor retornado...
//			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Valor Retornado..." + ENTER);
			
			// TODO: 2) Recuperar Valores de Params actuales
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando variables locales..." + ENTER);
			
			// TODO: 3) Recuperar Cotrol Link
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Estado de maquina..." + ENTER);
			
			// TODO: 4) Recuperar Estado de Maquina
			if(ErrorManager.debugEnabled) sb.append("; CallBacl-Recuperando Control Link..." + ENTER);
			
			// TODO: 5) Recuperar Variables Locales
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Variables Locales..." + ENTER);
			
			// TODO: 6) Recuperar Temporales
			if(ErrorManager.debugEnabled) sb.append("; CallBack-Recuperando Temporales..." + ENTER);
			
		}
	}
}
