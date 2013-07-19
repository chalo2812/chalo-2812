package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class EXPBOOL0 extends Produccion {

	
	public EXPBOOL0(){
		EXPBOOL1 exp1 = new EXPBOOL1();
		producciones.add(exp1); 
		EXPBOOL2 exp2 = new EXPBOOL2();
		producciones.add(exp2); 
		EXPBOOL3 exp3 = new EXPBOOL3();
		producciones.add(exp3);
		EXPBOOL4 espar = null;
		producciones.add(espar);
		EXPBOOL5 esimpar = null;
		producciones.add(esimpar);
	}
 
	 // EXPBOOL ->   EXP EXPBOOL' |  { (, PALABRA, ENTERO, NATURAL, aentero, anatural }
	 //				AND |			{ and }  
	 //				OR	|			{ or }
	 //				ESPAR | 		{ par }
	 //				ESIMPAR			{ impar }
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)	{
		boolean reconoce = false;

		if ((sintactic.siguiente.accept(visitor).equals("(")) || (sintactic.siguiente.getClass() == Palabra.class) 
				|| (sintactic.siguiente.getClass() == Entero.class) || (sintactic.siguiente.getClass() == Natural.class) || (sintactic.siguiente.accept(visitor).equals("aentero")) 
				|| (sintactic.siguiente.accept(visitor).equals("anatural")) ){
				ArbolHandler arbolSp1 = new ArbolHandler();
				reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
				arbolS.setArbol(arbolSp1.getArbol());
		}else{ 
			if (sintactic.siguiente.accept(visitor).equals("and")){
				ArbolHandler arbolSp2 = new ArbolHandler();
				reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic,arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}else	{
				if (sintactic.siguiente.accept(visitor).equals("or"))	{
					ArbolHandler arbolSp3 = new ArbolHandler();
					reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
					arbolS.setArbol(arbolSp3.getArbol());
				} else{
					if (sintactic.siguiente.accept(visitor).equals("par")) {
						ArbolHandler arbolSp4 = new ArbolHandler();
						producciones.set(3, new EXPBOOL4());
						reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic, arbolH, arbolSp4, tablaH);
						arbolS.setArbol(arbolSp4.getArbol());
					} else {
						if (sintactic.siguiente.accept(visitor).equals("impar")) {
							ArbolHandler arbolSp5 = new ArbolHandler();
							producciones.set(4, new EXPBOOL5());
							reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic, arbolH, arbolSp5, tablaH);
							arbolS.setArbol(arbolSp5.getArbol());
						}								
					}
				}					
			}
		}
		return reconoce;
	}
}
