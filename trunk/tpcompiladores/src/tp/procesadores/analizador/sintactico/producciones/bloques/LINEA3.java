package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class LINEA3 extends ProduccionC {
	
	public LINEA3(){
		PALRES palres = null;
		producciones.add(palres);
	}
	
	//LINEA -> PALRES 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("LINEA3");
		producciones.set(0, new PALRES());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		return r;
	} 
}
