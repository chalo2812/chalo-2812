package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSG0 extends ProduccionC {

	public VARSG0(){
		PalabraReservada var = new PalabraReservada("var");
		producciones.add(var);
		VARSGP0 varsgp = null;
		producciones.add(varsgp);
	}

	//VARSG  ->   var VARSG'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("VARSG0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new VARSGP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}	
}
