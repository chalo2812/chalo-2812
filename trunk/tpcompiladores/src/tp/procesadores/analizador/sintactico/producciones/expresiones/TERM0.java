package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TERM0 extends ProduccionC{

		public TERM0(){
			FACT0 fact = new FACT0(); 
			producciones.add(fact);
			TERMP0 termp = new TERMP0(); 
			producciones.add(termp);
		}
		
		//TERM  ->   FACT TERM'
		@Override
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) {
			boolean r; 
//			System.out.println("TERM0");
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			if (r) {
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}
			return r; 
		}
	
}
