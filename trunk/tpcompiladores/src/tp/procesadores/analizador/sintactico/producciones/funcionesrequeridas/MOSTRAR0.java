package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class MOSTRAR0 extends Produccion

{
	public MOSTRAR0()
	{
		SimboloTerminal mostrarln = new SimboloTerminal("mostrar");
		producciones.add(mostrarln);
		MOSTRARAUX0  mostraraux = null;
		producciones.add(mostraraux);
	}
	
	//MOSTRAR       ->   mostrar MOSTRARAUX
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("MOSTRAR0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			ArbolHandler arbolSp = new ArbolHandler();
			producciones.set(1, new MOSTRARAUX0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
			arbolS.setArbol(arbolSp.getArbol());
		}

		return r;
	}
}
