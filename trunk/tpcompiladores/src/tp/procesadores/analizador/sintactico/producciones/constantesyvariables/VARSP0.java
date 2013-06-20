package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSP0 extends ProduccionC
{
	public VARSP0 ()
	{
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		TVAR0 tvar = null;
		producciones.add(tvar);
	}
	
	//VARS' -> PALABRA TVAR
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("VARSP0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new TVAR0());
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
