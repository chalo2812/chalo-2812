package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class CONSTSP0 extends Produccion {

   public CONSTSP0() {
      PALABRA palabra = new PALABRA();
      producciones.add(palabra);
      TCONSTS0 tconsts = null;
      producciones.add(tconsts);
   }

   // CONSTS' -> PALABRA TCONSTS
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaConstantes listaH, LConstHandler listaS) {
      boolean reconoce;

      LConstHandler listaSp1 = new LConstHandler();
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
      if (reconoce) {
         LConstHandler listaSp2 = new LConstHandler();
         producciones.set(1, new TCONSTS0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, listaSp1.getLista(), listaSp2);
         listaS.setLista(listaSp2.getLista());
      } else {
         merrores.mostrarYSkipearError("Se esperaba un identificador", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
         reconoce = true;
      }
      return reconoce;
   }
}
