package tp.procesadores.analizador.semantico.arbol.principal;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Globales extends ClaseNodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5686265016020672168L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager){
		if ( this.nodos.size()>0)
		{
			Codigo codigoAux = new Codigo(); 
			codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
			codigo.setCodigo(codigo.getCodigo() + 
							codigoAux.getCodigo());
		}
		return codigo;
	}
	
	
}
