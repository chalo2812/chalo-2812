package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class VARSG0 extends Produccion {

   public VARSG0() {
      PalabraReservada var = new PalabraReservada("var");
      producciones.add(var);
      VARSGP0 varsgp = null;
      producciones.add(varsgp);
   }

   // VARSG -> var VARSG'
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      boolean reconoce;
      // System.out.println("VARSG0");
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         LVarHandler listaSp = new LVarHandler();
         producciones.set(1, new VARSGP0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp);
         listaS.setLista(listaSp.getLista());
      }
      return reconoce;
   }
}
