package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class VARSGPP0 extends Produccion
{
	public VARSGPP0()
	{
		VARSGPP1 varsgpp1 = null;
		producciones.add(varsgpp1);
		VARSGPP2 varsgpp2 = new VARSGPP2();
		producciones.add(varsgpp2);
	}
	
	/**
	 * VARSGPP  ->  , VARSGP |  ;
	 */
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaVariables listaH, LVarHandler listaS) 
	{
		boolean r;
//		System.out.println("VARSGPP0");
		producciones.set(0, new VARSGPP1());
		if ( sintactic.siguiente.accept(visitor).equals(","))
		{
			LVarHandler listaSp1 = new LVarHandler();
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
			listaS.setLista(listaSp1.getLista());
		}
		else
		{
			if ( sintactic.siguiente.accept(visitor).equals(";"))
			{
				LVarHandler listaSp2 = new LVarHandler();
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp2);
				listaS.setLista(listaSp2.getLista());
			}
			else
			{
				merrores.mostrarYSkipearError("Se esperaba una nueva variable/vector o un fin de linea", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}