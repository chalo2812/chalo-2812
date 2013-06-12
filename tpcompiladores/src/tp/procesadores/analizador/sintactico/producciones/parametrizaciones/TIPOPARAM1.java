package tp.procesadores.analizador.sintactico.producciones.parametrizaciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TIPOPARAM1 extends ProduccionC 
{
	public TIPOPARAM1()
	{
		SimboloTerminal ref = new SimboloTerminal("ref");
		producciones.add(ref);
	}

	//	ref
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("TIPOPARAM1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		return r;
	}
}
