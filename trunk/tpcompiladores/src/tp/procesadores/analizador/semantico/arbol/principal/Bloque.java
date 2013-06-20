package tp.procesadores.analizador.semantico.arbol.principal;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Bloque extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager){
		int index = 1; 
		Codigo codigoAux = new Codigo();
		while ( index <= (this.nodos.size()))
		{
			if( codigo.getContexto() != null )
				codigoAux.setContexto(codigo.getContexto());
			
			codigoAux = this.nodos.get(index-1).generarCodigo(codigoAux, tempManager, labelManager);
			codigo.appendCodigo(codigoAux.getCodigo());
			
			if ( codigoAux.getLabel()!= null )
				codigo.setLabel(codigoAux.getLabel());
			codigoAux = new Codigo();
			index++;
		}
		return codigo;
	}

}
