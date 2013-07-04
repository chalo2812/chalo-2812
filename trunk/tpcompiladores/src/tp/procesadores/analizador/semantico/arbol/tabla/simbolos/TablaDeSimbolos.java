package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;

import java.util.ArrayList;
import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class TablaDeSimbolos extends ClaseNodo 
{
	private static final long serialVersionUID = 1L;
	public List<FilaTabla> entradas = new ArrayList<FilaTabla>();
	public List<Metodo> metodos = new ArrayList<Metodo>();
	public TablaDeSimbolos padre; 

	public void add(FilaTabla entrada) {
		this.entradas.add(entrada);
	}

	public void remove(FilaTabla entrada) {
		this.entradas.remove(entrada);
	}
	
	public void addMethod (Metodo metodo) {
		this.metodos.add(metodo);
	}

	public void removeMethod (Metodo metodo) {
		this.metodos.remove(metodo);
	}
	
	public void insertarListaVariables(ListaVariables lista){
		if( lista!=null)
		{
			 List<ElementoIdentificador> listaAux= new ArrayList<ElementoIdentificador>();
			 int index = 0;
			 ElementoIdentificador elementoAux = new ElementoIdentificador();
			 
		     while ( lista.identificadores.iterator().hasNext() && (index <= (lista.identificadores.size()-1))) 
		     {
		    	 elementoAux = lista.identificadores.get(index);
		    	 if ( elementoAux.getTipo() == null){ 
		    		 listaAux.add(elementoAux);
		    	 }else{
		    		 if ( listaAux.isEmpty()) { 
		    			 FilaTabla fila = new FilaTabla(true,elementoAux.getLexema(),elementoAux.getTipo(),elementoAux.getValor());
		    			 this.entradas.add(fila);
		    		 }else{
		    			 int indexAux = 0;
		    			 String tipo = elementoAux.getTipo();
		    			 listaAux.add(elementoAux);
		    			 while( indexAux <= (listaAux.size() -1)){
		    				 FilaTabla fila = new FilaTabla(true,listaAux.get(indexAux).getLexema(),tipo,listaAux.get(indexAux).getValor());
			    			 this.entradas.add(fila);
			    			 indexAux++;
		    			 }
		    			 listaAux= new ArrayList<ElementoIdentificador>();
		    		 }
		    	 }
		    	 index++;
		     }
		}
	}
	
	public void insertarListaConstantes(ListaConstantes lista){
	     if ( lista != null )
	     {	 
			 List<ElementoIdentificador> listaAux= new ArrayList<ElementoIdentificador>();
			 int index = 0;
			 ElementoIdentificador elementoAux = new ElementoIdentificador();
			 
		     while ( lista.identificadores.iterator().hasNext() && (index <= (lista.identificadores.size()-1))) 
		     {
		    	 elementoAux = lista.identificadores.get(index);
		    	 if ( elementoAux.getTipo() == null){ 
		    		 listaAux.add(elementoAux);
		    	 }else{
		    		 if ( listaAux.isEmpty()) { 
		    			 FilaTabla fila = new FilaTabla(false,elementoAux.getLexema(),elementoAux.getTipo(),elementoAux.getValor());
		    			 this.entradas.add(fila);
		    		 }else{
		    			 int indexAux = 0;
		    			 String tipo = elementoAux.getTipo();
		    			 listaAux.add(elementoAux);
		    			 while( indexAux <= (listaAux.size() -1)){
		    				 FilaTabla fila = new FilaTabla(false,listaAux.get(indexAux).getLexema(),tipo,listaAux.get(indexAux).getValor());
			    			 this.entradas.add(fila);
			    			 indexAux++;
		    			 }
		    			 listaAux= new ArrayList<ElementoIdentificador>();
		    		 }
		    	 }
		    	 index++;
		     }
	     }
	}
	
	public void mostrarTabla(String identacion){
		int index = 0; 
		System.out.println(identacion + "TipoVar\t|   Id\t|T. Dato\t| Valor\t  ");
		while ( index <= (this.entradas.size() -1 ))
		{ 
			if ( this.entradas.get(index).esVariable())
			{
				System.out.println(identacion +  "var\t| "+ this.entradas.get(index).getId() + "\t| " +
								this.entradas.get(index).getTipo() + "\t| " +
								this.entradas.get(index).getValor());
			}else{ 
				System.out.println(identacion +  "const\t| " + this.entradas.get(index).getId() + "\t| " +
						this.entradas.get(index).getTipo() + "\t| " +
						this.entradas.get(index).getValor());
			}
			index++; 
		}
		if (this.metodos.size()!=0)
		{
			System.out.println(identacion + "TipoMetodo\t| Nom\t| Params\t| Retorno\t");
			index = 0; 
			while ( index <= (this.metodos.size()-1))
			{
				if(this.metodos.get(index).esFuncion())
				{ 
					System.out.println(identacion + "fun\t| "+ this.metodos.get(index).getNombre() + "\t| " +
							this.metodos.get(index).getListaParametrosAsString() + "\t| " + this.metodos.get(index).getTipo());
				}else 
				{
					System.out.println(identacion + "proc\t| "+ this.metodos.get(index).getNombre()+  "\t| "
							+ this.metodos.get(index).getListaParametrosAsString() +"\t| " +this.metodos.get(index).getTipo());
				}
			index++;	
			}
		}
	}

	public boolean existeId(String id) 
	{
		 int index = 0;
		 boolean existe = false;
	     while ((index <= (entradas.size()-1)) && !existe) 
	     {	  
	    	 if (entradas.get(index).getId().equals(id))
	    		 	existe = true; 
	    	 index++;
	     }	
	     if (!existe && (this.getPadre()!=null)){
	    	 existe = this.getPadre().existeId(id);
	     }
	     return existe;
	}
	

	public boolean existeMetodo(String palabra)
	{
		int index = 0; 
		boolean existe = false; 
		while ((index <= (metodos.size()-1)) && !existe ){ 
			if ( metodos.get(index).getNombre().equals(palabra))
					existe = true; 
			index++;
		}
		if ((!existe) && (this.getPadre()!=null)){ 
			existe = this.getPadre().existeMetodo(palabra);
		}
		return existe; 
	}

	public Metodo getMetodo(String palabra)
	{
		int index=0;
		boolean encontrado = false; 
		while (( index <= (metodos.size()-1) ) && !encontrado)
		{ 
			if(metodos.get(index).getNombre().equals(palabra))
				encontrado = true; 
			else
				index++; 
		}
		if ((!encontrado) && ( this.getPadre()!=null))
			return this.getPadre().getMetodo(palabra);
		else 
			return this.metodos.get(index);
	}

	public TablaDeSimbolos getPadre() {
		return this.padre;
	}

	public void setPadre(TablaDeSimbolos padre) {
		this.padre = padre;
	}
	
	
	public boolean esVariable(String id)
	{
		int index = 0;
		boolean encontrado = false, variable = false;
		while (!encontrado && (index <= (this.entradas.size()-1))) 
	    {	  
	    	if (this.entradas.get(index).getId().equals(id))
	    		encontrado = true; 
	    	else 
	    		index++;
	    }
		if(!encontrado){
			if (this.getPadre() != null){ 
				variable = this.getPadre().esVariable(id);
			}else{
				System.out.println("ID No definido");
			}
		}else{ 
			variable = this.entradas.get(index).esVariable();
		}
    	return variable;
	}	
	
	
	//Verifica que el Metodo main este presente solo en esta tabla, no pregunta por el padre. 
	public boolean verificiarMetodoMain(){
		int index = 0; 
		boolean encontrado = false;
	    while ( encontrado == false && (index <= (this.metodos.size()-1))) 
	    {	  
	    	if ( this.metodos.get(index).getNombre().equals("principal"))
	    	{ 
	    		encontrado = true; 
	    	}
	    	index++;
	    }
		return encontrado;
	}

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		int index = 0; 
        StringBuilder strbldr = new StringBuilder();
		while ( index <= (this.entradas.size() -1 ))
		{ 
			if ( this.entradas.get(index).esVariable())
			{		       
				if (this.entradas.get(index).getValor() == null)
				{
					strbldr.append(this.entradas.get(index).getId()+"\t").append("dw ?").append("\n");
				}else
				{
					int valor = Integer.parseInt(this.entradas.get(index).getValor());
					valor++;
					strbldr.append(this.entradas.get(index).getId()+"\t").append("dw ").append(Integer.toString(valor)).append(" dup (?)").append("\n");
					strbldr.append("MOV\t").append("["+this.entradas.get(index).getId()+"]").append(","+this.entradas.get(index).getValor()).append("\n"); 
				}
			}
			else
			{ 
				strbldr.append(this.entradas.get(index).getId()+"\t").append("dw ").append(this.entradas.get(index).getValor()).append("\n");
			}
			index++;
		}
		codigo.setCodigo(codigo.getCodigo() + "\n" +  strbldr.toString() );
		return codigo;
	}

	public String generarPUSH()
	{
		int index = 0; 
        StringBuilder strbldr = new StringBuilder();
		while ( index <= (this.entradas.size() -1 ))
		{ 
			if ( this.entradas.get(index).esVariable())
			{		       
				if (this.entradas.get(index).getValor() == null)
				{
					strbldr.append("PUSH\t"+this.entradas.get(index).getId()+"\n");
				}
			}else
			{ 
				strbldr.append("PUSH\t"+this.entradas.get(index).getId()+"\n");
			}
			index++;
		}
		return strbldr.toString();
	}

	
	public String generarPOP()
	{
		int index = this.entradas.size()-1; 
        StringBuilder strbldr = new StringBuilder();
		while ( index >= 0)
		{ 
			if ( this.entradas.get(index).esVariable())
			{		       
				if (this.entradas.get(index).getValor() == null)
				{
					strbldr.append("POP\t"+this.entradas.get(index).getId()+"\n");
				}
			}else
			{ 
				strbldr.append("POP\t"+this.entradas.get(index).getId()+"\n");
			}
			index--;
		}
		return strbldr.toString();	
	}

	@Override
	public TablaDeSimbolos acceptTSVisitor(TablaSimbolosVisitor visitor) {
		return this;
	}
	
	
	public String getContexto()
	{
		if (!this.metodos.isEmpty())
		{
			return this.metodos.get(this.metodos.size()-1).getNombre();
		}else
		{
			if ( this.padre!=null) 
				return this.padre.getContexto();
			else 
				return null;
		}
//			if ( this.getPadre() != null )
//				return this.padre.getContexto();
//			else 
	}

	public boolean esParametroDelContexto(String identificador) 
	{
		boolean retorno = false;
		if ((this.metodos!=null) && (this.metodos.size()>0))
		{
			int indexMetodos=0;
			boolean continuar=true;
			while (( indexMetodos<=(this.metodos.size()-1)) && continuar)
			{
				Metodo metodoAux = this.metodos.get(indexMetodos);
				List<Parametro> parametros = metodoAux.getParametros();
				int index = 0; 
				while((index<=(parametros.size()-1)) && continuar)
				{
					if(parametros.get(index).getLexema().equals(identificador)){
						continuar = false;
						retorno = true;
					}
					index++;
				}
				indexMetodos++;
			}
		}
		if ((!retorno ) && ( this.padre != null))
		{
			retorno = this.getPadre().esParametroDelContexto(identificador);
		}
		return retorno;
	}
	
}

