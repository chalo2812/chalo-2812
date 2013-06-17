package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.AENTERO0;

public class FACT4 extends ProduccionC{
	
	public FACT4(){
		AENTERO0 aentero = null;
		producciones.add(aentero);
	}

	//FACT ->   AENTERO 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) {
			boolean r = false; 
//			System.out.println("FACT4");
			if ( sintactic.siguiente.accept(visitor).equals("aentero")){
				producciones.set(0, new AENTERO0());
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);	
			}
			return r;
	}	
}
