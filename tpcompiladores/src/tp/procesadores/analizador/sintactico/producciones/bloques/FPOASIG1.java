package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.PASAJE0;

public class FPOASIG1 extends ProduccionC {

	public FPOASIG1()
	{
		SimboloTerminal parentesis1 = new SimboloTerminal("(");
		producciones.add(parentesis1);
		PASAJE0 pasaje = null;
		producciones.add(pasaje);
		SimboloTerminal parentesis2 = new SimboloTerminal(")");		
		producciones.add(parentesis2);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}

	//FPOASIG  ->   (PASAJE);
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("FPOASIG1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new PASAJE0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if(r)
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if (!r)
					{
						merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;	
					}
				}else 
				{
					merrores.mostrarYSkipearError("Se espera parentesis ')'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;	
				}
			}
		}
		return r;
	}
}