package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TCONSTS1 extends ProduccionC
{
	public TCONSTS1 ()
	{		
		SimboloTerminal dospuntos = new SimboloTerminal(":");
		producciones.add(dospuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
		SimboloTerminal igual = new SimboloTerminal("=");
		producciones.add(igual);
		NUMERO numero = new NUMERO();
		producciones.add(numero);
		CONSTSPP0 constspp = null;
		producciones.add(constspp);
	}
	
	//CONSTS' ->  : TIPO = NUMERO CONSTS''
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("TCONSTS1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new TIPO0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						producciones.set(4, new CONSTSPP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
					}
					else
					{
						merrores.mostrarYSkipearError("Se esperaba un numero entero o natural", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
				}
				else
				{
					merrores.mostrarYSkipearError("Se esperaba palabra reservada '='", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
			else
			{
				merrores.mostrarYSkipearError("Se esperaba palabras reservadas 'entero' o 'natural'", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r; 	
	}
}
