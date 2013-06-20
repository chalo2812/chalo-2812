package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXP0;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class BLOQUEF0 extends ProduccionC {

	public BLOQUEF0()
	{
		PalabraReservada comenzar = new PalabraReservada("comenzar");
		producciones.add(comenzar);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
		PalabraReservada finfuncion = new PalabraReservada("fin-func");
		producciones.add(finfuncion);
		EXP0 exp = null;
		producciones.add(exp);
		SimboloTerminal pycoma = new SimboloTerminal(";");
		producciones.add(pycoma);
	}

	//BLOQUEF ->   comenzar BLOQUE fin-func EXP;
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("BLOQUEF0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new BLOQUE0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					producciones.set(3, new EXP0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
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
					merrores.mostrarYSkipearError("Se espera palabra reservada fin-func", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}else
		{
			merrores.mostrarYSkipearError("Se esperaba palabra reservada comenzar", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}
		return r;
	}
}