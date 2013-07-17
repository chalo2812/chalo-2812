package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class BLOQUE0 extends Produccion {

   public BLOQUE0() {
      LINEA0 linea = null;
      producciones.add(linea);
      BLOQUE0 bloque = null;
      producciones.add(bloque);
   }

   // BLOQUE -> LINEA BLOQUE | lambda;

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;
      ArbolHandler arbolSp1 = new ArbolHandler();
      producciones.set(0, new LINEA0());
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
      if (reconoce) {
         ArbolHandler arbolSp2 = new ArbolHandler();
         producciones.set(1, new BLOQUE0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH);
         arbolS.setArbol(arbolSp2.getArbol());
      } else {
         arbolS.setArbol(arbolH);
         reconoce = true;
      }
      return reconoce;
   }

}