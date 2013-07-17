package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Eof;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class SP extends Produccion {

   public SP() {
      S s = new S();
      this.add(s);
   }

   // S.ArbolH = SP.ArbolH
   // SP.ArbolS = S.ArbolS

   // SP -> S EOF
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS) {
      boolean valida = false;
      ArbolHandler arbolSp = new ArbolHandler();
      valida = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp);
      arbolS.setArbol(arbolSp.getArbol());
      if (valida && sintactic.siguiente.getClass() == Eof.class) {
         valida = true;
      }
      return valida;
   }
}
