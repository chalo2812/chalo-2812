package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class BLOQUESI0 extends ProduccionC {

	public BLOQUESI0()
	{
		PalabraReservada si = new PalabraReservada("si");
		producciones.add(si);
		EXPBLOQUE0 expbloque = null;
		producciones.add(expbloque);
		PalabraReservada entonces = new PalabraReservada("entonces");
		producciones.add(entonces);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
		BLOQUESIP0 bloquesip = null;
		producciones.add(bloquesip);
	}

	//BLOQUESI ->   si EXPBLOQUE entonces BLOQUE BLOQUESI'
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("BLOQUESI0");
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
						producciones.set(4, new BLOQUESIP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
					}
				}
				else
				{
					merrores.mostrarYSkipearError("Se espera palabra reservada 'entonces'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
				
			}
			
		}
		return r;
	}
}