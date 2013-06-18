package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class VARSGPP2 extends ProduccionC {

	public VARSGPP2(){
		SimboloTerminal pycoma = new SimboloTerminal (";"); 
		producciones.add(pycoma);
	}
	
	//VARSGPP ->  ;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("VARSGPP2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		return r;
	}

}