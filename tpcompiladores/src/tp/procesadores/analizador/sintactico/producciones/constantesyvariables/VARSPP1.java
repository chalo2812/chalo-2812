package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class VARSPP1 extends Produccion
{
	public VARSPP1 ()
	{
		PalabraReservada comma = new PalabraReservada(",");
		producciones.add(comma);
		VARSP0 varsp = null;
		producciones.add(varsp);
	}

	//VARS'.ListaVariablesH = TVAR.ListaVariablesH 
	//TVAR.ListaVariablesS = VARS'.ListaVariablesS 
	
	//VARS�' -> , VARS� 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ListaVariables listaH, LVarHandler listaS) 
	{		
		boolean r;
//		System.out.println("VARSPP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			LVarHandler listaSp = new LVarHandler();
			producciones.set(1, new VARSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp);
			listaS.setLista(listaSp.getLista());
		}
		return r; 	
	}
}
