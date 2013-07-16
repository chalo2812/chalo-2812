package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class TCONSTS0 extends Produccion
{
	public TCONSTS0 ()
	{
		TCONSTS1 tconsts1 = null;
		producciones.add(tconsts1);
		TCONSTS2 tconsts2 = null;
		producciones.add(tconsts2);
	}
	
	//TCONSTS -> :TIPO = NUMERO CONSTS'' | , CONSTS'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaConstantes listaH, LConstHandler listaS) 
	{
		boolean r;
//		System.out.println("TCONSTS");
		if (sintactic.siguiente.accept(visitor).equals(":")) 
		{
			LConstHandler listaSp1 = new LConstHandler();
			producciones.set(0, new TCONSTS1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
			listaS.setLista(listaSp1.getLista());
		}
		else
		{
			if (sintactic.siguiente.accept(visitor).equals(","))
			{
				LConstHandler listaSp2 = new LConstHandler();
				producciones.set(1, new TCONSTS2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp2);
				listaS.setLista(listaSp2.getLista());
			}
			else
			{
				merrores.mostrarYSkipearError("Se esperaba un ':' mas tipo e inicializacion de constante o ',' y otro identificador", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
