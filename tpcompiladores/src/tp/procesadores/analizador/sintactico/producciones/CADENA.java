package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Cadena;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.general.NodoCadena;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class CADENA extends ProduccionC {
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS) 
	{
//		System.out.println("CADENA");
		if ( sintactic.siguiente.getClass() == Cadena.class )
		{
			NodoCadena cadena = new NodoCadena(sintactic.siguiente.accept(visitor));
			sintactic.consumir(lexic);
			arbolS.setArbol(cadena);
		}else 
		{
			merrores.mostrarYSkipearError("Se espera una cadena", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}

}
