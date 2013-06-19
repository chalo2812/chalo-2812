package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class FACT1 extends ProduccionC{
	
	public FACT1(){
		SimboloTerminal parentesis1 = new SimboloTerminal("(");
		producciones.add(parentesis1);
		EXP0 exp = null; 
		producciones.add(exp);
		SimboloTerminal parentesis2 = new SimboloTerminal(")"); 
		producciones.add(parentesis2);
	}

	//EXP.ArbolH = FACT.ArbolH
	//FACT.ArbolS = EXP.ArbolH 

	//FACT ->   (EXP)
//	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//		System.out.println("FACT1"); 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{ 
			producciones.set(1, new EXP0());
			ArbolHandler arbolSp = new ArbolHandler();
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
			arbolS.setArbol(arbolSp.getArbol());
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if(!r)
				{
					merrores.mostrarYSkipearError("Se espera parentesis ')'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r=true;
				}
			}
		}
		return r;
	}
}
