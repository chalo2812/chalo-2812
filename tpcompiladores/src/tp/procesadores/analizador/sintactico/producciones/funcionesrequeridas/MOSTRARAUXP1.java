package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class MOSTRARAUXP1 extends Produccion {
   
   public MOSTRARAUXP1() {
      SimboloTerminal coma = new SimboloTerminal(",");
      producciones.add(coma);
      MOSTRARAUX0 mostraraux0 = null;
      producciones.add(mostraraux0);
   }

   // MOSTRARAUX' -> , MOSTRARAUX0
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         ArbolHandler arbolSp = new ArbolHandler();
         producciones.set(1, new MOSTRARAUX0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
         arbolS.setArbol(arbolSp.getArbol());
      }
      return reconoce;
   }

}
