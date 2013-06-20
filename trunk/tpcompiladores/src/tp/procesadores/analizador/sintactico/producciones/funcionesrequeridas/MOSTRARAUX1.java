package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.CADENA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class MOSTRARAUX1 extends ProduccionC
{
	public MOSTRARAUX1()
	{
		CADENA caedena = new CADENA();
		producciones.add(caedena);
		MOSTRARAUXP0 mostrarauxp0 = null;
		producciones.add(mostrarauxp0);
	}

	//MOSTRARAUX ->	CADENA MOSTRARAUX'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("MOSTRARAUX1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			producciones.set(1, new MOSTRARAUXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}
}