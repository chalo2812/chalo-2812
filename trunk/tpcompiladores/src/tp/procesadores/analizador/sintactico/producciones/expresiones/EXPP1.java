package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoSumaEnteros;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class EXPP1 extends Produccion{
	
	public EXPP1(){
		SimboloTerminal mas = new SimboloTerminal("+");
		producciones.add(mas);
		TERM0 term = null; 
		producciones.add(term);
		EXPP0 expp = null; 
		producciones.add(expp);
	}
	
	
	//EXP'1.ArbolH = CrearNodoSumaEntero ( EXP'.ArbolH, TERM.ArbolS) 
	//EXP'.ArbolS = EXP'1.ArbolS
	
	//EXP' ->   + TERM EXP'
//	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("EXPP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{ 
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(1, new TERM0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
			if ( r )
			{
				ArbolHandler arbolSp2 = new ArbolHandler();
				NodoSumaEnteros suma = new NodoSumaEnteros(arbolH, arbolSp1.getArbol());
//				arbolH = arbolUtils.deepCopy(suma);
				arbolH = suma;
				producciones.set(2, new EXPP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}
		}
		return r;
	}
}
