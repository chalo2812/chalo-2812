package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class LEER0 extends Produccion

{
   public LEER0() {
      SimboloTerminal leer = new SimboloTerminal("leer");
      producciones.add(leer);
      PALABRA palabra = new PALABRA();
      producciones.add(palabra);
      PAUX0 paux0 = null;
      producciones.add(paux0);
      SimboloTerminal ptoycoma = new SimboloTerminal(";");
      producciones.add(ptoycoma);
   }

   // LEER -> leer PALABRA PAUX;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
         if (reconoce) {
            ArbolHandler arbolSp2 = new ArbolHandler();
            producciones.set(2, new PAUX0());
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
            arbolH.add(arbolSp2.getArbol());
            if (reconoce) {
               reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic);
               arbolS.setArbol(arbolH);
               if (!reconoce) {
                  merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
                  sintactic.setEstadoAnalisis(false);
                  reconoce = true;
               }
            }
         } else {
            merrores.mostrarYSkipearError("Se espera un identificador", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
