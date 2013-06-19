package tp.procesadores.analizador.lexico.tokens.visitor;


public class NodeVisitor {
	private String lexema;
	private String contexto;
	
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public String getContexto() {
		return contexto;
	}
	public void setContexto(String contexto) {
		this.contexto = contexto;
	} 
	
	
	
}
