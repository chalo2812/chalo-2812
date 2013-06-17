package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class EXP0 extends ProduccionC {

	public EXP0()
	{
		TERM0 term = new TERM0();
		EXPP0 expp = new EXPP0();
		this.add(term); 
		this.add(expp);
	}
	
	//EXP -> TERM EXP'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("EXPO");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if (r) {
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r; 
	}
}
