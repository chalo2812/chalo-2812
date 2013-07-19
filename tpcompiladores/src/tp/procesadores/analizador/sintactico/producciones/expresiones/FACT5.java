package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.ANATURAL0;

public class FACT5 extends Produccion {

   public FACT5() {
      ANATURAL0 anatural = null;
      producciones.add(anatural);
   }

   // FACT -> ANATURAL
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce = false;
      if (sintactic.siguiente.accept(visitor).equals("anatural")) {
         ArbolHandler arbolSp = new ArbolHandler();
         producciones.set(0, new ANATURAL0());
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
         arbolS.setArbol(arbolSp.getArbol());
      }
      return reconoce;
   }
}
