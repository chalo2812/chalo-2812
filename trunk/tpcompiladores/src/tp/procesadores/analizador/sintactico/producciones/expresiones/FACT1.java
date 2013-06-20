package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class FACT1 extends ProduccionC{
	
	public FACT1(){
		SimboloTerminal parentesis1 = new SimboloTerminal("(");
		producciones.add(parentesis1);
		EXP0 exp = null; 
		producciones.add(exp);
		SimboloTerminal parentesis2 = new SimboloTerminal(")"); 
		producciones.add(parentesis2);
	}

	
	//FACT ->   (EXP)
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("FACT1");
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
					merrores.mostrarYSkipearError("Se espera parentesis ')'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r=true;
				}
			}
		}
		return r;
	}
}
