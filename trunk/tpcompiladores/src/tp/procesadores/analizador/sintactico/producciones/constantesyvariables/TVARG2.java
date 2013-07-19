package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TVARG2 extends Produccion {

   public TVARG2() {
      SimboloTerminal dosPuntos = new SimboloTerminal(":");
      producciones.add(dosPuntos);
      TIPO0 tipo = null;
      producciones.add(tipo);
      VARSGPP0 varsgpp = null;
      producciones.add(varsgpp);
   }

   // TVARG -> : TIPO VARSGPP
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {

      boolean reconoce;
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         producciones.set(1, new TIPO0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH.getLastElement());
         if (reconoce) {
            LVarHandler listaSp = new LVarHandler();
            producciones.set(2, new VARSGPP0());
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, listaH, listaSp);
            listaS.setLista(listaSp.getLista());
         }
      }
      return reconoce;
   }
}