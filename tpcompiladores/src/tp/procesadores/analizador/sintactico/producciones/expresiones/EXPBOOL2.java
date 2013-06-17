package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class EXPBOOL2 extends ProduccionC {

	public EXPBOOL2(){
		AND and = new AND(); 
		producciones.add(and);
	}

	//EXPBOOL -> AND  
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("EXPBOOL2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		return r;
	}

}
