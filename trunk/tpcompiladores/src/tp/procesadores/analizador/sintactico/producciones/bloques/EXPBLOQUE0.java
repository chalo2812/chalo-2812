package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXPBOOL0;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.ESIMPAR0;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.ESPAR0;

public class EXPBLOQUE0 extends Produccion {

	public EXPBLOQUE0()
	{
		EXPBOOL0 expbool = null;
		producciones.add(expbool);
		ESPAR0 espar = null;
		producciones.add(espar);
		ESIMPAR0 esimpar = null;
		producciones.add(esimpar);
		
	}

	//	EXPBLOQUE       	->   EXPBOOL | ESPAR | ESIMPAR
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r = false;
//		System.out.println("EXPBLOQUE0");
		if ((sintactic.siguiente.accept(visitor).equals("(")) || (sintactic.siguiente.getClass() == Palabra.class) 
				|| (sintactic.siguiente.getClass() == Entero.class) || (sintactic.siguiente.getClass() == Natural.class) || (sintactic.siguiente.accept(visitor).equals("aentero")) 
				|| (sintactic.siguiente.accept(visitor).equals("anatural")) || sintactic.siguiente.accept(visitor).equals("and") || sintactic.siguiente.accept(visitor).equals("or")  ) 
			{
				producciones.set(0, new EXPBOOL0());
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);	
			}
		else
			{
				if (sintactic.siguiente.accept(visitor).equals("par"))
				{
					producciones.set(1, new ESPAR0());
					r = producciones.get(1).reconocer(lexic, visitor, sintactic);	

				}	
				else
				{
					if (sintactic.siguiente.accept(visitor).equals("impar")) 
					{
						producciones.set(2, new ESIMPAR0());
						r = producciones.get(2).reconocer(lexic, visitor, sintactic);	

					}
						
				}
				
			}
			
		return r;
	}
}