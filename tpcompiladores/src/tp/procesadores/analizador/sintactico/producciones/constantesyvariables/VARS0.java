package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class VARS0 extends ProduccionC
{
	public VARS0 ()
	{
		PalabraReservada var = new PalabraReservada("var");
		producciones.add(var);
		VARSP0 varsp = null;
		producciones.add(varsp);
	}
	
	//VARS -> var VARS'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("VARS0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new VARSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r; 	
	}
}
