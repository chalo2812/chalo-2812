package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class TVARG0 extends ProduccionC {

	public TVARG0(){
		TVARG1 tvarg1 = new TVARG1();
		producciones.add(tvarg1);
		TVARG2 tvarg2 = new TVARG2();
		producciones.add(tvarg2);
		TVARG3 tvarg3 = new TVARG3();
		producciones.add(tvarg3);
	}
	
	
	/**
	 * TVARG ->    , VARSG' |
     *             : TIPO INI VARSG'' |
     *             [ NATURAL ] VECT VARSG''
	 */
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("TVARG0");
		if ( sintactic.siguiente.accept(visitor).equals(","))
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else 
		{ 
			if ( sintactic.siguiente.accept(visitor).equals(":"))
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}
			else
			{
				if ( sintactic.siguiente.accept(visitor).equals("["))
				{
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				}
				else 
				{
					merrores.mostrarYSkipearError("Se esperaba un ':' mas definicion de variables/vector o ',' y otro identificador ", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}

}
