package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.*;
import tp.procesadores.analizador.sintactico.producciones.subrutinas.*;

public class DECLARACIONES0 extends ProduccionC 
{
	public DECLARACIONES0 ()
	{
		VARS0 vars = null ;
		producciones.add(vars);
		CONSTS0 consts = null;
		producciones.add(consts);
		FP0 fp = null;
		producciones.add(fp);
	}

	//DECLARACIONES -> VARS | CONSTS | FP 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r = false; 
//		System.out.println("DECLARACIONES0 ");		
		if ( sintactic.siguiente.accept(visitor).equals("var") )
		{
			producciones.set(0, new VARS0());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			if ( sintactic.siguiente.accept(visitor).equals("const") )
			{
				producciones.set(1, new CONSTS0());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}
			else
			{
				if ( (sintactic.siguiente.accept(visitor).equals("procedimiento")) || (sintactic.siguiente.accept(visitor).equals("funcion")) )
				{
					producciones.set(2, new FP0());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				}
			}
		}
		return r; 				
	}
}
