package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.ESIMPAR0;

public class EXPBOOL5 extends Produccion
{
	public EXPBOOL5()
	{
		ESIMPAR0 esimpar = new ESIMPAR0(); 
		producciones.add(esimpar);
	}
	
	//EXPBOOL -> ESIMPAR \\ EXPBOOL.ArbolS = ESIMPAR.ArbolS
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//			System.out.println("EXPBOOL5");
		ArbolHandler arbolSp = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
		arbolS.setArbol(arbolSp.getArbol());
		return r;	
	}
}