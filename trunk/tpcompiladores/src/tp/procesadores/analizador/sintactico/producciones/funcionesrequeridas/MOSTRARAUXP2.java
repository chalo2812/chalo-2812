package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class MOSTRARAUXP2 extends ProduccionC
{

	public MOSTRARAUXP2()
	{
		SimboloTerminal ptoycoma = new SimboloTerminal(";");
		producciones.add(ptoycoma);
	}

	//MOSTRARAUX' -> ;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("MOSTRARAUXP2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		return r;
	}
}
