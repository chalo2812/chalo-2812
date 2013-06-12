package tp.procesadores.analizador.semantico.arbol.funcionesrequeridas;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class NodoAEntero extends ClaseNodo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1003677944011559167L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{		
		Codigo codigoAux = new Codigo();
		this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);	
		codigo.appendCodigo(codigoAux.getCodigo());
		StringBuilder str = new StringBuilder();
		str.append("MOV\t"+"BX,"+codigoAux.getLabel()+"\n");
		str.append("CALL\t"+"AENTERO"+"\n");		
		codigo.appendCodigo(str.toString());
		codigo.setLabel("AX");
		return codigo;
	}
}
