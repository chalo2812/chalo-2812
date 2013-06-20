package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class LINEA2 extends ProduccionC {
	
	public LINEA2(){
		BLOQUEM0 bloquem = null;
		producciones.add(bloquem);
	}
	
	//LINEA ->  BLOQUEM
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("LINEA2");
		producciones.set(0, new BLOQUEM0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		return r;
	} 
}
