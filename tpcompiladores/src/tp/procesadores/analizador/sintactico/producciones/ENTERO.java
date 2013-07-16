package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.general.NodoEntero;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ElementoIdentificador;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class ENTERO extends Produccion {
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS) 
	{
//		System.out.println("ENTERO");
		if ( sintactic.siguiente.getClass() == Entero.class){
			NodoEntero entero = new NodoEntero(sintactic.siguiente.accept(visitor));
			arbolS.setArbol(entero);
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera numero de tipo Entero", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}
	
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ElementoIdentificador elemento) 
	{
//		System.out.println("ENTERO");
		if ( sintactic.siguiente.getClass() == Entero.class){
			elemento.setValor(sintactic.siguiente.accept(visitor));
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera numero de tipo Entero", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}

}
