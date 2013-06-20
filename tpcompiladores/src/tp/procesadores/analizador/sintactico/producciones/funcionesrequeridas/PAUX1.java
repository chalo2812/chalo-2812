package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class PAUX1 extends ProduccionC
{

	public PAUX1()
	{
		SimboloTerminal corcheteabre = new SimboloTerminal("[");
		producciones.add(corcheteabre);
		EXP0 exp = null;
		producciones.add(exp);
		SimboloTerminal corchetecierra = new SimboloTerminal("]");
		producciones.add(corchetecierra);
	}

	//	[EXP]
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("PAUX1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			ArbolHandler arbolSp = new ArbolHandler();
			producciones.set(1, new EXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
			arbolH.add(arbolSp.getArbol());
			arbolS.setArbol(arbolH);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if (!r)
				{
					merrores.mostrarYSkipearError("Se espera cierre de corchete ']'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
			
		}
		return r;
	}
}
