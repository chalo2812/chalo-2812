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
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.parametrizaciones.PARAMS0;

public class ENCABEZADOP0 extends ProduccionC {

		public ENCABEZADOP0(){
			PalabraReservada procedimiento = new PalabraReservada("procedimiento");
			producciones.add(procedimiento);
			PALABRA palabra = new PALABRA();
			producciones.add(palabra);
			SimboloTerminal parentesis1 = new SimboloTerminal("(");
			producciones.add(parentesis1);
			PARAMS0 params =  null;
			producciones.add(params);
			SimboloTerminal parentesis2 = new SimboloTerminal(")");
			producciones.add(parentesis2);
			SimboloTerminal puntoycoma = new SimboloTerminal(";");
			producciones.add(puntoycoma);
		}
	
		//ENCABEZADOP 	->   procedimiento PALABRA(PARAMS);
		@Override 
		public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
				Metodo metodoH, MetodoHandler metodoS){
			boolean r; 
//			System.out.println("ENCABEZADOP");
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			if ( r )
			{ 
				metodoH.setEsFuncion(false);
				MetodoHandler metodoSp = new MetodoHandler();
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, metodoH, metodoSp);
				if ( r )
				{ 
					r = producciones.get(2).reconocer(lexic, visitor, sintactic); 
					if ( r )
					{
						List<Parametro> parametrosH = new ArrayList<Parametro>();
						ListaParametrosHandler parametrosS = new ListaParametrosHandler();
						producciones.set(3, new PARAMS0());
						r = producciones.get(3).reconocer(lexic, visitor, sintactic, parametrosH, parametrosS);
						metodoSp.getMetodo().setParametros(parametrosS.getParametros());
						if ( r )
						{
							r = producciones.get(4).reconocer(lexic, visitor, sintactic); 
							if ( r )
							{
								r = producciones.get(5).reconocer(lexic, visitor, sintactic);
								metodoS.setMetodo(metodoSp.getMetodo());
								if (!r)
								{
									merrores.mostrarYSkipearError("Falta punto y coma ';'", lexic, sintactic, visitor);
									sintactic.setEstadoAnalisis(false);
									r = true;
								}
							}
						}
					}else
					{
						merrores.mostrarYSkipearError("Se espera parentesis '(' ", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
				}
			}
			return r;
		}
}
