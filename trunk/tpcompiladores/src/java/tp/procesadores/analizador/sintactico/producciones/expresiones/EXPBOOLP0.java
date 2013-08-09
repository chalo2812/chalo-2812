package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class EXPBOOLP0 extends Produccion {

   public EXPBOOLP0() {
      EXPBOOLP1 expbool1 = new EXPBOOLP1();
      producciones.add(expbool1);
      EXPBOOLP2 expbool2 = new EXPBOOLP2();
      producciones.add(expbool2);
      EXPBOOLP3 expbool3 = new EXPBOOLP3();
      producciones.add(expbool3);
      EXPBOOLP4 expbool4 = new EXPBOOLP4();
      producciones.add(expbool4);
      EXPBOOLP5 expbool5 = new EXPBOOLP5();
      producciones.add(expbool5);
      EXPBOOLP6 expbool6 = new EXPBOOLP6();
      producciones.add(expbool6);
   }

   // EXPBOOL' -> = EXP |
   // < EXP |
   // != EXP |
   // == EXP |
   // << EXP |
   // !== EXP
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;
      if (sintactic.siguiente.accept(visitor).equals("=")) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
         arbolS.setArbol(arbolSp1.getArbol());
      } else {
         if (sintactic.siguiente.accept(visitor).equals("<")) {
            ArbolHandler arbolSp2 = new ArbolHandler();
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
            arbolS.setArbol(arbolSp2.getArbol());
         } else {
            if (sintactic.siguiente.accept(visitor).equals("!=")) {
               ArbolHandler arbolSp3 = new ArbolHandler();
               reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
               arbolS.setArbol(arbolSp3.getArbol());
            } else {
               if (sintactic.siguiente.accept(visitor).equals("==")) {
                  ArbolHandler arbolSp4 = new ArbolHandler();
                  reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic, arbolH, arbolSp4, tablaH);
                  arbolS.setArbol(arbolSp4.getArbol());
               } else {
                  if (sintactic.siguiente.accept(visitor).equals("<<")) {
                     ArbolHandler arbolSp5 = new ArbolHandler();
                     reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic, arbolH, arbolSp5, tablaH);
                     arbolS.setArbol(arbolSp5.getArbol());
                  } else {
                     if (sintactic.siguiente.accept(visitor).equals("!==")) {
                        ArbolHandler arbolSp6 = new ArbolHandler();
                        reconoce = producciones.get(5).reconocer(lexic, visitor, sintactic, arbolH, arbolSp6, tablaH);
                        arbolS.setArbol(arbolSp6.getArbol());
                     } else {
                        merrores.mostrarYSkipearError("Se espera operador booleano { '=', '<', '!=', '==', '<<', '!=='} ", lexic, sintactic, visitor);
                        sintactic.setEstadoAnalisis(false);
                        reconoce = true;
                     }
                  }
               }
            }
         }
      }
      return reconoce;
   }

}
