package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ElementoIdentificador;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class NUMERO extends Produccion {

   public NUMERO() {
      ENTERO entero = new ENTERO();
      producciones.add(entero);
      NATURAL natural = new NATURAL();
      producciones.add(natural);
   }

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS) {
      boolean reconoce;
      if (sintactic.siguiente.getClass() == Entero.class) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1);
         arbolS.setArbol(arbolSp1.getArbol());
      } else {
         if (sintactic.siguiente.getClass() == Natural.class) {
            ArbolHandler arbolSp2 = new ArbolHandler();
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2);
            arbolS.setArbol(arbolSp2.getArbol());
         } else {
            reconoce = false;
         }
      }
      return reconoce;
   }

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ElementoIdentificador elemento) {
      boolean reconoce;
      if (sintactic.siguiente.getClass() == Entero.class) {
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, elemento);
      } else {
         if (sintactic.siguiente.getClass() == Natural.class) {
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, elemento);
         } else {
            reconoce = false;
         }
      }
      return reconoce;
   }

}
