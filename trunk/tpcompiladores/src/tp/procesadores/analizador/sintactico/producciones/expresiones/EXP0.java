package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class EXP0 extends Produccion {

   public EXP0() {
      TERM0 term = new TERM0();
      EXPP0 expp = new EXPP0();
      this.add(term);
      this.add(expp);
   }

   // EXP'.ArbolH = TERM.ArbolS
   // EXP.ArbolS = EXP'.ArbolS

   // EXP -> TERM EXP'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean valida;

      ArbolHandler arbolSp = new ArbolHandler();
      valida = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
      if (valida) {
         ArbolHandler arbolSp2 = new ArbolHandler();
         valida = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp.getArbol(), arbolSp2, tablaH);
         arbolS.setArbol(arbolSp2.getArbol());
      }
      return valida;
   }
}
