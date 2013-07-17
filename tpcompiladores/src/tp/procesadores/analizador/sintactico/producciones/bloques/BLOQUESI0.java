package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresionBooleana;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXPBOOL0;

public class BLOQUESI0 extends Produccion {

   public BLOQUESI0() {
      PalabraReservada si = new PalabraReservada("si");
      producciones.add(si);
      EXPBOOL0 expbool = null;
      producciones.add(expbool);
      PalabraReservada entonces = new PalabraReservada("entonces");
      producciones.add(entonces);
      BLOQUE0 bloque = null;
      producciones.add(bloque);
      BLOQUESIP0 bloquesip = null;
      producciones.add(bloquesip);
   }

   // BLOQUESI -> si EXPBLOQUE entonces BLOQUE BLOQUESI'
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean error;
      error = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (error) {
         NodoExpresionBooleana expresion = new NodoExpresionBooleana();
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(1, new EXPBOOL0());
         error = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
         expresion.add(arbolSp1.getArbol());
         arbolH.add(expresion);
         if (error) {
            error = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (error) {
               ArbolHandler arbolSp2 = new ArbolHandler();
               producciones.set(3, new BLOQUE0());
               error = producciones.get(3).reconocer(lexic, visitor, sintactic, new Bloque(), arbolSp2, tablaH);
               arbolH.add(arbolSp2.getArbol());
               if (error) {
                  ArbolHandler arbolSp3 = new ArbolHandler();
                  producciones.set(4, new BLOQUESIP0());
                  error = producciones.get(4).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
                  arbolS.setArbol(arbolSp3.getArbol());
               }
            } else {
               merrores.mostrarYSkipearError("Se espera palabra reservada 'entonces'", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               error = true;
            }
         }
      }
      return error;
   }
}