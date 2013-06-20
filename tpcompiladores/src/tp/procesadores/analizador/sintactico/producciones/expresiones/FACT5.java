package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.ANATURAL0;

public class FACT5 extends ProduccionC{
	
	public FACT5(){
		ANATURAL0 anatural = null;
		producciones.add(anatural);
	}

	//FACT ->   ANATURAL
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) {
			boolean r = false; 
//			System.out.println("FACT5");
			if ( sintactic.siguiente.accept(visitor).equals("anatural")){
				producciones.set(0, new ANATURAL0());
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);	
			}
			return r;
	}	
}
