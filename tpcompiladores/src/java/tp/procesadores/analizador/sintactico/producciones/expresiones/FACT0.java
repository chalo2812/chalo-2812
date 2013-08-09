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

public class FACT0 extends Produccion {

   public FACT0() {
      FACT1 fact1 = new FACT1();
      producciones.add(fact1);
      FACT2 fact2 = new FACT2();
      producciones.add(fact2);
      FACT3 fact3 = new FACT3();
      producciones.add(fact3);
      FACT4 fact4 = new FACT4();
      producciones.add(fact4);
      FACT5 fact5 = new FACT5();
      producciones.add(fact5);
   }

   // FACT -> (EXP) |
   // PALABRA FACT' |
   // NUMERO |
   // AENTERO |
   // ANATURAL

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce = true;
      if (sintactic.siguiente.accept(visitor).equals("(")) {
         producciones.set(0, new FACT1());
         ArbolHandler arbolSp1 = new ArbolHandler();
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
         arbolS.setArbol(arbolSp1.getArbol());
      } else {
         if (sintactic.siguiente.getClass() == Palabra.class) {
            producciones.set(1, new FACT2());
            ArbolHandler arbolSp2 = new ArbolHandler();
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
            arbolS.setArbol(arbolSp2.getArbol());
         } else {
            if ((sintactic.siguiente.getClass() == Entero.class) || (sintactic.siguiente.getClass() == Natural.class)) {
               producciones.set(2, new FACT3());
               ArbolHandler arbolSp3 = new ArbolHandler();
               reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
               arbolS.setArbol(arbolSp3.getArbol());
            } else {
               if (sintactic.siguiente.accept(visitor).equals("aentero")) {
                  producciones.set(3, new FACT4());
                  ArbolHandler arbolSp4 = new ArbolHandler();
                  reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic, arbolH, arbolSp4, tablaH);
                  arbolS.setArbol(arbolSp4.getArbol());
               } else {
                  if (sintactic.siguiente.accept(visitor).equals("anatural")) {
                     ArbolHandler arbolSp5 = new ArbolHandler();
                     producciones.set(4, new FACT5());
                     reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic, arbolH, arbolSp5, tablaH);
                     arbolS.setArbol(arbolSp5.getArbol());
                  } else {
                     merrores.mostrarYSkipearError("Se espera Factor", lexic, sintactic, visitor);
                     sintactic.setEstadoAnalisis(false);
                     reconoce = true;
                  }
               }
            }
         }
      }
      return reconoce;
   }

}
