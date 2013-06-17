package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class TIPO0 extends ProduccionC
{
	public TIPO0 ()	
	{
		PalabraReservada entero = new PalabraReservada("entero");
		producciones.add(entero);
		PalabraReservada natural = new PalabraReservada("natural");
		producciones.add(natural);
	}
	
	//TIPO -> entero | natural
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r = false; 
//		System.out.println("TIPO0");
		if  (sintactic.siguiente.accept(visitor).equals("entero")) 
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			if  (sintactic.siguiente.accept(visitor).equals("natural")) 
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);				
			}
			else 
			{
				merrores.mostrarYSkipearError("Se esperaba palabra reservada 'entero' o 'natural'", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
