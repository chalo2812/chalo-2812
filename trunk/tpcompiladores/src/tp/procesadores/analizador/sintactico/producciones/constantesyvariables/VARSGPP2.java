package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class VARSGPP2 extends ProduccionC {

	public VARSGPP2(){
		SimboloTerminal pycoma = new SimboloTerminal (";"); 
		producciones.add(pycoma);
	}
	
	//VARSG''.ListaVariablesS = VARSG''.ListaVariablesH 
			
	//VARSGPP ->  ;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ListaVariables listaH, LVarHandler listaS) 
	{
		boolean r; 
//		System.out.println("VARSGPP2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		listaS.setLista(listaH);
		return r;
	}

}