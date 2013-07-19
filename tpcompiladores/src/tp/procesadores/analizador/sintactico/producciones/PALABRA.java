package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.general.Identificador;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ElementoIdentificador;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.MetodoHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ParametroHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class PALABRA extends Produccion {

   @Override
   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
                            TablaDeSimbolos tablaH) {
      if (sintactic.siguiente.getClass() == Palabra.class) {
         Identificador identificador = new Identificador(sintactic.siguiente.accept(visitor), "");
         identificador.add(tablaH);
         identificador.setContexto(tablaH.getContexto());
         sintactic.consumir(lexic);
         arbolS.setArbol(identificador);
      } else {
         merrores.mostrarYSkipearError("Se espera identificador", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
      }
      return true;
   }

   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaConstantes listaH, LConstHandler listaS) {
      if (sintactic.siguiente.getClass() == Palabra.class) {
         ElementoIdentificador identificador = new ElementoIdentificador();
         identificador.setLexema(sintactic.siguiente.accept(visitor));
         sintactic.consumir(lexic);
         listaH.identificadores.add(identificador);
         listaS.setLista(listaH);
      } else {
         merrores.mostrarYSkipearError("Se espera identificador", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
      }
      return true;
   }

   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, ListaVariables listaH, LVarHandler listaS) {
      if (sintactic.siguiente.getClass() == Palabra.class) {
         ElementoIdentificador identificador = new ElementoIdentificador();
         identificador.setLexema(sintactic.siguiente.accept(visitor));
         sintactic.consumir(lexic);
         listaH.identificadores.add(identificador);
         listaS.setLista(listaH);
      } else {
         merrores.mostrarYSkipearError("Se espera identificador", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
      }
      return true;
   }

   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, Metodo metodoH, MetodoHandler metodoS) {
      if (sintactic.siguiente.getClass() == Palabra.class) {
         metodoH.setNombre(sintactic.siguiente.accept(visitor));
         metodoS.setMetodo(metodoH);
         sintactic.consumir(lexic);
      } else {
         merrores.mostrarYSkipearError("Se espera identificador", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
      }
      return true;
   }

   public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, Parametro parametroH, ParametroHandler parametroS) {
      if (sintactic.siguiente.getClass() == Palabra.class) {
         parametroH.setLexema(sintactic.siguiente.accept(visitor));
         parametroS.setParametro(parametroH);
         sintactic.consumir(lexic);
      } else {
         merrores.mostrarYSkipearError("Se espera identificador", lexic, sintactic, visitor);
         sintactic.setEstadoAnalisis(false);
      }
      return true;
   }
}
