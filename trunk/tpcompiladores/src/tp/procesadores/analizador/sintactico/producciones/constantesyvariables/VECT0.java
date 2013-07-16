package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class VECT0 extends Produccion {

	public VECT0(){ 
		SimboloTerminal dosPuntos = new SimboloTerminal(":");
		producciones.add(dosPuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
	}
	
	//TIPO.ElementoIdentificador = VECT.ListaVariablesH.UltimoElemento
	//VECT.ListaVariablesS = VECT.ListaVariablesH
	
	//VECT ->  : TIPO | lambda (ver TVARG3 donde se trata el caso de lambda)
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaVariables listaH, LVarHandler listaS) 
	{
		boolean r; 
//		System.out.println("VECT0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{ 
			producciones.set(1, new TIPO0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH.getLastElement());
			listaS.setLista(listaH);
		}		
		return r;
	}	
}
