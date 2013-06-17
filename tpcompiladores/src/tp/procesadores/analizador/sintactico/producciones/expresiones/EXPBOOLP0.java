package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class EXPBOOLP0 extends ProduccionC{

	public EXPBOOLP0(){
		EXPBOOLP1 expbool1 = new EXPBOOLP1();
		producciones.add(expbool1);
		EXPBOOLP2 expbool2 = new EXPBOOLP2();
		producciones.add(expbool2);
		EXPBOOLP3 expbool3 = new EXPBOOLP3();
		producciones.add(expbool3);
		EXPBOOLP4 expbool4 = new EXPBOOLP4();
		producciones.add(expbool4);
		EXPBOOLP5 expbool5 = new EXPBOOLP5();
		producciones.add(expbool5);
		EXPBOOLP6 expbool6 = new EXPBOOLP6();
		producciones.add(expbool6);
	}
	
	/**
	 * EXPBOOL'    	->	= EXP |
	 *					< EXP |
	 *					!= EXP |
	 *					== EXP |
	 *					<< EXP | 
	 *					!== EXP 
	 */
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("EXPBOOLP0");
		if(sintactic.siguiente.accept(visitor).equals("="))
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{
			if(sintactic.siguiente.accept(visitor).equals("<"))
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				if(sintactic.siguiente.accept(visitor).equals("!="))
				{
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				}else
				{
					if(sintactic.siguiente.accept(visitor).equals("=="))
					{
						r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					}else
					{
						if(sintactic.siguiente.accept(visitor).equals("<<"))
						{
							r = producciones.get(4).reconocer(lexic, visitor, sintactic);
						}else
						{
							if(sintactic.siguiente.accept(visitor).equals("!=="))
							{
								r = producciones.get(5).reconocer(lexic, visitor, sintactic);
							}else
							{
								merrores.mostrarYSkipearError("Se espera operador booleano { '=', '<', '!=', '==', '<<', '!=='} ", lexic, sintactic, visitor);
								sintactic.setEstadoAnalisis(false);
								r = true;
							}
						}
					}
				}
			}
		}
		return r;
	}
	
	
}
