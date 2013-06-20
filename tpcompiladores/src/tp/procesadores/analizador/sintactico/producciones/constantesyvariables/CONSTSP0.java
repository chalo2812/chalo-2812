package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class CONSTSP0 extends ProduccionC
{
	public CONSTSP0 ()
	{
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		TCONSTS0 tconsts = null;
		producciones.add(tconsts);
	}
	
	//CONSTS' -> PALABRA TCONSTS
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("CONSTSP0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new TCONSTS0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			merrores.mostrarYSkipearError("Se esperaba un identificador", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}
		return r;
	}
}
