package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TCONSTS0 extends ProduccionC
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
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("TCONSTS");
		if (sintactic.siguiente.accept(visitor).equals(":")) 
		{
			producciones.set(0, new TCONSTS1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);			
		}
		else
		{
			if (sintactic.siguiente.accept(visitor).equals(","))
			{
				producciones.set(1, new TCONSTS2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
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
