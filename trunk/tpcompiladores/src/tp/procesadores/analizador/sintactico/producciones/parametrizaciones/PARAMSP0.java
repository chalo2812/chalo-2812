package tp.procesadores.analizador.sintactico.producciones.parametrizaciones;

import java.util.List;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaParametrosHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ParametroHandler;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.TIPO0;

public class PARAMSP0 extends ProduccionC
{
	public PARAMSP0()
	{
		SimboloTerminal coma = new SimboloTerminal(",");
		producciones.add(coma);
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

	//	PARAMS'		->	,TIPOPARAM PALABRA: TIPO PARAMS'	|	lambda
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, List<Parametro> parametrosH, ListaParametrosHandler parametrosS) 
	{
		boolean r;
//		System.out.println("PARAMSP0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			ParametroHandler parametroSp1 = new ParametroHandler();
			producciones.set(1, new TIPOPARAM0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new Parametro(), parametroSp1);
			if ( r )
			{
				ParametroHandler parametroSp2 = new ParametroHandler();
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, parametroSp1.getParametro(), parametroSp2);
				if ( r )
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						ParametroHandler parametroSp3 = new ParametroHandler();
						producciones.set(4, new TIPO0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic, parametroSp2.getParametro(), parametroSp3);
						parametrosH.add(parametroSp3.getParametro());
						if ( r )
						{
							ListaParametrosHandler parametrosSp = new ListaParametrosHandler();
							producciones.set(5, new PARAMSP0());
							r = producciones.get(5).reconocer(lexic, visitor, sintactic, parametrosH, parametrosSp);
							parametrosS.setParametros(parametrosSp.getParametros());
						}
					}else
					{
						merrores.mostrarYSkipearError("Se espera dos puntos ':'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
					
				}
				
			}
			
		}
		else
		{
			if(sintactic.siguiente.accept(visitor).equals(")")){
				r=true;
				parametrosS.setParametros(parametrosH);
			}else
			{
				merrores.mostrarYSkipearError("Se espera parentesis ')' o comma ',' y mas parametros", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}

}
