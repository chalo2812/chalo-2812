package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FACTP0 extends ProduccionC{
	
	public FACTP0(){
		FACTP1 factp1 = new FACTP1();
		producciones.add(factp1);
		FACTP2 factp2 = new FACTP2(); 
		producciones.add(factp2);
	}
	
	
	//FACT' ->	[EXP] | (PASAJE) | e 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("FACTP0");
		if (sintactic.siguiente.accept(visitor).equals("[")){
			producciones.set(0, new FACTP1()); 
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{
			if (sintactic.siguiente.accept(visitor).equals("("))
			{
				producciones.set(1, new FACTP2()); 
				r = producciones.get(1).reconocer(lexic, visitor, sintactic); 
			}else
			{
				r = true; 
			}
		}
		return r; 
	} 
}
