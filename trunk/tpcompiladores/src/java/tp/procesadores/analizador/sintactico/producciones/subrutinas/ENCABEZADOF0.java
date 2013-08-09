package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import java.util.ArrayList;
import java.util.List;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaParametrosHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.MetodoHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.TIPO0;
import tp.procesadores.analizador.sintactico.producciones.parametrizaciones.PARAMS0;

public class ENCABEZADOF0 extends Produccion {

   public ENCABEZADOF0() {
      PalabraReservada funcion = new PalabraReservada("funcion");
      producciones.add(funcion);
      PALABRA palabra = new PALABRA();
      producciones.add(palabra);
      SimboloTerminal parentesis1 = new SimboloTerminal("(");
      producciones.add(parentesis1);
      PARAMS0 params = null;
      producciones.add(params);
      SimboloTerminal parentesis2 = new SimboloTerminal(")");
      producciones.add(parentesis2);
      SimboloTerminal dospuntos = new SimboloTerminal(":");
      producciones.add(dospuntos);
      TIPO0 tipo = null;
      producciones.add(tipo);
      SimboloTerminal puntoycoma = new SimboloTerminal(";");
      producciones.add(puntoycoma);
   }

   // ENCABEZADOF -> funcion PALABRA(PARAMS): TIPO;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, Metodo metodoH, MetodoHandler metodoS) {
      boolean reconoce;

      reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (reconoce) {
         metodoH.setEsFuncion(true);
         MetodoHandler metodoSp1 = new MetodoHandler();
         reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, metodoH, metodoSp1);
         if (reconoce) {
            reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (reconoce) {
               List<Parametro> parametrosH = new ArrayList<Parametro>();
               ListaParametrosHandler parametrosS = new ListaParametrosHandler();
               producciones.set(3, new PARAMS0());
               reconoce = producciones.get(3).reconocer(lexic, visitor, sintactic, parametrosH, parametrosS);
               metodoSp1.getMetodo().setParametros(parametrosS.getParametros());
               if (reconoce) {
                  reconoce = producciones.get(4).reconocer(lexic, visitor, sintactic);
                  if (reconoce) {
                     reconoce = producciones.get(5).reconocer(lexic, visitor, sintactic);
                     if (reconoce) {
                        MetodoHandler metodoSp2 = new MetodoHandler();
                        producciones.set(6, new TIPO0());
                        reconoce = producciones.get(6).reconocer(lexic, visitor, sintactic, metodoSp1.getMetodo(), metodoSp2);
                        metodoS.setMetodo(metodoSp2.getMetodo());
                        if (reconoce) {
                           reconoce = producciones.get(7).reconocer(lexic, visitor, sintactic);
                           if (!reconoce) {
                              merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
                              sintactic.setEstadoAnalisis(false);
                              reconoce = true;
                           }
                        }
                     } else {
                        merrores.mostrarYSkipearError("Se espera asignacion de tipo ': tipo'", lexic, sintactic, visitor);
                        sintactic.setEstadoAnalisis(false);
                        reconoce = true;
                     }
                  }
               }
            } else {
               merrores.mostrarYSkipearError("Se espera parentesis '(' ", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               reconoce = true;
            }
         }
      }
      if (!reconoce) {
         sintactic.setEstadoAnalisis(false);
      }
      return reconoce;
   }
}
