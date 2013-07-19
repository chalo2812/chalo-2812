package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Funcion;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class FP0 extends Produccion {

   public FP0() {
      FUNCION0 funcion = new FUNCION0();
      producciones.add(funcion);
      PROCEDIMIENTO0 procedimiento = new PROCEDIMIENTO0();
      producciones.add(procedimiento);
   }

   // FP -> FUNCION | PROCEDIMIENTO
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH, TSHandler tablaS) {
      boolean reconoce;
      if (sintactic.siguiente.accept(visitor).equals("funcion")) {
         Funcion funcion = new Funcion();
         ArbolHandler arbolSp1 = new ArbolHandler();
         TSHandler tablaSp1 = new TSHandler();
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, funcion, arbolSp1, tablaH, tablaSp1);
         arbolH.add(arbolSp1.getArbol());
         arbolS.setArbol(arbolH);
         tablaS.setTabla(tablaSp1.getTabla());
      } else {
         if (sintactic.siguiente.accept(visitor).equals("procedimiento")) {
            Procedimiento procedimiento = new Procedimiento();
            ArbolHandler arbolSp2 = new ArbolHandler();
            TSHandler tablaSp2 = new TSHandler();
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, procedimiento, arbolSp2, tablaH, tablaSp2);
            arbolH.add(arbolSp2.getArbol());
            arbolS.setArbol(arbolH);
            tablaS.setTabla(tablaSp2.getTabla());
         } else {
            reconoce = false;
         }
      }
      return reconoce;
   }
}
