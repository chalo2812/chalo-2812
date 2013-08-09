package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class FACT3 extends Produccion {

   public FACT3() {
      NUMERO numero = new NUMERO();
      producciones.add(numero);
   }

   // FACT -> NUMERO
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;
      ArbolHandler arbolSp = new ArbolHandler();
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp);
      arbolS.setArbol(arbolSp.getArbol());
      return reconoce;
   }

}
