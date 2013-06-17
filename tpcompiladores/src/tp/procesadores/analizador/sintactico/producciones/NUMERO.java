package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class NUMERO extends ProduccionC {
	
	public NUMERO(){
		ENTERO entero = new ENTERO();
		producciones.add(entero);
		NATURAL natural = new NATURAL();
		producciones.add(natural);
	}
	
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
//		System.out.println("NUMERO");
		boolean r;
		if ( sintactic.siguiente.getClass() == Entero.class ){
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else{
			if ( sintactic.siguiente.getClass() == Natural.class )
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else 
			{
				r = false;
			}
		}
		return r;
	}

}
