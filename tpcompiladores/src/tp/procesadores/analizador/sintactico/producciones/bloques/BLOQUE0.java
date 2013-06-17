package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class BLOQUE0 extends ProduccionC {

	public BLOQUE0()
	{
		LINEA0 linea = null;
		producciones.add(linea);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
	}

	//BLOQUE  ->   LINEA BLOQUE | lambda;
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("BLOQUE0");
		producciones.set(0, new LINEA0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new BLOQUE0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);		
		}
		else
		{
			r = true;
		}
		return r;
	}
}