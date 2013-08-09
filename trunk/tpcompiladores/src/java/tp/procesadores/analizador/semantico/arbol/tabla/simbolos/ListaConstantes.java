package tp.procesadores.analizador.semantico.arbol.tabla.simbolos;

import java.util.ArrayList;
import java.util.List;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;

public class ListaConstantes extends ClaseNodo{

	private static final long serialVersionUID = 1L;
	public List<ElementoIdentificador> identificadores = new ArrayList<ElementoIdentificador>();
	
	public void addIdentificador(ElementoIdentificador id){
		identificadores.add(id);
	}
	
	public ElementoIdentificador getLastElement(){
		if ( identificadores.size()!=0)
		{
			return identificadores.get(identificadores.size()-1);
		}else
		{
			return null;
		}
	}
}
