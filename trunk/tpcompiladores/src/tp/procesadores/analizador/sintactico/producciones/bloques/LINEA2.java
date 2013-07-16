package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.bloque.Mientras;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class LINEA2 extends Produccion {
	
	public LINEA2(){
		BLOQUEM0 bloquem = null;
		producciones.add(bloquem);
	}
	
	//LINEA ->   BLOQUEM
		//	LINEA.ArbolS = BLOQUEM.ArbolS
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			 ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("LINEA2");
		ArbolHandler arbolSp = new ArbolHandler();
		producciones.set(0, new BLOQUEM0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new Mientras(), arbolSp, tablaH);
		arbolH.add(arbolSp.getArbol());
		arbolS.setArbol(arbolH);
		return r;
	} 
}
