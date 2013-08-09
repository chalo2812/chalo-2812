package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class FUNCIONP0 extends Produccion {

   public FUNCIONP0() {
      FUNCIONP1 f1 = null;
      producciones.add(f1);
      FUNCIONP2 f2 = null;
      producciones.add(f2);
   }

   // FUNCION -> adelantado; |
   // DECL BLOQUEF

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH, TSHandler tablaS) {
      boolean reconoce;
      if (sintactic.siguiente.accept(visitor).equals("adelantado")) {
         producciones.set(0, new FUNCIONP1());
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
         tablaH.metodos.get(tablaH.metodos.size() - 1).setEsAdelantado(true);
         tablaS.setTabla(tablaH);
         arbolS.setArbol(arbolH);
      } else {
         ArbolHandler arbolSp = new ArbolHandler();
         TSHandler tablaSp = new TSHandler();
         producciones.set(1, new FUNCIONP2());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH, tablaSp);
         tablaS.setTabla(tablaSp.getTabla());
         arbolS.setArbol(arbolSp.getArbol());

      }
      return reconoce;
   }
}
