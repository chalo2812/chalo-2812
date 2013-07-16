package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class EXPBOOL1 extends Produccion {

		public EXPBOOL1(){
			EXP0 exp = null;
			producciones.add(exp);
			EXPBOOLP0 expboolp = null;
			this.add(expboolp);
		}
	
		//EXPBOOL'.ArbolH = EXP.ArbolS
		//EXPBOOL.ArbolS = EXPBOOL'.ArbolS
		
		// EXPBOOL ->   EXP EXPBOOL'
		@Override
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
				ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
		{
			boolean r; 
//			System.out.println("EXPBOOL1");
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(0, new EXP0()); 
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH); 
			if ( r ) 
			{ 
				ArbolHandler arbolSp2 = new ArbolHandler();
				producciones.set(1, new EXPBOOLP0()); 
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}
			return r; 
		}
}
