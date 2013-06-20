package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class MOSTRARAUX2 extends ProduccionC
{
	public MOSTRARAUX2()
	{
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		PAUX0 paux0 = null;
		producciones.add(paux0);
		MOSTRARAUXP0 mostrarpauxp0 = null;
		producciones.add(mostrarpauxp0);
	}

	//MOSTRARAUX ->	PALABRA PAUX MOSTRARAUX'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("MOSTRARAUX2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new PAUX0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				producciones.set(2, new MOSTRARAUXP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
			}
			
		}
		return r;
	}
}