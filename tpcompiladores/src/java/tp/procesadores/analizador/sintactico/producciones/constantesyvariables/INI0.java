package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;

public class INI0 extends Produccion {
   public INI0() {
      PalabraReservada igual = new PalabraReservada("=");
      producciones.add(igual);
      NUMERO numero = new NUMERO();
      producciones.add(numero);
   }

   // INI -> = NUMERO | lambda ( ver TVARG2 ahi se trata al caso lambda)
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
      boolean reconoce;
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic);
         if (!reconoce) {
            merrores.mostrarYSkipearError("Se esperaba un entero o un natural", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
