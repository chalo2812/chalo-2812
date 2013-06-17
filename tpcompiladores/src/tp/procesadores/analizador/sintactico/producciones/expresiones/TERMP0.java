package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TERMP0 extends ProduccionC {
	
		public TERMP0(){
			TERMP1 termp1 = null; 
			producciones.add(termp1);
			TERMP1 termp2= null; 
			producciones.add(termp2);
			TERMP1 termp3 = null; 
			producciones.add(termp3);
			TERMP1 termp4 = null; 
			producciones.add(termp4);
		}
		
		/**
		 * TERM' -> * FACT TERM' |
		 * 			/ FACT TERM' | 
		 *			** FACT TERM'| 
		 *			// FACT TERM'| e
		 */
		
		@Override
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) 
		{
			boolean r; 
//			System.out.println("TERMP0");
			if (sintactic.siguiente.accept(visitor).equals("*")) 
			{ 
					producciones.set(0, new TERMP1());
					r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			}else
			{ 
				if (sintactic.siguiente.accept(visitor).equals("/")) 
				{ 
						producciones.set(1, new TERMP2());
						r = producciones.get(1).reconocer(lexic, visitor, sintactic);
				}else
				{
					if (sintactic.siguiente.accept(visitor).equals("**")) 
					{ 
							producciones.set(2, new TERMP3());
							r = producciones.get(2).reconocer(lexic, visitor, sintactic);
					}else
					{
						if (sintactic.siguiente.accept(visitor).equals("//")) 
						{ 
								producciones.set(3, new TERMP4());
								r = producciones.get(3).reconocer(lexic, visitor, sintactic);
						}else 
						{
							r = true;
						}
					}
				}
			}	
			return r;
		}
}
