package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;

import java.util.ArrayList;
import java.util.List;

public class Metodo {

	private boolean esFuncion;
	private String nombre;
	private String tipo;
	private boolean esAdelantado;
	private List<Parametro> parametros = new ArrayList<Parametro>();

	//Metodo null
	public Metodo(){
		this.setEsFuncion(false);
		this.setNombre(null);
		this.setTipo(null);
		this.setEsAdelantado(false);
	}
		
	//Metodo
	public Metodo( boolean esFuncion, String nombre, String tipo, Boolean esAdelantado){
		this.setEsFuncion(esFuncion);
		this.setNombre(nombre);
		this.setTipo(tipo);
		this.setEsAdelantado(esAdelantado);
		
	}
	
	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	
	public boolean esFuncion() {
		return esFuncion;
	}
	
	public void setEsFuncion(boolean esFuncion) {
		this.esFuncion = esFuncion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean EsAdelantado() {
		return esAdelantado;
	}

	public void setEsAdelantado(boolean esAdelantado) {
		this.esAdelantado = esAdelantado;
	}
	
	public boolean parametrosEsNull () {
		return this.parametros.isEmpty();
	}
	
	//si existe el parametro devuelve la clase parametro si no devuelve la clase en null
	public Parametro existeParametro(String nombre)
	{
		 int index = 0;
		 boolean existe = false;
		 Parametro entradaaux = new Parametro();
	     while ( existe == false && (index <= (parametros.size()-1))) 
	     {	  
	    	 entradaaux=parametros.get(index);
	    	 if ( entradaaux.getLexema()== nombre)
	    	 { 
	    		 	existe = true; 
	    	 }
	    	 index++;
	     }	
    	 return (entradaaux);
	}
	
	public String getListaParametrosAsString ()
	{
		String aux = "";
		if(this.parametros.size()!=0)
		{
			int index = 0;
			aux = this.parametros.get(index).getParametro();
			index++;
			while ( index <= this.parametros.size()-1){ 
				aux = aux + "," +this.parametros.get(index).getParametro();
				index++;
			}
		}
		return aux; 
	}
	
		
}
