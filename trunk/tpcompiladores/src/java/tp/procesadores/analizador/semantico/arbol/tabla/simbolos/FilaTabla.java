package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;


public class FilaTabla {

	private boolean esVariable; 
	private String id;
	private String tipo; 
	private String valor; 

	
	//Entrada null
	public FilaTabla(){
		this.setEsVariable(false);
		this.setId(null);
		this.setTipo(null);
		this.setValor(null);
	}
	
	//Entrada de vector o constante 
	public FilaTabla( boolean variable, String identificador, String tipoVar, String val){
		this.setEsVariable(variable);
		this.setId(identificador);
		this.setTipo(tipoVar);
		this.setValor(val);
	}

	//entrada de variable 
	public FilaTabla( boolean variable, String identificador, String tipoVar){
		this.setEsVariable(variable);
		this.setId(identificador);
		this.setTipo(tipoVar);
	}

	
	public boolean esVariable() {
		return esVariable;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public void setEsVariable(boolean esVariable)
	{
		this.esVariable = esVariable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public int obtenerIdentificador(){
		return 0;//new Identificador("HDP");
	}
	
}
