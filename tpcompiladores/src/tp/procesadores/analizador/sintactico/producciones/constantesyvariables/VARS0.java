package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class VARS0 extends Produccion {
   public VARS0() {
      PalabraReservada var = new PalabraReservada("var");
      producciones.add(var);
      VARSP0 varsp = null;
      producciones.add(varsp);
   }

   // VARS -> var VARS'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      boolean reconoce;
      // System.out.println("VARS0");
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
