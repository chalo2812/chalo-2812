package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class BLOQUESIP1 extends Produccion {

   public BLOQUESIP1() {
      PalabraReservada finsi = new PalabraReservada("fin-si");
      producciones.add(finsi);
      SimboloTerminal pyc = new SimboloTerminal(";");
      producciones.add(pyc);
   }

   // BLOQUESIP0 -> fin-si;
   // BLOQUESI'.ArbolS = BLOQUESI'.ArbolS

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS) {
      boolean error;
      error = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (error) {
         error = producciones.get(1).reconocer(lexic, visitor, sintactic);
         arbolS.setArbol(arbolH);
         if (!error) {
            merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            error = true;
         }
      }
      return error;
   }
}