package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class DECGL0 extends ProduccionC 
{
	public DECGL0 ()
	{
		DECGLOBAL0 decglobal = null;
		producciones.add(decglobal);
		DECGL0 decgl = null;
		producciones.add(decgl);
	}
	
	//DECGL -> DECGLOBAL DECGL | e 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("DECGL0");
		producciones.set(0, new DECGLOBAL0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new DECGL0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}else{
			r = true;
		}
		return r; 				
	}		
}
