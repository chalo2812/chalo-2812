package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.*;

public class DECGLOBAL0 extends Produccion 
{
	public DECGLOBAL0 ()
	{
		VARSG0 varsg = null ;
		producciones.add(varsg);
		CONSTS0 consts = null;
		producciones.add(consts);
	}
	

	//CONSTS.ListaH = new ListaConstantes() 
	//DECGLOBALES.TablaSimbolH.add ( CONSTS.ListaS ) 
	//DECGLOBALES.TablaSimboloS = DECGLOBALES.TablaSimbolosH 

	//DECGLOBAL -> VARSG | CONSTS 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			TablaDeSimbolos tablaH, TSHandler tablaS) 
	{
		boolean r = false; 
//		System.out.println("DECGLOBAL0"); 		
		if ( sintactic.siguiente.accept(visitor).equals("var") ) 
		{
			LVarHandler listaP1 = new LVarHandler();
			producciones.set(0, new VARSG0());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ListaVariables(), listaP1);
			tablaH.insertarListaVariables(listaP1.getLista());
			tablaS.setTabla(tablaH);
		}
		else 
		{
			if ( sintactic.siguiente.accept(visitor).equals("const") )
			{
				LConstHandler listaP2 = new LConstHandler();
				producciones.set(1, new CONSTS0()); 
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, new ListaConstantes(), listaP2);
				tablaH.insertarListaConstantes(listaP2.getLista());
				tablaS.setTabla(tablaH);
			}
		}
		return r; 				
	}		
}
