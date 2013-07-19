package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class EXPBOOL3 extends Produccion {

   public EXPBOOL3() {
      OR or = new OR();
      producciones.add(or);
   }

   // EXPBOOL -> OR
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;
      ArbolHandler arbolSp = new ArbolHandler();
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
      arbolS.setArbol(arbolSp.getArbol());
      return reconoce;
   }
}
