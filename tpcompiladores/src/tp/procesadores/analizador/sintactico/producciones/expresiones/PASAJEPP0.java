package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class PASAJEPP0 extends Produccion {

	public PASAJEPP0(){
		PASAJEPP1 pp1 = null; 
		producciones.add(pp1);
		PASAJEPP2 pp2 = null;
		producciones.add(pp2);
	}
	
	
	//PASAJE'.ArbolH = PALABRA.ArbolS
	//PASAJE''.ArbolS = PASAJE'.ArbolS
	
	/**
	 * 	PASAJE'' ->   PALABRA PASAJE'|
	 * 				  NUMERO PASAJE'
	 */
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r; 
		if ( sintactic.siguiente.getClass() == Palabra.class) 
		{
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(0, new PASAJEPP1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
			arbolS.setArbol(arbolSp1.getArbol());
		}else
		{
			if (( sintactic.siguiente.getClass() == Entero.class) || ( sintactic.siguiente.getClass() == Natural.class))
			{
				ArbolHandler arbolSp2 = new ArbolHandler();
				producciones.set(1, new PASAJEPP2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}else
			{
				merrores.mostrarYSkipearError("Se espera identificador o parametro", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
