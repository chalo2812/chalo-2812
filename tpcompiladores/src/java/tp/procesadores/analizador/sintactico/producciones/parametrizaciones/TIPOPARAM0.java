package tp.procesadores.analizador.sintactico.producciones.parametrizaciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ParametroHandler;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class TIPOPARAM0 extends Produccion {

   public TIPOPARAM0() {
      TIPOPARAM1 tipoparam1 = new TIPOPARAM1();
      producciones.add(tipoparam1);
      TIPOPARAM2 tipoparam2 = new TIPOPARAM2();
      producciones.add(tipoparam2);
   }

   // TIPOPARAM -> ref | val | e
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, Parametro parametroH, ParametroHandler parametroS) {
      boolean reconoce;
      // System.out.println("TIPOPARAM0");
      if (sintactic.siguiente.accept(visitor).equals("ref")) {
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
         parametroH.setEsPorValor(false);
         parametroS.setParametro(parametroH);
      } else {
         if (sintactic.siguiente.accept(visitor).equals("val")) {
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic);
            parametroH.setEsPorValor(true);
            parametroS.setParametro(parametroH);
         } else {
            parametroH.setEsPorValor(true);
            parametroS.setParametro(parametroH);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
