package tp.procesadores.analizador.sintactico.producciones.parametrizaciones;

import java.util.List;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaParametrosHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ParametroHandler;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.TIPO0;


public class PARAMS0 extends Produccion
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
			SintacticAnalyzer sintactic, List<Parametro> parametrosH, ListaParametrosHandler listaS) 
	{
		boolean r;
//		System.out.println("PARAMS0");
		ParametroHandler parametroSp1 = new ParametroHandler();
		producciones.set(0, new TIPOPARAM0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new Parametro(), parametroSp1); 
		if ( sintactic.siguiente.getClass() == Palabra.class )
		{
			ParametroHandler parametroSp2 = new ParametroHandler();
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, parametroSp1.getParametro(), parametroSp2);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					ParametroHandler parametroSp3 = new ParametroHandler();
					producciones.set(3, new TIPO0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic, parametroSp2.getParametro(), parametroSp3);
					if ( r )
					{
						parametrosH.add(parametroSp3.getParametro());
						ListaParametrosHandler listaSp = new ListaParametrosHandler();
						producciones.set(4, new PARAMSP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic, parametrosH, listaSp);
						listaS.setParametros(listaSp.getParametros());
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
			listaS.setParametros(parametrosH);
			r = true;
		}
		return r;
	}

}

