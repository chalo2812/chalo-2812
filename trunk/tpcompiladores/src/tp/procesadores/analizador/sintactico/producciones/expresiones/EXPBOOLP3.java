package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class EXPBOOLP3 extends ProduccionC {

	public EXPBOOLP3(){
		SimboloTerminal igual = new SimboloTerminal("!=");
		producciones.add(igual);
		EXP0 exp = null;
		producciones.add(exp);
	}
	
	//EXPBOOL' ->  != EXP 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("EXPBOOLP3");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new EXP0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r; 
	}
}
