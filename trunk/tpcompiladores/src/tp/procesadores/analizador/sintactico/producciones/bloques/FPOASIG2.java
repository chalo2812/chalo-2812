package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class FPOASIG2 extends ProduccionC {

	public FPOASIG2()
	{
		SimboloTerminal digual = new SimboloTerminal(":=");
		producciones.add(digual);
		EXP0 exp = null;
		producciones.add(exp);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}

	//FPOASIG  -> := EXP;
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("FPOASIG2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new EXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if(!r)
				{
					merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}
}	