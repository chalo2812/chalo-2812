package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class EXPBOOL1 extends ProduccionC {

		public EXPBOOL1(){
			EXP0 exp = null;
			producciones.add(exp);
			EXPBOOLP0 expboolp = null;
			this.add(expboolp);
		}
	
		// EXPBOOL ->   EXP EXPBOOL'
		
		@Override
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) 
		{
			boolean r; 
//			System.out.println("EXPBOOL1");
			producciones.set(0, new EXP0()); 
			r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
			if ( r ) 
			{ 
				producciones.set(1, new EXPBOOLP0()); 
				r = producciones.get(1).reconocer(lexic, visitor, sintactic); 
			}
			return r; 
		}
		
		
}
