package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Cadena;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
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
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r=false;
//		System.out.println("MOSTRARAUX0");
		if ( sintactic.siguiente.getClass() == Cadena.class )
		{
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(0, new MOSTRARAUX1());	
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
			arbolS.setArbol(arbolSp1.getArbol());
		}
		else
		{
			if ( sintactic.siguiente.getClass() == Palabra.class )
			{
				ArbolHandler arbolSp2 = new ArbolHandler();
				producciones.set(1, new MOSTRARAUX2());	
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
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
