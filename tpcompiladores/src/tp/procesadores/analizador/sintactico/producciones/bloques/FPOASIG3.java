package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.bloque.Asignacion;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresion;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class FPOASIG3 extends Produccion {

   public FPOASIG3() {
      SimboloTerminal corchete1 = new SimboloTerminal("[");
      producciones.add(corchete1);
      EXP0 exp1 = null;
      producciones.add(exp1);
      SimboloTerminal corchete2 = new SimboloTerminal("]");
      producciones.add(corchete2);
      SimboloTerminal digual = new SimboloTerminal(":=");
      producciones.add(digual);
      EXP0 exp2 = null;
      producciones.add(exp2);
      SimboloTerminal pyc = new SimboloTerminal(";");
      producciones.add(pyc);
   }

   // FPOASIG -> [EXP] := EXP;
   // PARA LA LLAMADA DESDE EXP ENTRA ACA
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      Asignacion asignacion = new Asignacion();

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         NodoExpresion expresion1 = new NodoExpresion();
         producciones.set(1, new EXP0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
         expresion1.add(arbolSp1.getArbol());
         arbolH.add(expresion1);
         asignacion.add(arbolH);
         if (reconoce) {
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (reconoce) {
               reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic);
               if (reconoce) {
                  ArbolHandler arbolSp2 = new ArbolHandler();
                  NodoExpresion expresion2 = new NodoExpresion();
                  producciones.set(4, new EXP0());
                  reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp2, tablaH);
                  expresion2.add(arbolSp2.getArbol());
                  asignacion.add(expresion2);
                  arbolS.setArbol(asignacion);
                  if (reconoce) {
                     reconoce = producciones.get(5).reconocer(lexic, visitor, sintactic);
                     if (!reconoce) {
                        merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
                        sintactic.setEstadoAnalisis(false);
                        reconoce = true;
                     }
                  }
               } else {
                  merrores.mostrarYSkipearError("Se espera operador de asignacion ':='", lexic, sintactic, visitor);
                  sintactic.setEstadoAnalisis(false);
                  reconoce = true;
               }
            } else {
               merrores.mostrarYSkipearError("Se espera corchete ']'", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               reconoce = true;
            }
         }
      }
      return reconoce;
   }
}