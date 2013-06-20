package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSPP0 extends ProduccionC
{
	public VARSPP0 ()
	{
		VARSPP1 varspp1 = null;
		producciones.add(varspp1);
		VARSPP2 varspp2 = new VARSPP2();
		producciones.add(varspp2);
	}

	//VARS’' -> , VARS’ | ;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{		
		boolean r;
//		System.out.println("VARSPP0");
		producciones.set(0, new VARSPP1());
		if ( sintactic.siguiente.accept(visitor).equals(","))
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			if ( sintactic.siguiente.accept(visitor).equals(";"))
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}
			else
			{
				merrores.mostrarYSkipearError("Se esperaba una nueva variable o un fin de linea", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
