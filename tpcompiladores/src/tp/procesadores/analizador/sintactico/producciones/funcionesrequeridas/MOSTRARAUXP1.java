package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class MOSTRARAUXP1 extends ProduccionC
{
	public MOSTRARAUXP1()
	{
		SimboloTerminal coma = new SimboloTerminal(",");
		producciones.add(coma);
		MOSTRARAUX0 mostraraux0 = null;
		producciones.add(mostraraux0);
	}

	//MOSTRARAUX' -> , MOSTRARAUX0
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("MOSTRARAUXP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new MOSTRARAUX0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}

}

