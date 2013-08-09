package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NATURAL;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TVARG3 extends Produccion {

   public TVARG3() {
      SimboloTerminal corchete1 = new SimboloTerminal("[");
      producciones.add(corchete1);
      NATURAL numero = new NATURAL();
      producciones.add(numero);
      SimboloTerminal corchete2 = new SimboloTerminal("]");
      producciones.add(corchete2);
      VECT0 vect = null;
      producciones.add(vect);
      VARSGPP0 varsgpp = null;
      producciones.add(varsgpp);
   }

   // TVARG -> [ NATURAL ] VECT VARSGPP
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      boolean reconoce;
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH.getLastElement());
         if (reconoce) {
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (reconoce) {
               if (sintactic.siguiente.accept(visitor).equals(":")) {
                  LVarHandler listaSp1 = new LVarHandler();
                  producciones.set(3, new VECT0());
                  reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
                  if (reconoce) {
                     LVarHandler listaSp2 = new LVarHandler();
                     producciones.set(4, new VARSGPP0());
                     reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic, listaSp1.getLista(), listaSp2);
                     listaS.setLista(listaSp2.getLista());
                  }
               } else {
                  if (sintactic.siguiente.accept(visitor).equals(";")) {
                     merrores.mostrarYSkipearError("Se esperaba un ':' mas definicion de variables/vector o ',' y otro identificador ", lexic,
                                                   sintactic, visitor);
                     sintactic.setEstadoAnalisis(false);
                     reconoce = true;
                  } else {
                     producciones.set(4, new VARSGPP0());
                     reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic);
                  }
               }
            } else {
               merrores.mostrarYSkipearError("Se esperaba simbolo ']'", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               reconoce = true;
            }
         }
      }
      return reconoce;
   }
}