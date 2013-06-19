package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class ExpresionBooleana extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override 
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		codigo.setLabel("bool");
		return this.nodos.get(0).generarCodigo(codigo, tempManager, labelManager);
	}
}
