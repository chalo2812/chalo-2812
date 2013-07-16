package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;


public class EXPP0 extends Produccion {

	public EXPP0(){
		EXPP1 exp1 = new EXPP1(); 
		producciones.add(exp1);
		EXPP2 exp2 = new EXPP2(); 
		producciones.add(exp2);
		EXPP3 exp3 = new EXPP3(); 
		producciones.add(exp3);
		EXPP4 exp4 = new EXPP4(); 
		producciones.add(exp4);
	}
	
	
	/**
	 * Produccion: 
	 * 
	 * EXP'  -> + TERM EXP'  |
	 *			- TERM EXP'  |
	 * 			++ TERM EXP' |
	 *			-- TERM EXP' | e
	 */
//	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH ) 
	{
//		System.out.println("EXPPO");
		boolean r; 
		if (sintactic.siguiente.accept(visitor).equals("+")) 
		{  
				ArbolHandler arbolSp1 = new ArbolHandler();
				producciones.set(0, new EXPP1()); 
				r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH); 
				arbolS.setArbol(arbolSp1.getArbol());
		}else
		{ 
			if (sintactic.siguiente.accept(visitor).equals("-")) 
			{ 
					ArbolHandler arbolSp2 = new ArbolHandler();
					producciones.set(1, new EXPP2());
					r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
					arbolS.setArbol(arbolSp2.getArbol());
			}else
			{
				if (sintactic.siguiente.accept(visitor).equals("++")) 
				{ 
						ArbolHandler arbolSp3 = new ArbolHandler();
						producciones.set(2, new EXPP3());
						r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
						arbolS.setArbol(arbolSp3.getArbol());
				}else
				{
					if (sintactic.siguiente.accept(visitor).equals("--")) 
					{ 
							ArbolHandler arbolSp4 = new ArbolHandler();
							producciones.set(3, new EXPP4());
							r = producciones.get(3).reconocer(lexic, visitor, sintactic, arbolH, arbolSp4, tablaH);
							arbolS.setArbol(arbolSp4.getArbol());
					}else
					{
						r = true;
						arbolS.setArbol(arbolH);
					}
					
				}
			}
		}	
		return r;
	}
}
	
