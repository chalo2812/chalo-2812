package tp.procesadores.analizador.sintactico.producciones.parametrizaciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.TIPO0;


public class PARAMS0 extends ProduccionC
{
	public PARAMS0()
	{
		TIPOPARAM0 tipoparam = null;
		producciones.add(tipoparam);
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		SimboloTerminal dosptos = new SimboloTerminal(":");
		producciones.add(dosptos);
		TIPO0  tipo = null;
		producciones.add(tipo);
		PARAMSP0  paramsp = null;
		producciones.add(paramsp);
	}

	//PARAMS -> TIPOPARAM PALABRA: TIPO PARAMS'	|	lambda
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("PARAMS0");
		producciones.set(0, new TIPOPARAM0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( sintactic.siguiente.getClass() == Palabra.class )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					producciones.set(3, new TIPO0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						producciones.set(4, new PARAMSP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
					}
				}else
				{
					merrores.mostrarYSkipearError("Se espera dos puntos ':'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true; 
				}
			}
		}else
		{
			r = true;
		}
		return r;
	}

}
