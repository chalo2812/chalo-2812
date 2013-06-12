package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.general.NodoNatural;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ElementoIdentificador;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class NATURAL extends ProduccionC {
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS) 
	{
//		System.out.println("NATURAL");
		if ( sintactic.siguiente.getClass() == Natural.class){
			arbolS.setArbol(new NodoNatural(sintactic.siguiente.accept(visitor)));
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera numero de tipo Natural", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ElementoIdentificador elemento) 
	{
//		System.out.println("NATURAL");
		if ( sintactic.siguiente.getClass() == Natural.class){
			elemento.setValor(sintactic.siguiente.accept(visitor));
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera numero de tipo Natural", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}
}
