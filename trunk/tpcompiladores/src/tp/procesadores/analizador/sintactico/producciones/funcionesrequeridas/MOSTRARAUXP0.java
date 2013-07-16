package tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class MOSTRARAUXP0 extends Produccion
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
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r=false;
//		System.out.println("MOSTRARAUXP0");
		if (sintactic.siguiente.accept(visitor).equals(",")) 
		{ 
				ArbolHandler arbolSp1 = new ArbolHandler();
				r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
				arbolS.setArbol(arbolSp1.getArbol());
		}
		else
		{ 
			if (sintactic.siguiente.accept(visitor).equals(";")) 
			{
					ArbolHandler arbolSp2 = new ArbolHandler();
					r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
					arbolS.setArbol(arbolSp2.getArbol());
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