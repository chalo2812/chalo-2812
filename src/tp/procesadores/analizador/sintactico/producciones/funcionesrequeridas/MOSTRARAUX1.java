package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.CADENA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class MOSTRARAUX1 extends ProduccionC
{
	public MOSTRARAUX1()
	{
		CADENA caedena = new CADENA();
		producciones.add(caedena);
		MOSTRARAUXP0 mostrarauxp0 = null;
		producciones.add(mostrarauxp0);
	}

	//MOSTRARAUX ->	CADENA MOSTRARAUX'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("MOSTRARAUX1");
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1); 
		arbolH.add(arbolSp1.getArbol());
		if ( r )
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			producciones.set(1, new MOSTRARAUXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
			arbolS.setArbol(arbolSp2.getArbol());
		}
		return r;
	}
}