package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class LINEA1 extends ProduccionC {

	public LINEA1(){
		BLOQUESI0 bloquesi = null;
		producciones.add(bloquesi);
	}
	
	//LINEA1 ->   BLOQUESI
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("LINEA1");
		producciones.set(0, new BLOQUESI0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		return r;
	} 
}
