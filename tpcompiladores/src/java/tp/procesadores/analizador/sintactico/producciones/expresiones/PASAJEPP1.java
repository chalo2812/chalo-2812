package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class PASAJEPP1 extends Produccion {

   public PASAJEPP1() {
      PALABRA palabra = new PALABRA();
      producciones.add(palabra);
      PASAJEP0 pasaje = new PASAJEP0();
      producciones.add(pasaje);
   }

   // PASAJE'' -> PALABRA PASAJE'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      ArbolHandler arbolSp1 = new ArbolHandler();
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
      arbolH.add(arbolSp1.getArbol());
      if (reconoce) {
         ArbolHandler arbolSp2 = new ArbolHandler();
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
         arbolS.setArbol(arbolSp2.getArbol());
      }
      return reconoce;
   }

}