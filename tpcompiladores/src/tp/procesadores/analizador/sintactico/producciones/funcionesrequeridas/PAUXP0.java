package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PAUXP0 extends ProduccionC

{
	public PAUXP0()
	{
		PAUXP1 pauxp1 = null; 
		producciones.add(pauxp1);
		
	}

	//PAUX' ->    [ NATURAL ] | lambda
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r=false;
//		System.out.println("PAUXP0");
		if (sintactic.siguiente.accept(visitor).equals("[")) 
		{ 
			producciones.set(0, new PAUXP1());	
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{ 
			r=true;
		}	
		return r;
	}
}