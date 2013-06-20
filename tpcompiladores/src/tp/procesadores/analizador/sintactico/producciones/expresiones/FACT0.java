package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;


public class FACT0 extends ProduccionC{
	
	public FACT0(){
			FACT1 fact1 = new FACT1(); 
			producciones.add(fact1);
			FACT2 fact2 = new FACT2(); 
			producciones.add(fact2);
			FACT3 fact3 = new FACT3();
			producciones.add(fact3);
			FACT4 fact4 = new FACT4();
			producciones.add(fact4);
			FACT5 fact5 = new FACT5();
			producciones.add(fact5);
	}
	
	
	/**
	 *   FACT  ->  	(EXP) |
	 *				PALABRA FACT' |
	 *				NUMERO |
	 *				AENTERO |
	 *				ANATURAL
	 *
	 */
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r = true; 
//		System.out.println("FACT0");
		if (sintactic.siguiente.accept(visitor).equals("("))
		{
			producciones.set(0, new FACT1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		}else
		{
			if ( sintactic.siguiente.getClass() == Palabra.class)
			{
				producciones.set(1, new FACT2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic); 
			}else 
			{
				if (( sintactic.siguiente.getClass() == Entero.class) || ( sintactic.siguiente.getClass() == Natural.class))
				{
					producciones.set(2, new FACT3());
					r =  producciones.get(2).reconocer(lexic, visitor, sintactic); 
				}else 
				{ 
					if( sintactic.siguiente.accept(visitor).equals("aentero")){ 
						producciones.set(3, new FACT4());
						r =  producciones.get(3).reconocer(lexic, visitor, sintactic); 
					}else 
					{
						if (sintactic.siguiente.accept(visitor).equals("anatural"))
						{ 
							producciones.set(4, new FACT5());
							r =  producciones.get(4).reconocer(lexic, visitor, sintactic);
						}else
						{
							merrores.mostrarYSkipearError("Se espera Factor", lexic, sintactic, visitor);
							sintactic.setEstadoAnalisis(false);
							r=true;
						}
					}
				}
			}
		}
		return r;
	}

}
