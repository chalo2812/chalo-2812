package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.bloque.Sino;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class BLOQUESIP0 extends Produccion {

   public BLOQUESIP0() {
      BLOQUESIP1 sip1 = null;
      producciones.add(sip1);
      BLOQUESIP2 sip2 = null;
      producciones.add(sip2);
   }

   // BLOQUESIP0 -> fin-si; | sino BLOQUE fin-si;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean error;
      if (sintactic.siguiente.accept(visitor).equals("fin-si")) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(0, new BLOQUESIP1());
         error = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1);
         arbolS.setArbol(arbolH);
      } else {
         if (sintactic.siguiente.accept(visitor).equals("sino")) {
            ArbolHandler arbolSp = new ArbolHandler();
            producciones.set(1, new BLOQUESIP2());
            error = producciones.get(1).reconocer(lexic, visitor, sintactic, new Sino(), arbolSp, tablaH);
            arbolH.add(arbolSp.getArbol());
            arbolS.setArbol(arbolH);
         } else {
            merrores.mostrarYSkipearError("Se espara palabra reservada 'fin-si' o bloque 'sino .. fin-si'", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            error = true;
         }
      }
      return error;
   }
}