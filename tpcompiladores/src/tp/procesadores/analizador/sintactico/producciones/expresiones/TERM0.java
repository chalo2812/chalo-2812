package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TERM0 extends ProduccionC{

	public TERM0(){
		FACT0 fact = new FACT0(); 
		producciones.add(fact);
		TERMP0 termp = new TERMP0(); 
		producciones.add(termp);
	}
	
	
//	TERM'.ArbolH = FACT.ArbolS
//	TERM.ArbolS = TERM'.ArbolS

	
	//TERM  ->   FACT TERM'
//	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("TERM0");
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
		if (r) 
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
			arbolS.setArbol(arbolSp2.getArbol());
		}
		return r; 
	}
}
