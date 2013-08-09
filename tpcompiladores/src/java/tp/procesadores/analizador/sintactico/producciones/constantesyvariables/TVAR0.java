package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class TVAR0 extends Produccion {
 
   public TVAR0() {
      TVAR1 tvar1 = null;
      producciones.add(tvar1);
      TVAR2 tvar2 = null;
      producciones.add(tvar2);
   }

   // TVAR -> , VARS' | : TIPO INI VARS''
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      boolean reconoce = false;
      if (sintactic.siguiente.accept(visitor).equals(",")) {
         LVarHandler listaSp1 = new LVarHandler();
         producciones.set(0, new TVAR1());
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
         listaS.setLista(listaSp1.getLista());
      } else {
         if (sintactic.siguiente.accept(visitor).equals(":")) {
            LVarHandler listaSp2 = new LVarHandler();
            producciones.set(1, new TVAR2());
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp2);
            listaS.setLista(listaSp2.getLista());
         } else {
            merrores.mostrarYSkipearError("Se esperaba un ':' mas tipo e inicializacion de variable o ',' y otro identificador", lexic, sintactic,
                                          visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
