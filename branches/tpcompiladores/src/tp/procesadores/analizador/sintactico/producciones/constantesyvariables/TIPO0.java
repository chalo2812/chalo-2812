package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ElementoIdentificador;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.MetodoHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ParametroHandler;
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
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ElementoIdentificador elementoH) 
	{
		boolean r = false; 
//		System.out.println("TIPO0");
		if  (sintactic.siguiente.accept(visitor).equals("entero")) 
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			elementoH.setTipo("entero");
		}
		else
		{
			if  (sintactic.siguiente.accept(visitor).equals("natural")) 
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
				elementoH.setTipo("natural");
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
	
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			Parametro parametroH, ParametroHandler parametroS) 
	{
		boolean r = false; 
//		System.out.println("TIPO0");
		if  (sintactic.siguiente.accept(visitor).equals("entero")) 
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			parametroH.setTipo("entero");
			parametroS.setParametro(parametroH);
		}
		else
		{
			if  (sintactic.siguiente.accept(visitor).equals("natural")) 
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
				parametroH.setTipo("natural");
				parametroS.setParametro(parametroH);
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
	
	
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			Metodo metodoH, MetodoHandler metodoS) 
	{
		boolean r = false; 
//		System.out.println("TIPO0");
		if  (sintactic.siguiente.accept(visitor).equals("entero")) 
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
			metodoH.setTipo("entero");
			metodoS.setMetodo(metodoH);
		}
		else
		{
			if  (sintactic.siguiente.accept(visitor).equals("natural")) 
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
				metodoH.setTipo("natural");
				metodoS.setMetodo(metodoH);
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
