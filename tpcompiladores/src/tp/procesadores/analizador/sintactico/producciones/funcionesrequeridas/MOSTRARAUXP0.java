package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class MOSTRARAUXP0 extends ProduccionC
{
	public MOSTRARAUXP0()
	{
		MOSTRARAUXP1 mostrarauxp1 = new MOSTRARAUXP1(); 
		producciones.add(mostrarauxp1);
		MOSTRARAUXP2 mostrarauxp2 = new MOSTRARAUXP2(); 
		producciones.add(mostrarauxp2);
	}

	//MOSTRARAUX'    ->   , MOSTRARAUX |	;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r=false;
//		System.out.println("MOSTRARAUXP0");
		if (sintactic.siguiente.accept(visitor).equals(",")) 
		{ 
				r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		else
		{ 
			if (sintactic.siguiente.accept(visitor).equals(";")) 
			{ 
					r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				merrores.mostrarYSkipearError("Se espera alguno de los siguientes operadores {';',','}", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}	
		return r;
	}
}