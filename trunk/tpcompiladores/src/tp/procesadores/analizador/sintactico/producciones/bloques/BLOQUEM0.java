package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresionBooleana;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXPBOOL0;

public class BLOQUEM0 extends Produccion {

	public BLOQUEM0()
	{
		PalabraReservada mientras = new PalabraReservada("mientras");
		producciones.add(mientras);
		EXPBOOL0 expbool = null;
		producciones.add(expbool);
		PalabraReservada hacer = new PalabraReservada("hacer");
		producciones.add(hacer);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
		PalabraReservada finmientras = new PalabraReservada("fin-mientras");
		producciones.add(finmientras);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}


	//BLOQUEM -> mientras EXPBOOL hacer BLOQUE fin-mientras;
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("BLOQUEM0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			NodoExpresionBooleana expresion = new NodoExpresionBooleana();
			ArbolHandler arbolSp1 = new ArbolHandler(); 
			producciones.set(1, new EXPBOOL0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
			expresion.add(arbolSp1.getArbol());
			arbolH.add(expresion);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					ArbolHandler arbolSp2 = new ArbolHandler();
					producciones.set(3, new BLOQUE0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic, new Bloque(), arbolSp2, tablaH);
					arbolH.add(arbolSp2.getArbol());
					arbolS.setArbol(arbolH);
					if ( r )
					{
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
						if ( r )
						{
							r = producciones.get(5).reconocer(lexic, visitor, sintactic);
							if (!r)
							{
								merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
								sintactic.setEstadoAnalisis(false);
								r = true;
							}
						}
						else
						{
							merrores.mostrarYSkipearError("Se espera palabra reservada 'fin-mientras'", lexic, sintactic, visitor);
							sintactic.setEstadoAnalisis(false);
							r = true;
						}
					}
				}
				else
				{
					merrores.mostrarYSkipearError("Se espera palabra reservada 'hacer'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}
}