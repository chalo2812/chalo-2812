package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.ProductoNatural;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TERMP3 extends Produccion {

   public TERMP3() {
      SimboloTerminal por = new SimboloTerminal("**");
      producciones.add(por);
      FACT0 fact = null;
      producciones.add(fact);
      TERMP0 termp = null;
      producciones.add(termp);
   }

   // TERM' -> ** FACT TERM'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(1, new FACT0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
         if (reconoce) {
            ArbolHandler arbolSp2 = new ArbolHandler();
            ProductoNatural producto = new ProductoNatural(arbolH, arbolSp1.getArbol());
            arbolH = producto;
            producciones.set(2, new TERMP0());
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
            arbolS.setArbol(arbolSp2.getArbol());
         }
      }
      return reconoce;
   }
}
