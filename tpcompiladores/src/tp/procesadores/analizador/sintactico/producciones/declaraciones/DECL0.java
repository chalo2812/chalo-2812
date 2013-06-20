package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class DECL0 extends ProduccionC 
{
	public DECL0 ()
	{
		DECLARACIONES0 declaraciones = null;
		producciones.add(declaraciones);
		DECL0 decl = null;
		producciones.add(decl);
	}
	
	//DECL -> DECLARACIONES DECL | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("DECL0");
		producciones.set(0, new DECLARACIONES0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			producciones.set(1, new DECL0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}else{
			r = true;
		}
		return r; 				
	}		
}
