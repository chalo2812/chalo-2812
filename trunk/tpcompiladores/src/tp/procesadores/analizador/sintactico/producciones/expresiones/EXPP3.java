package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoSumaNatural;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class EXPP3 extends Produccion {

   public EXPP3() {
      SimboloTerminal mas = new SimboloTerminal("++");
      producciones.add(mas);
      TERM0 term = null;
      producciones.add(term);
      EXPP0 expp = null;
      producciones.add(expp);
   }

   // EXP' -> ++ TERM EXP'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(1, new TERM0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
         if (reconoce) {
            ArbolHandler arbolSp2 = new ArbolHandler();
            NodoSumaNatural suma = new NodoSumaNatural(arbolH, arbolSp1.getArbol());
            arbolH = suma;
            producciones.set(2, new EXPP0());
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
            arbolS.setArbol(suma);
         }
      }
      return reconoce;
   }
}