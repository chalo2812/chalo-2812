package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class MOSTRAR0 extends ProduccionC

{
	public MOSTRAR0()
	{
		SimboloTerminal mostrarln = new SimboloTerminal("mostrar");
		producciones.add(mostrarln);
		MOSTRARAUX0  mostraraux = null;
		producciones.add(mostraraux);
	}
	
	//MOSTRARLN       ->   mostrarln MOSTRARAUX
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("MOSTRAR0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new MOSTRARAUX0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}

		return r;
	}
}
