package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class MOSTRARAUX2 extends ProduccionC
{
	public MOSTRARAUX2()
	{
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		PAUX0 paux0 = null;
		producciones.add(paux0);
		MOSTRARAUXP0 mostrarpauxp0 = null;
		producciones.add(mostrarpauxp0);
	}
	
	//PAUX.ArbolH = PALABRA.ArbolS
	//MOSTRARAUX.ArbolH.add ( PALABRA.ArbolS ) 
	//MOSTRARAUX'.ArbolH = MOSTRARAUX.ArbolH 
	//MOSTRARAUX.ArbolS = MOSTRARAUX.ArbolS 
			
	//MOSTRARAUX ->	PALABRA PAUX MOSTRARAUX'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("MOSTRARAUX2");
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH); 
		if ( r )
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			producciones.set(1, new PAUX0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
			arbolH.add(arbolSp2.getArbol());
			if ( r )
			{
				ArbolHandler arbolSp3 = new ArbolHandler();
				producciones.set(2, new MOSTRARAUXP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
				arbolS.setArbol(arbolSp3.getArbol());
			}
		}
		return r;
	}
}