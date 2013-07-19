package tp.procesadores.analizador.sintactico.producciones.bloques;

import java.util.List;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.FilaTabla;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class FPOASIG0 extends Produccion {

   public FPOASIG0() {
      FPOASIG1 fpoasig1 = null;
      producciones.add(fpoasig1);
      FPOASIG2 fpoasig2 = null;
      producciones.add(fpoasig2);
      FPOASIG3 fpoasig3 = null;
      producciones.add(fpoasig3);
   }

   // FPOASIG -> (PASAJE); | := EXP; | [EXP] := EXP;
   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      boolean reconoce;
      if (sintactic.siguiente.accept(visitor).equals("(")) {
         NodeVisitor identVisitor = new NodeVisitor();
         identVisitor.setContexto(null);
         identVisitor.setLexema(null);
         if (!tablaH.existeMetodo(arbolH.accept(identVisitor))) {
            identVisitor.setContexto(tablaH.metodos == null ? tablaH.metodos.get(0).getNombre() : "");
            identVisitor.setLexema(tablaH.metodos == null ? tablaH.getMetodo(arbolH.accept(identVisitor)).getNombre() : "");
         }
         if (!tablaH.existeMetodo(arbolH.accept(identVisitor))) {
            merrores.mostrarErrorSemantico("El metodo \'" + arbolH.accept(identVisitor) + "\' NO esta declarado", sintactic);
            sintactic.setEstadoAnalisis(false);
         }
         ArbolHandler arbolSp1 = new ArbolHandler();
         producciones.set(0, new FPOASIG1());
         reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
         arbolS.setArbol(arbolSp1.getArbol());
      } else {
         if (sintactic.siguiente.accept(visitor).equals(":=")) {
            NodeVisitor identVisitor = new NodeVisitor();
            if (tablaH.entradas.size() != 0) {
               identVisitor.setContexto(tablaH.entradas.get(tablaH.entradas.size() - 1).getTipo());
               identVisitor.setLexema(tablaH.entradas.get(tablaH.entradas.size() - 1).getId());
            }
            if (!tablaH.existeId(arbolH.accept(identVisitor))) {
               if (!tablaH.esParametroDelContexto(arbolH.accept(identVisitor))) {

                  identVisitor.setLexema(tablaH.padre.entradas != null && tablaH.padre.entradas.size() > 0 ? obtenerIdentificador(tablaH.padre.entradas,
                                                                                                                                  arbolH): "");
                  merrores.mostrarErrorSemantico("El identificador \'" + arbolH.accept(identVisitor) + "\' NO esta declarado", sintactic);
                  sintactic.setEstadoAnalisis(false);
               }
            } else {
               if (!tablaH.esVariable(arbolH.accept(identVisitor))) {
                  merrores.mostrarErrorSemantico("No se puede realizar una asignación a una constante: \'" + sintactic.actual.accept(visitor) + "\'",
                                                 sintactic);
                  sintactic.setEstadoAnalisis(false);
               }
            }
            ArbolHandler arbolSp2 = new ArbolHandler();
            producciones.set(1, new FPOASIG2());
            reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
            arbolS.setArbol(arbolSp2.getArbol());
         } else {
            if (sintactic.siguiente.accept(visitor).equals("[")) {
               ArbolHandler arbolSp3 = new ArbolHandler();
               producciones.set(2, new FPOASIG3());
               reconoce = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
               arbolS.setArbol(arbolSp3.getArbol());
            } else {
               merrores.mostrarYSkipearError("Se espera alguno de los siguientes operadores {'(',':=','['}", lexic, sintactic, visitor);
               sintactic.setEstadoAnalisis(false);
               reconoce = true;
            }
         }
      }
      return reconoce;
   }

   private String obtenerIdentificador(List<FilaTabla> entradas, ClaseNodo lexico) {
      for (int i = 0; i < entradas.size(); i++) {
         if (entradas.get(i).getId().equals(lexico.getLexema()))
            return lexico.getLexema();
      }
      return null;
   }
}