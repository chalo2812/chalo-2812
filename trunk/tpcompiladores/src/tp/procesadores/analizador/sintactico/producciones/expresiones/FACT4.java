package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.AENTERO0;

public class FACT4 extends Produccion {

   public FACT4() {
      AENTERO0 aentero = null;
      producciones.add(aentero);
   }

   // FACT -> AENTERO
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce = false;
      if (sintactic.siguiente.accept(visitor).equals("aentero")) {
         producciones.set(0, new AENTERO0());
         ArbolHandler arbolSp = new ArbolHandler();
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
         arbolS.setArbol(arbolSp.getArbol());
      }
      return reconoce;
   }
}
