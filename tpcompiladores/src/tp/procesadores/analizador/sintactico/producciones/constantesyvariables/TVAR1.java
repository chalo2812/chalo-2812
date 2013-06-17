package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TVAR1 extends ProduccionC
{
	public TVAR1 ()
	{
		SimboloTerminal comma = new SimboloTerminal(",");
		producciones.add(comma);
		VARSP0 varsp = null;
		producciones.add(varsp);
	}
	
	//TVAR -> , VARS’	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("TVAR1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new VARSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r; 	
	}
}
