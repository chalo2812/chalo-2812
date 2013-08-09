package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class VARSPP0 extends Produccion {

   public VARSPP0() {
      VARSPP1 varspp1 = null;
      producciones.add(varspp1);
      VARSPP2 varspp2 = new VARSPP2();
      producciones.add(varspp2);
   }

   // VARS' -> , VARS | ;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      boolean reconoce;
      producciones.set(0, new VARSPP1());
      if (sintactic.siguiente.accept(visitor).equals(",")) {
         LVarHandler listaSp1 = new LVarHandler();
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
         listaS.setLista(listaSp1.getLista());
      } else {
         if (sintactic.siguiente.accept(visitor).equals(";")) {
            LVarHandler listaSp2 = new LVarHandler();
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp2);
            listaS.setLista(listaSp2.getLista());
         } else {
            merrores.mostrarYSkipearError("Se esperaba una nueva variable o un fin de linea", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
