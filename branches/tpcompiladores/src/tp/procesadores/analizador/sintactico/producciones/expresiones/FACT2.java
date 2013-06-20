package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FACT2 extends ProduccionC {

	public FACT2(){
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		FACTP0 factp = null; 
		producciones.add(factp); 
	}
	
	//FACT.ArbolH = CrearArbol (PALABRA.lexema, FACT'.ArbolH ) 
	//FACT.ArbolS = FACT'.ArbolS
	
	//FACT ->   PALABRA FACT'
//	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("FACT2");
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH); 
		if ( r )
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			producciones.set(1, new FACTP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
			arbolS.setArbol(arbolSp2.getArbol());
		}
		return r;
	}
}
