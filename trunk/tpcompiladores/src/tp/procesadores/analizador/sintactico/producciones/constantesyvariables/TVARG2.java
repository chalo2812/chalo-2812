package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TVARG2 extends ProduccionC
{
	public TVARG2()
	{
		SimboloTerminal dosPuntos = new SimboloTerminal(":");
		producciones.add(dosPuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
		INI0 ini = null;
		producciones.add(ini);
		VARSGPP0 varsgpp = null;
		producciones.add(varsgpp);
	}
	
	//TVARG  ->  : TIPO INI VARSGPP
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("TVARG2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new TIPO0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				if ( sintactic.siguiente.accept(visitor).equals("="))
				{
					producciones.set(2, new INI0());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						producciones.set(3, new VARSGPP0());
						r = producciones.get(3).reconocer(lexic, visitor, sintactic);								
					}
				}
				else
				{
					producciones.set(3, new VARSGPP0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);								
				}
			}
		}
		return r; 	
	}
}