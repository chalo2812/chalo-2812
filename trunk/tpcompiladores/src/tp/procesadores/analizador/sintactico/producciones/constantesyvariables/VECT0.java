package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class VECT0 extends ProduccionC {

	public VECT0(){ 
		SimboloTerminal dosPuntos = new SimboloTerminal(":");
		producciones.add(dosPuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
	}
	
	//VECT ->  : TIPO | lambda (ver TVARG3 donde se trata el caso de lambda)
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("VECT0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{ 
			producciones.set(1, new TIPO0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}		
		return r;
	}	
}
