package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.*;

public class DECGLOBAL0 extends ProduccionC 
{
	public DECGLOBAL0 ()
	{
		VARSG0 varsg = null ;
		producciones.add(varsg);
		CONSTS0 consts = null;
		producciones.add(consts);
	}

	//DECGLOBAL -> VARSG | CONSTS 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r = false; 
//		System.out.println("DECGLOBAL0"); 		
		if ( sintactic.siguiente.accept(visitor).equals("var") ) 
		{
			producciones.set(0, new VARSG0());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			if ( sintactic.siguiente.accept(visitor).equals("const") )
			{
				producciones.set(1, new CONSTS0());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}
		}
		return r; 				
	}		
}
