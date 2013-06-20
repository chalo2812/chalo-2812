package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class CONSTSPP0 extends ProduccionC 
{
	public CONSTSPP0 ()
	{
		CONSTSPP1 constspp1 = null;
		producciones.add(constspp1);
		CONSTSPP2 constspp2 = null;
		producciones.add(constspp2);
	}
	
	//CONSTS'' -> , CONSTS' | ;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r= true; 
//		System.out.println("CONSTSPP0");
		if (sintactic.siguiente.accept(visitor).equals(","))
		{
			producciones.set(0, new CONSTSPP1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else 
		{
			if (sintactic.siguiente.accept(visitor).equals(";"))
			{
				producciones.set(1, new CONSTSPP2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}	
			else
			{
				merrores.mostrarYSkipearError("Se esperaba una nueva constante o fin de linea", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r; 	
	}
}
