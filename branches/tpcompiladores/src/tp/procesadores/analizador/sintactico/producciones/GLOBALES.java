package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Globales;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.declaraciones.DECGL0;

public class GLOBALES extends ProduccionC {

	public GLOBALES(){
		PalabraReservada globales = new PalabraReservada("globales");
		producciones.add(globales);
		DECGL0 decl = null; 
		producciones.add(decl);
		PalabraReservada finGlobales = new PalabraReservada("fin-globales");
		producciones.add(finGlobales);
		SimboloTerminal pycoma = new SimboloTerminal(";");
		producciones.add(pycoma);
	}
	
	
	//globales = new NodoGlobales() 
	//DECGL.TablaSimboloH = new TablaSimbolos() 
	//globales.add ( DECGL.TablaSimbolosS ) 
	//GLOBALES.ArbolH.add ( globales ) 
	//GLOBALES.ArbolS = GLOBALES.ArbolH 

	//GLOBALES  ->   globales DECGL fin-globales; | e
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS) 
	{
		boolean r; 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
//		System.out.println("GLOBALES");
		if ( r )
		{
			Globales globales = new Globales();
			TSHandler tablaSp = new TSHandler();
			producciones.set(1, new DECGL0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, tablaH, tablaSp);
			globales.add(tablaSp.getTabla());
			arbolH.add(globales);
			arbolS.setArbol(arbolH);
			tablaS.setTabla(tablaSp.getTabla());
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if (!r)
					{
						merrores.mostrarYSkipearError("Falta punto y coma ';'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
				}else
				{
					merrores.mostrarYSkipearError("Se espera palabra reservada 'fin-globales'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}else
		{
			arbolS.setArbol(arbolH);
			r = true;
		}
		return r;
	}
}
