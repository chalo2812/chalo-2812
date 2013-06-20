package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TVARG1 extends ProduccionC {

	public TVARG1()
	{
		SimboloTerminal coma = new SimboloTerminal(",");
		producciones.add(coma);
		VARSGP0 varsgp = null;
		producciones.add(varsgp);
	}
	
	//TVARG -> , VARGS'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("TVARG1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new VARSGP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}
}
