package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.bloque.Asignacion;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresion;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class FPOASIG2 extends Produccion {

	public FPOASIG2()
	{
		SimboloTerminal digual = new SimboloTerminal(":=");
		producciones.add(digual);
		EXP0 exp = null;
		producciones.add(exp);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}

	//FPOASIG  -> := EXP;
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("FPOASIG2");
		Asignacion asignacion = new Asignacion();
		asignacion.add(arbolH);
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			ArbolHandler arbolSp = new ArbolHandler();
			NodoExpresion expresion = new NodoExpresion();
			producciones.set(1, new EXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp, tablaH);
			expresion.add(arbolSp.getArbol());
			asignacion.add(expresion);
			arbolS.setArbol(asignacion);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if(!r)
				{
					merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}
}	