package tp.procesadores.analizador.sintactico.producciones.subrutinas;


import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FUNCIONP0 extends ProduccionC {

	public FUNCIONP0(){
		FUNCIONP1 f1 = null; 
		producciones.add(f1);
		FUNCIONP2 f2 = null; 
		producciones.add(f2);
	}
	
	/** FUNCIONÕ-> adelantado; |
	 *				DECL BLOQUEF
	 **/
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r;
//		System.out.println("FUNCIONP0");
		if ( sintactic.siguiente.accept(visitor).equals("adelantado")) 
		{ 
			producciones.set(0, new FUNCIONP1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{ 
			producciones.set(1, new FUNCIONP2());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}

	
}
