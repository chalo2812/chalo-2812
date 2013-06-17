package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class FPOASIG3 extends ProduccionC {

	public FPOASIG3()
	{
		SimboloTerminal corchete1 = new SimboloTerminal("[");
		producciones.add(corchete1);
		EXP0 exp1 = null;
		producciones.add(exp1);
		SimboloTerminal corchete2 = new SimboloTerminal("]");
		producciones.add(corchete2);
		SimboloTerminal digual = new SimboloTerminal(":=");
		producciones.add(digual);
		EXP0 exp2 = null;
		producciones.add(exp2);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}

	//FPOASIG  -> [EXP] := EXP;
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("FPOASIG3");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new EXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						producciones.set(4, new EXP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
						if ( r )
						{
							r = producciones.get(5).reconocer(lexic, visitor, sintactic);
							if(!r)
							{
								merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
								sintactic.setEstadoAnalisis(false);
								r = true;
							}
						}
					}else
					{
						merrores.mostrarYSkipearError("Se espera operador de asignacion ':='", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
				}else
				{
					merrores.mostrarYSkipearError("Se espera corchete ']'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}
}	