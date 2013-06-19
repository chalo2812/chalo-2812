package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
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
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
				ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)  
		{
			boolean r; 
//			System.out.println("TERMP0");
			if (sintactic.siguiente.accept(visitor).equals("*")) 
			{ 
					producciones.set(0, new TERMP1());
					ArbolHandler arbolSp1 = new ArbolHandler();
					r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
					arbolS.setArbol(arbolSp1.getArbol());
			}else
			{ 
				if (sintactic.siguiente.accept(visitor).equals("/")) 
				{ 
						producciones.set(1, new TERMP2());
						ArbolHandler arbolSp2 = new ArbolHandler();
						r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
						arbolS.setArbol(arbolSp2.getArbol());
				}else
				{
					if (sintactic.siguiente.accept(visitor).equals("**")) 
					{ 
							producciones.set(2, new TERMP3());
							ArbolHandler arbolSp3 = new ArbolHandler();
							r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
							arbolS.setArbol(arbolSp3.getArbol());
					}else
					{
						if (sintactic.siguiente.accept(visitor).equals("//")) 
						{ 
								ArbolHandler arbolSp4 = new ArbolHandler();
								producciones.set(3, new TERMP4());
								r = producciones.get(3).reconocer(lexic, visitor, sintactic, arbolH, arbolSp4, tablaH);
								arbolS.setArbol(arbolSp4.getArbol());
						}else 
						{
							arbolS.setArbol(arbolH);
							r = true;
						}
					}
				}
			}	
			return r;
		}
}
