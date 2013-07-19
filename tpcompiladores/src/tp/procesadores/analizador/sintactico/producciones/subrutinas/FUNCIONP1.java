package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class FUNCIONP1 extends Produccion {

   public FUNCIONP1() {
      PalabraReservada adelantado = new PalabraReservada("adelantado");
      producciones.add(adelantado);
      SimboloTerminal pycoma = new SimboloTerminal(";");
      producciones.add(pycoma);
   }

   // FUNCION' -> adelantado;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
      boolean reconoce;
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic);
         if (!reconoce) {
            merrores.mostrarYSkipearError("Falta punto y coma ';'", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }

}
