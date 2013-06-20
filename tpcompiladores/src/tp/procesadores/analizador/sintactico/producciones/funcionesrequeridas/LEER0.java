package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class LEER0 extends ProduccionC

{
	public LEER0()
	{
		SimboloTerminal leer = new SimboloTerminal("leer");
		producciones.add(leer);
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		PAUXP0  paux0 = null;
		producciones.add(paux0);
		SimboloTerminal ptoycoma = new SimboloTerminal(";");
		producciones.add(ptoycoma);
	}
	
	//LEER ->   leer PALABRA PAUX';
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("LEER0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				producciones.set(2, new PAUXP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if (!r)
					{
						merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
					
				}
				
			}
			else
			{
				merrores.mostrarYSkipearError("Se espera un identificador", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
			
		}

		return r;
	}
}
