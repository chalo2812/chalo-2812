package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Programa;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class S extends Produccion {

   public S() {
      GLOBALES globales = null;
      producciones.add(globales);
      SUBRUTINA subrutina = null;
      producciones.add(subrutina);
   }

   // S -> GLOBALES SUBRUTINA
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS) {
      boolean reconoce;

      Programa programa = new Programa();
      TSHandler tablaSp1 = new TSHandler();
      arbolS.setArbol(programa);
      if (sintactic.siguiente.accept(visitor).equals("globales")) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(0, new GLOBALES());
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, programa, arbolSp1, new TablaDeSimbolos(), tablaSp1);
         arbolS.setArbol(arbolSp1.getArbol());
      }

      if ((sintactic.siguiente.accept(visitor).equals("funcion")) || (sintactic.siguiente.accept(visitor).equals("procedimiento"))) {
         ArbolHandler arbolSp2 = new ArbolHandler();
         TSHandler tablaSp2 = new TSHandler();
         producciones.set(1, new SUBRUTINA());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolS.getArbol(), arbolSp2, tablaSp1.getTabla(), tablaSp2);
         arbolS.setArbol(arbolSp2.getArbol());
         if (!tablaSp2.getTabla().verificiarMetodoMain()) {
            sintactic.setEstadoAnalisis(false);
            merrores.mostrarErrorSemantico("El procedimiento Principal() no esta declarado o se encuentra fuera del contexto principal", sintactic);
         }

      } else {
         merrores.mostrarYSkipearError("Se espera palabra reservada 'funcion' o 'procedimiento'", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
         reconoce = true;
      }
      return reconoce;
   }

}
