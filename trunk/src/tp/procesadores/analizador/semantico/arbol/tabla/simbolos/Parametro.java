package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;

public class Parametro {
	private boolean esPorValor;
	private String lexema; 
	private String tipo;
	
	public Parametro(boolean valor, String identificador, String tip){
		setEsPorValor(valor); 
		setLexema(identificador);
		setTipo(tip);
	}
	
	public Parametro(){
		setEsPorValor(false); 
		setLexema(null);
		setTipo(null);
	}

	public boolean esPorValor() {
		return esPorValor;
	}

	public void setEsPorValor(boolean esPorValor) {
		this.esPorValor = esPorValor;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public String getParametro(){
		String aux; 
		if ( this.esPorValor()){ 
			aux = "val:" + this.getLexema() + ":" + this.getTipo();
		}else{
			aux = "ref:" + this.getLexema() + ":" + this.getTipo();
		}
		return aux; 
	}
	
}
