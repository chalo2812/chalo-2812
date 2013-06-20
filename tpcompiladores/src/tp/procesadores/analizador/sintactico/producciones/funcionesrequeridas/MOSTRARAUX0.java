package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Cadena;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class MOSTRARAUX0 extends ProduccionC
{
	public MOSTRARAUX0()
	{
		MOSTRARAUX1 mosrarpaux1 = null; 
		producciones.add(mosrarpaux1);
		MOSTRARAUX2 mosrarpaux2 = null; 
		producciones.add(mosrarpaux2);
	}

	//MOSTRARAUX 	->   CADENA MOSTRARAUX' |	PALABRA PAUX MOSTRARAUX'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r=false;
//		System.out.println("MOSTRARAUX0");
		if ( sintactic.siguiente.getClass() == Cadena.class )
		{
			producciones.set(0, new MOSTRARAUX1());	
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			if ( sintactic.siguiente.getClass() == Palabra.class )
			{ 
				producciones.set(1, new MOSTRARAUX2());	
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				merrores.mostrarYSkipearError("Se espera identificador o una cadena de caracteres", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		
		}	
		return r;
	}
}
