package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class EXPP3 extends ProduccionC{
	
	public EXPP3(){
		SimboloTerminal mas = new SimboloTerminal("++");
		producciones.add(mas);
		TERM0 term = null; 
		producciones.add(term);
		EXPP0 expp = null; 
		producciones.add(expp);
	}

	//EXP'  ->   ++ TERM EXP' 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("EXPP3");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new TERM0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				producciones.set(2, new EXPP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
			}
		}
		return r;
	}
}
