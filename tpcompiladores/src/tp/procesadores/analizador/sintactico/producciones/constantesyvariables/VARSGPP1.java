package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class VARSGPP1 extends Produccion {

	public VARSGPP1(){
		SimboloTerminal coma = new SimboloTerminal(",");
		producciones.add(coma);
		VARSGP0 varsgp = null;
		producciones.add(varsgp);
	}

	//VARSG'.ListaVariablesH = TVARG.ListaVariablesH 
	//TVARG.ListaVariablesS = VARSG'.ListaVariablesS 
	
	//VARSGPP ->   , VARSGP
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaVariables listaH, LVarHandler listaS) 
	{
		boolean r;
//		System.out.println("VARSGPP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			LVarHandler listaSp = new LVarHandler();
			producciones.set(1, new VARSGP0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp);
			listaS.setLista(listaSp.getLista());
		}
		return r; 	
	}
}
