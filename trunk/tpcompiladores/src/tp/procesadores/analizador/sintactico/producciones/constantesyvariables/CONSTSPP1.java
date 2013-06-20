package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class CONSTSPP1 extends ProduccionC 
{
	public CONSTSPP1 ()
	{
		PalabraReservada comma = new PalabraReservada(",");
		producciones.add(comma);
		CONSTSP0 constsp = null;
		producciones.add(constsp);
	}
	
	//CONSTS'' -> , CONSTS' 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("CONSTSPP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new CONSTSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r; 	
	}
}
