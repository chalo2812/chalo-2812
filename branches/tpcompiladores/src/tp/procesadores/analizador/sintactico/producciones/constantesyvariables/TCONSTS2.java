package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TCONSTS2 extends ProduccionC
{
	public TCONSTS2 ()
	{
		PalabraReservada comma = new PalabraReservada(",");
		producciones.add(comma);
		CONSTSP0 constsp = null;
		producciones.add(constsp);
	}

	//CONSTS'.ListaConstantesH = TCONSTS.ListaConstantesH 
	//TCONSTS.ListaConstantesS = CONSTS'.ListaConstantesS 
	
	//CONSTS' -> , CONSTS'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaConstantes listaH, LConstHandler listaS) 
	{
		boolean r; 
//		System.out.println("TCONSTS2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			LConstHandler listaSp = new LConstHandler();
			producciones.set(1, new CONSTSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp);
			listaS.setLista(listaSp.getLista());
		}
		return r; 	
	}
}
