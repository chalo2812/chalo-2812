package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class BLOQUESIP0 extends ProduccionC {

	public BLOQUESIP0()
	{
		BLOQUESIP1 sip1 = null;
		producciones.add(sip1);
		BLOQUESIP2 sip2 = null;
		producciones.add(sip2); 
	}

	//BLOQUESIP0 ->   fin-si; | sino BLOQUE fin-si;
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("BLOQUESIP0");
		if( sintactic.siguiente.accept(visitor).equals("fin-si"))
		{
			producciones.set(0, new BLOQUESIP1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{
			if( sintactic.siguiente.accept(visitor).equals("sino"))
			{
				producciones.set(1, new BLOQUESIP2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				merrores.mostrarYSkipearError("Se espara palabra reservada 'fin-si' o bloque 'sino .. fin-si'", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}