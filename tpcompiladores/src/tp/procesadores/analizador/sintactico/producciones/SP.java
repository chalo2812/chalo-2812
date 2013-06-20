package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Eof;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;


public class SP extends ProduccionC{
	
	public SP(){
		S s = new S();
		this.add(s);
	}
	
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r = false;
//		System.out.println("SP");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ){
			if ( sintactic.siguiente.getClass() == Eof.class )
			{
				r = true; 
			}
		}
		return r;
	}
}
