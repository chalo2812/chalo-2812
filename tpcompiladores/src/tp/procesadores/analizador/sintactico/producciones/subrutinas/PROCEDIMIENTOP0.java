package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PROCEDIMIENTOP0 extends ProduccionC {
	
		public PROCEDIMIENTOP0(){
			PROCEDIMIENTOP1 pp1 = new PROCEDIMIENTOP1();
			producciones.add(pp1);
			PROCEDIMIENTOP2 pp2 = new PROCEDIMIENTOP2();
			producciones.add(pp2);
		}
		
		/**
		 * PROCEDIMIENTOÕ  ->   adelantado; |
         *        				DECL BLOQUEP
		 */
		@Override 
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
			boolean r;
//			System.out.println("PROCEDIMIENTOP0");
			if ( sintactic.siguiente.accept(visitor).equals("adelantado")){
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			}else
			{
					r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}
			return r;
		}
}
