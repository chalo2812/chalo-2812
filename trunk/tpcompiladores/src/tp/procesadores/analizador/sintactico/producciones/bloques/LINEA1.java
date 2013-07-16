package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.bloque.Si;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class LINEA1 extends Produccion {

	public LINEA1(){
		BLOQUESI0 bloquesi = null;
		producciones.add(bloquesi);
	}
	
	//LINEA ->   BLOQUESI
		//	LINEA.ArbolS = BLOQUESI.ArbolS
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			 ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("LINEA1");
		ArbolHandler arbolSp = new ArbolHandler();
		producciones.set(0, new BLOQUESI0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new Si(), arbolSp, tablaH);
		arbolH.add(arbolSp.getArbol());
		arbolS.setArbol(arbolH);
		return r;
	} 
}
