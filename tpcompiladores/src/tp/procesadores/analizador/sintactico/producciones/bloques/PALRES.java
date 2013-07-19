package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.palres.Leer;
import tp.procesadores.analizador.semantico.arbol.palres.Mostrar;
import tp.procesadores.analizador.semantico.arbol.palres.MostrarLn;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.LEER0;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.MOSTRAR0;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.MOSTRARLN0;

public class PALRES extends Produccion {

   public PALRES() {
      LEER0 leer = null;
      producciones.add(leer);
      MOSTRAR0 mostrar = null;
      producciones.add(mostrar);
      MOSTRARLN0 mostrarln = null;
      producciones.add(mostrarln);
   }

   // PALRES -> LEER | MOSTRAR | MOSTRARLN
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean r = false;
      if (sintactic.siguiente.accept(visitor).equals("leer")) {
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(0, new LEER0());
         r = producciones.get(0).reconocer(lexic, visitor, sintactic, new Leer(), arbolSp1, tablaH);
         arbolH.add(arbolSp1.getArbol());
         arbolS.setArbol(arbolH);
      } else {
         if (sintactic.siguiente.accept(visitor).equals("mostrar")) {
            ArbolHandler arbolSp2 = new ArbolHandler();
            producciones.set(1, new MOSTRAR0());
            r = producciones.get(1).reconocer(lexic, visitor, sintactic, new Mostrar(), arbolSp2, tablaH);
            arbolH.add(arbolSp2.getArbol());
            arbolS.setArbol(arbolH);
         } else {
            if (sintactic.siguiente.accept(visitor).equals("mostrarln")) {
               ArbolHandler arbolSp3 = new ArbolHandler();
               producciones.set(2, new MOSTRARLN0());
               r = producciones.get(2).reconocer(lexic, visitor, sintactic, new MostrarLn(), arbolSp3, tablaH);
               arbolH.add(arbolSp3.getArbol());
               arbolS.setArbol(arbolH);
            }
         }
      }
      return r;
   }
}