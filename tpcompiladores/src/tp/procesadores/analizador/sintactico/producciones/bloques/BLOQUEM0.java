package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class BLOQUEM0 extends ProduccionC {

	public BLOQUEM0()
	{
		PalabraReservada mientras = new PalabraReservada("mientras");
		producciones.add(mientras);
		EXPBLOQUE0 expbloque = null;
		producciones.add(expbloque);
		PalabraReservada hacer = new PalabraReservada("hacer");
		producciones.add(hacer);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
		PalabraReservada finmientras = new PalabraReservada("fin-mientras");
		producciones.add(finmientras);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}


	//BLOQUEM -> mientras EXPBLOQUE hacer BLOQUE fin-mientras;
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("BLOQUEM0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new EXPBLOQUE0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					producciones.set(3, new BLOQUE0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
						if ( r )
						{
							r = producciones.get(5).reconocer(lexic, visitor, sintactic);
							if (!r)
							{
								merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
								sintactic.setEstadoAnalisis(false);
								r = true;
							}
						}
						else
						{
							merrores.mostrarYSkipearError("Se espera palabra reservada 'fin-mientras'", lexic, sintactic, visitor);
							sintactic.setEstadoAnalisis(false);
							r = true;
						}
					}
				}
				else
				{
					merrores.mostrarYSkipearError("Se espera palabra reservada 'hacer'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
				
			}
			
		}
		return r;
	}
}