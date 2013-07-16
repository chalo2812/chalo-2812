package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class LINEA3 extends Produccion {
	
	public LINEA3(){
		PALRES palres = null;
		producciones.add(palres);
	}
	
	//LINEA ->   PALRES 
		//	LINEA.ArbolS = PALRES.ArbolS
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			 ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("LINEA3");
		ArbolHandler arbolSp = new ArbolHandler();
		producciones.set(0, new PALRES());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
		arbolS.setArbol(arbolSp.getArbol());
		return r;
	} 
}
