package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TERMP2 extends ProduccionC{
	
	public TERMP2(){
		SimboloTerminal dividido = new SimboloTerminal("/");
		producciones.add(dividido);
		FACT0 fact = null; 
		producciones.add(fact);
		TERMP0 termp = null; 
		producciones.add(termp);
	}

	
	//TERM' ->  / FACT TERM'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r;
//		System.out.println("TERMP2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new FACT0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				producciones.set(2, new TERMP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
			}
		}
		return r;
	}
}
