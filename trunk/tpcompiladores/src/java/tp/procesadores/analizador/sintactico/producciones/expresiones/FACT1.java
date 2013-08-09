package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class FACT1 extends Produccion {

   public FACT1() {
      SimboloTerminal parentesis1 = new SimboloTerminal("(");
      producciones.add(parentesis1);
      EXP0 exp = null;
      producciones.add(exp);
      SimboloTerminal parentesis2 = new SimboloTerminal(")");
      producciones.add(parentesis2);
   }

   // FACT -> (EXP)
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         producciones.set(1, new EXP0());
         ArbolHandler arbolSp = new ArbolHandler();
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
         arbolS.setArbol(arbolSp.getArbol());
         if (reconoce) {
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (!reconoce) {
               merrores.mostrarYSkipearError("Se espera parentesis ')'", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               reconoce = true;
            }
         }
      }
      return reconoce;
   }
}
