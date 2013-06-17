package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSPP1 extends ProduccionC
{
	public VARSPP1 ()
	{
		PalabraReservada comma = new PalabraReservada(",");
		producciones.add(comma);
		VARSP0 varsp = null;
		producciones.add(varsp);
	}

	//VARS’' -> , VARS’ 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{		
		boolean r;
//		System.out.println("VARSPP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new VARSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r; 	
	}
}
