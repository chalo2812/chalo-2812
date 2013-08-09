package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class DECGL0 extends Produccion {

   public DECGL0() {
      DECGLOBAL0 decglobal = null;
      producciones.add(decglobal);
      DECGL0 decgl = null;
      producciones.add(decgl);
   }

   // DECGL -> DECGLOBAL DECGL | e
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, TablaDeSimbolos tablaH, TSHandler tablaS) {
      boolean reconoce;
      TSHandler tablaSp1 = new TSHandler();
      producciones.set(0, new DECGLOBAL0());
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, tablaH, tablaSp1);
      if (reconoce) {
         TSHandler tablaSp2 = new TSHandler();
         producciones.set(1, new DECGL0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, tablaSp1.getTabla(), tablaSp2);
         tablaS.setTabla(tablaSp2.getTabla());
      } else {
         tablaS.setTabla(tablaH);
         reconoce = true;
      }
      return reconoce;
   }
}
