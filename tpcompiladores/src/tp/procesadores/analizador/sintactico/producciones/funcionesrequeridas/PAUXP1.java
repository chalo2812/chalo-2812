package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class PAUXP1 extends ProduccionC

{
	public PAUXP1()
	{
		SimboloTerminal par = new SimboloTerminal("[");
		producciones.add(par);
		NUMERO numero = new NUMERO();
		producciones.add(numero);
		SimboloTerminal parentesiscerrar = new SimboloTerminal("]");
		producciones.add(parentesiscerrar);
	}
	
	//	[ NATURAL ] 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("PAUXP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				merrores.mostrarYSkipearError("Se espera cierre de corchete ']'", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
			
		}

		return r;
	}
}