package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
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
		 * PROCEDIMIENTO�  ->   adelantado; |
         *        				DECL BLOQUEP
		 */
		@Override 
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
				ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS) 
		{
			boolean r;
//			System.out.println("PROCEDIMIENTOP0");
			if ( sintactic.siguiente.accept(visitor).equals("adelantado")){
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);
				tablaH.metodos.get(tablaH.metodos.size()-1).setEsAdelantado(true);
				tablaS.setTabla(tablaH);
				arbolS.setArbol(arbolH);
			}else
			{
				ArbolHandler arbolSp = new ArbolHandler();
				TSHandler tablaSp = new TSHandler(); 
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH, tablaSp);
				tablaS.setTabla(tablaSp.getTabla());
				arbolS.setArbol(arbolSp.getArbol());
			}
			return r;
		}
}
