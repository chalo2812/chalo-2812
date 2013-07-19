package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.bloques.BLOQUEP0;
import tp.procesadores.analizador.sintactico.producciones.declaraciones.DECL0;

public class PROCEDIMIENTOP2 extends Produccion {

   public PROCEDIMIENTOP2() {
      DECL0 decl = null;
      producciones.add(decl);
      BLOQUEP0 bloquep = null;
      producciones.add(bloquep);
   }

   // PROCEDIMIENTO' -> DECL BLOQUEP

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH, TSHandler tablaS) {
      boolean reconoce;

      TSHandler tablaSp = new TSHandler();
      ArbolHandler arbolSp1 = new ArbolHandler();
      producciones.set(0, new DECL0());
      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH, tablaSp);
      arbolH.add(tablaSp.getTabla());
      if (sintactic.siguiente.accept(visitor).equals("comenzar")) {
         Bloque bloque = new Bloque();
         ArbolHandler arbolSp2 = new ArbolHandler();
         producciones.set(1, new BLOQUEP0());
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, bloque, arbolSp2, tablaSp.getTabla());
         arbolH.add(arbolSp2.getArbol());
         arbolS.setArbol(arbolH);
         tablaS.setTabla(tablaSp.getTabla());
      } else {
         merrores.mostrarYSkipearError("Se espera palabra reservada 'comenzar'", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
         reconoce = true;
      }
      return reconoce;
   }

}
