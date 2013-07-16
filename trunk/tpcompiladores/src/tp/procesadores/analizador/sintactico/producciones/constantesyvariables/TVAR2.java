package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class TVAR2 extends Produccion
{

	public TVAR2 ()
	{
		PalabraReservada dospuntos = new PalabraReservada(":");
		producciones.add(dospuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
		VARSPP0 varspp = null;
		producciones.add(varspp);
	}
	
	//TIPO.ElementoIdentificador = TVAR.ListaVariablesH.UltimoElemento
	//VARS''.ListaVariablesH = TVAR.ListaVariablesH 
	//TVAR'.ListaVariablesS = VARS''.ListaVariablesS 
	
	//TVAR -> : TIPO VARS''
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ListaVariables listaH, LVarHandler listaS) 
	{
		boolean r;
//		System.out.println("TVAR2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new TIPO0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH.getLastElement());
			if ( r )
			{
				LVarHandler listaSp = new LVarHandler();
				producciones.set(2, new VARSPP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, listaH, listaSp);
				listaS.setLista(listaSp.getLista());
			}
		}
		return r;
	}
}