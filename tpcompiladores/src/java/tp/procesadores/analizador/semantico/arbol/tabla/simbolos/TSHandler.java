package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;


public class TSHandler {

	private TablaDeSimbolos tabla;
	private TablaDeSimbolos tablaPadre;
	
	public TablaDeSimbolos getTabla() {
		return tabla;
	}

	public void setTabla(TablaDeSimbolos tabla) {
		this.tabla = tabla;
	} 
	
	public TablaDeSimbolos getTablaPadre() {
		return tablaPadre;
	}

	public void setTablaPadre(TablaDeSimbolos tablaPadre) {
		this.tablaPadre = tablaPadre;
	}
}
