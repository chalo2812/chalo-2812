package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FPOASIG0 extends ProduccionC {

	public FPOASIG0()
	{
		FPOASIG1 fpoasig1 = null; 
		producciones.add(fpoasig1);
		FPOASIG2 fpoasig2 = null;
		producciones.add(fpoasig2);
		FPOASIG3 fpoasig3 = null;
		producciones.add(fpoasig3);
	}

	/**
	 * FPOASIG ->	(PASAJE); |
					:= EXP; | 
					[EXP] := EXP;
	 */
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("FPOASIG0");
		if ( sintactic.siguiente.accept(visitor).equals("("))
		{
			producciones.set(0, new FPOASIG1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else{
			if ( sintactic.siguiente.accept(visitor).equals(":="))
			{
				producciones.set(1, new FPOASIG2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				if (sintactic.siguiente.accept(visitor).equals("[")){ 
					producciones.set(2, new FPOASIG3());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				}else
				{
					merrores.mostrarYSkipearError("Se espera alguno de los siguientes operadores {'(',':=','['}", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true; 
				}
			}
		}
		return r;
	}
}