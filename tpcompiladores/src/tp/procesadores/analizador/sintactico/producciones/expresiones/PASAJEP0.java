package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class PASAJEP0 extends Produccion{

	public PASAJEP0(){
		SimboloTerminal comma = new SimboloTerminal(","); 
		producciones.add(comma);
		PASAJEPP0 pasajepp = new PASAJEPP0(); 
		producciones.add(pasajepp);
	}

	//PASAJE'.ArbolS = PASAJE''.ArbolS
	
	//PASAJE' -> ,PASAJE'' | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			ArbolHandler arbolSp = new ArbolHandler();
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
			arbolS.setArbol(arbolSp.getArbol());
		}else
		{
			arbolS.setArbol(arbolH);
			r = true; 
		}
		return r;
	}
}
