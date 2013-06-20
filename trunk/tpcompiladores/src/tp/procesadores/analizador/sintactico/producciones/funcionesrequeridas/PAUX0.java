package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PAUX0 extends ProduccionC
{
	public PAUX0()
	{
		PAUX1 paux1 = null; 
		producciones.add(paux1);
	}

	//PAUX  ->    [ EXP ] | lambda
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r=false;
//		System.out.println("PAUX0");
		if (sintactic.siguiente.accept(visitor).equals("[")) 
		{ 
			producciones.set(0, new PAUX1());	
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{ 
			r=true;
		}	
		return r;
	}
}
