package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.funcionesrequeridas.NodoAEntero;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class AENTERO0 extends Produccion

{
	public AENTERO0()
	{
		SimboloTerminal aentero = new SimboloTerminal("aentero");
		producciones.add(aentero);
		SimboloTerminal parentesisabrir = new SimboloTerminal("(");
		producciones.add(parentesisabrir);
		EXP0  tipo = null;
		producciones.add(tipo);
		SimboloTerminal parentesiscerrar = new SimboloTerminal(")");
		producciones.add(parentesiscerrar);
	}
	
	//AENTERO.ArbolH = CrearNodoAentero ( ) 
	//AENTERO.ArbolH.add  ( EXP.ArbolS ) 
	//AENTERO.ArbolS = ArbolH 
	
	//AENTERO         ->   aentero ( EXP )
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("AENTERO0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				arbolH = new NodoAEntero();
				ArbolHandler arbolSp = new ArbolHandler();
				producciones.set(2, new EXP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
				arbolH.add(arbolSp.getArbol());
				arbolS.setArbol(arbolH);
				
				if ( r ) 
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if (!r)
					{
						merrores.mostrarYSkipearError("Se espera cierre de parentesis ')'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
				}
			}
			else
			{
				merrores.mostrarYSkipearError("Se espera apertura de parentesis '('", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
