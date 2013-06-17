package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TVAR2 extends ProduccionC
{

	public TVAR2 ()
	{
		PalabraReservada dospuntos = new PalabraReservada(":");
		producciones.add(dospuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
		INI0 ini = null;
		producciones.add(ini);
		VARSPP0 varspp = null;
		producciones.add(varspp);
	}
	
	//TVAR -> : TIPO INI VARS''
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("TVAR2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new TIPO0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				if ( sintactic.siguiente.accept(visitor).equals("="))
				{
					producciones.set(2, new INI0());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						producciones.set(3, new VARSPP0());
						r = producciones.get(3).reconocer(lexic, visitor, sintactic);								
					}
				}
				else
				{
					producciones.set(3, new VARSPP0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);								
				}
			}
		}
		return r;
	}
}