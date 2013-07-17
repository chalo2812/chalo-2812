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
import tp.procesadores.analizador.sintactico.producciones.parametrizaciones.PARAMS0;

public class ENCABEZADOP0 extends Produccion {

   public ENCABEZADOP0() {
      PalabraReservada procedimiento = new PalabraReservada("procedimiento");
      producciones.add(procedimiento);
      PALABRA palabra = new PALABRA();
      producciones.add(palabra);
      SimboloTerminal parentesis1 = new SimboloTerminal("(");
      producciones.add(parentesis1);
      PARAMS0 params = null;
      producciones.add(params);
      SimboloTerminal parentesis2 = new SimboloTerminal(")");
      producciones.add(parentesis2);
      SimboloTerminal puntoycoma = new SimboloTerminal(";");
      producciones.add(puntoycoma);
   }

   // ENCABEZADOP -> procedimiento PALABRA(PARAMS);
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, Metodo metodoH, MetodoHandler metodoS) {
      boolean valida;
      // System.out.println("ENCABEZADOP");
      valida = producciones.get(0).reconocer(lexic, visitor, sintactic);
      if (valida) {
         metodoH.setEsFuncion(false);
         MetodoHandler metodoSp = new MetodoHandler();
         valida = producciones.get(1).reconocer(lexic, visitor, sintactic, metodoH, metodoSp);
         if (valida) {
            valida = producciones.get(2).reconocer(lexic, visitor, sintactic);
            if (valida) {
               List<Parametro> parametrosH = new ArrayList<Parametro>();
               ListaParametrosHandler parametrosS = new ListaParametrosHandler();
               producciones.set(3, new PARAMS0());
               valida = producciones.get(3).reconocer(lexic, visitor, sintactic, parametrosH, parametrosS);
               metodoSp.getMetodo().setParametros(parametrosS.getParametros());
               if (valida) {
                  valida = producciones.get(4).reconocer(lexic, visitor, sintactic);
                  if (valida) {
                     valida = producciones.get(5).reconocer(lexic, visitor, sintactic);
                     metodoS.setMetodo(metodoSp.getMetodo());
                     if (!valida) {
                        merrores.mostrarYSkipearError("Falta punto y coma ';'", lexic, sintactic, visitor);
                        sintactic.setEstadoAnalisis(false);
                        valida = true;
                     }
                  }
               }
            } else {
               merrores.mostrarYSkipearError("Se espera parentesis '(' ", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               valida = true;
            }
         }
      }
      return valida;
   }
}
