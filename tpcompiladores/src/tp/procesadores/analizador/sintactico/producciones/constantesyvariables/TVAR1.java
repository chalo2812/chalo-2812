package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class TVAR1 extends Produccion {
   public TVAR1() {
      SimboloTerminal comma = new SimboloTerminal(",");
      producciones.add(comma);
      VARSP0 varsp = null;
      producciones.add(varsp);
   }

   // TVAR -> , VARS'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      boolean reconoce;
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         LVarHandler listaSp = new LVarHandler();
         producciones.set(1, new VARSP0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp);
         listaS.setLista(listaSp.getLista());
      }
      return reconoce;
   }
}