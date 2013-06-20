package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class LINEA4 extends ProduccionC {
	
	public LINEA4(){
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		FPOASIG0 fpoasig = null;
		producciones.add(fpoasig);
	}
	
	//LINEA -> PALABRA FPOSASIG
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("LINEA4");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new FPOASIG0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}
}
