package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;

import java.util.ArrayList;
import java.util.List;

public class ListaParametrosHandler {

	private List<Parametro> parametros = new ArrayList<Parametro>();

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}
}
