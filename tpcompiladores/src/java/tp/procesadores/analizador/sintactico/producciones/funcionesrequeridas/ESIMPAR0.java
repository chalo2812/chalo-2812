package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.funcionesrequeridas.FuncionEsImpar;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;

public class ESIMPAR0 extends Produccion {

   public ESIMPAR0() {
      SimboloTerminal impar = new SimboloTerminal("impar");
      producciones.add(impar);
      SimboloTerminal parentesisabrir = new SimboloTerminal("(");
      producciones.add(parentesisabrir);
      EXP0 tipo = null;
      producciones.add(tipo);
      SimboloTerminal parentesiscerrar = new SimboloTerminal(")");
      producciones.add(parentesiscerrar);
   }

   // ESIMPAR -> impar ( EXP )
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic);
         if (reconoce) {
            arbolH = new FuncionEsImpar();
            ArbolHandler arbolSp = new ArbolHandler();
            producciones.set(2, new EXP0());
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
            arbolH.add(arbolSp.getArbol());
            arbolS.setArbol(arbolH);
            if (reconoce) {
               reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic);
               if (!reconoce) {
                  merrores.mostrarYSkipearError("Se espera cierre de parentesis ')'", lexic, sintactic, visitor);
                  sintactic.setEstadoAnalisis(false);
                  reconoce = true;
               }
            }
         } else {
            merrores.mostrarYSkipearError("Se espera apertura de parentesis '('", lexic, sintactic, visitor);
            sintactic.setEstadoAnalisis(false);
            reconoce = true;
         }
      }
      return reconoce;
   }
}
