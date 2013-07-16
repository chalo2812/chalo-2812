package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.general.Identificador;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class LINEA4 extends Produccion {
	
	public LINEA4(){
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		FPOASIG0 fpoasig = null;
		producciones.add(fpoasig);
	}
	
	//LINEA -> PALABRA FPOSASIG
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			 ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("LINEA4");
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new Identificador("",""), arbolSp1, tablaH);
		if ( r )
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			producciones.set(1, new FPOASIG0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
			arbolH.add(arbolSp2.getArbol());
			arbolS.setArbol(arbolH);
		}
		return r;
	}
}
