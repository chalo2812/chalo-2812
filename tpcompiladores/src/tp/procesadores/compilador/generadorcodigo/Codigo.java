package tp.procesadores.compilador.generadorcodigo;

public class Codigo {

	String codigo;
	String contexto;
	
	public Codigo(){
		super();
		this.codigo = "";
		this.contexto = "";
	}
	
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getContexto() {
		return null;
	}

	public void setContexto(String contexto) {

	}

	public void appendCodigo(String codigo) {
		this.codigo.concat(codigo);
	}

	public String getLabel() {
		return null;
	}

	public void setLabel(String label) {

	}

}
