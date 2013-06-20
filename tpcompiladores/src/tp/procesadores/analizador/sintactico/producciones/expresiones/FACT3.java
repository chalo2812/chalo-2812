package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FACT3 extends ProduccionC{
	
	public FACT3(){
		NUMERO numero = new NUMERO();
		producciones.add(numero);
	}
	
	//FACT ->   NUMERO 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("FACT3");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		return r;
	}	

}
