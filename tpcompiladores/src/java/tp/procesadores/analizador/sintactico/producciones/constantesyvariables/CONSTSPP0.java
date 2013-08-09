package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class CONSTSPP0 extends Produccion {

   public CONSTSPP0() {
      CONSTSPP1 constspp1 = null;
      producciones.add(constspp1);
      CONSTSPP2 constspp2 = null;
      producciones.add(constspp2);
   }

   // CONSTS'' -> , CONSTS' | ;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaConstantes listaH, LConstHandler listaS) {
      boolean reconoce = true;
      // System.out.println("CONSTSPP0");
      if (sintactic.siguiente.accept(visitor).equals(",")) {
         LConstHandler listaSp1 = new LConstHandler();
         producciones.set(0, new CONSTSPP1());
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
         listaS.setLista(listaSp1.getLista());
      } else {
         if (sintactic.siguiente.accept(visitor).equals(";")) {
            LConstHandler listaSp2 = new LConstHandler();
            producciones.set(1, new CONSTSPP2());
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp2);
            listaS.setLista(listaSp2.getLista());
         } else {
            merrores.mostrarYSkipearError("Se esperaba una nueva constante o fin de linea", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
