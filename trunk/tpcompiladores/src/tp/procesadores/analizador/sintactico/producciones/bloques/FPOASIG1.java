package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.bloque.LlamadaFP;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.PASAJE0;

public class FPOASIG1 extends Produccion {

   public FPOASIG1() {
      SimboloTerminal parentesis1 = new SimboloTerminal("(");
      producciones.add(parentesis1);
      PASAJE0 pasaje = null;
      producciones.add(pasaje);
      SimboloTerminal parentesis2 = new SimboloTerminal(")");
      producciones.add(parentesis2);
      SimboloTerminal pyc = new SimboloTerminal(";");
      producciones.add(pyc);
   }

   // FPOASIG -> (PASAJE);
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean error;

      LlamadaFP llamadaFP = new LlamadaFP();
      llamadaFP.add(tablaH);
      llamadaFP.add(arbolH);
      error = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (error) {
         ArbolHandler arbolSp = new ArbolHandler();
         producciones.set(1, new PASAJE0());
         error = producciones.get(1).reconocer(lexic, visitor, sintactic, llamadaFP, arbolSp, tablaH);
         arbolS.setArbol(arbolSp.getArbol());
         if (error) {
            error = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (error) {
               error = producciones.get(3).reconocer(lexic, visitor, sintactic);
               if (!error) {
                  merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
                  sintactic.setEstadoAnalisis(false);
                  error = true;
               }
            } else {
               merrores.mostrarYSkipearError("Se espera parentesis ')'", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               error = true;
            }
         }
      }
      return error;
   }
}