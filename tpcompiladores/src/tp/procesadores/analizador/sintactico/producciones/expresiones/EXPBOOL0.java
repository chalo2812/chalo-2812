package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class EXPBOOL0 extends ProduccionC {

	
	public EXPBOOL0(){
		EXPBOOL1 exp1 = new EXPBOOL1();
		producciones.add(exp1); 
		EXPBOOL2 exp2 = new EXPBOOL2();
		producciones.add(exp2); 
		EXPBOOL3 exp3 = new EXPBOOL3();
		producciones.add(exp3); 
		
	}
	
	/** 
	 * EXPBOOL ->   EXP EXPBOOL' |  { (, PALABRA, ENTERO, NATURAL, aentero, anatural }
	 *				AND |			{ and }  
	 *				OR				{ or }
	 */
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r = false;
//		System.out.println("EXPBOOL0");
		if ((sintactic.siguiente.accept(visitor).equals("(")) || (sintactic.siguiente.getClass() == Palabra.class) 
				|| (sintactic.siguiente.getClass() == Entero.class) || (sintactic.siguiente.getClass() == Natural.class) || (sintactic.siguiente.accept(visitor).equals("aentero")) 
				|| (sintactic.siguiente.accept(visitor).equals("anatural")) ) 
		{
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{ 
				if (sintactic.siguiente.accept(visitor).equals("and"))
				{
					r = producciones.get(1).reconocer(lexic, visitor, sintactic);
				}else
				{
					if (sintactic.siguiente.accept(visitor).equals("or"))
					{
						r = producciones.get(2).reconocer(lexic, visitor, sintactic);
					}
				}
		}
		return r;
	}
}
