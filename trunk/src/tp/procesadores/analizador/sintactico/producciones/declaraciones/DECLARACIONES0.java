package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.*;
import tp.procesadores.analizador.sintactico.producciones.subrutinas.*;

public class DECLARACIONES0 extends ProduccionC 
{
	public DECLARACIONES0()
	{
		VARS0 vars = null ;
		producciones.add(vars);
		CONSTS0 consts = null;
		producciones.add(consts);
		FP0 fp = null;
		producciones.add(fp);
	}
	
	//VARS.ListaH = new ListaVariables() 
	//DECLARACIONES.TablaSimbolosH.add ( VARS.ListaVariablesS ) 
	//DECLARACIONES.TablaSimboloS = DECLARACIONES.TablaSimbolosH  

	//DECLARACIONES -> VARS | CONSTS | FP 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS)  
	{
		boolean r = false; 
//		System.out.println("DECLARACIONES0 ");		
		if ( sintactic.siguiente.accept(visitor).equals("var") )
		{
			LVarHandler listaP1 = new LVarHandler();
			producciones.set(0, new VARS0());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ListaVariables(), listaP1);
			tablaH.insertarListaVariables(listaP1.getLista());
			tablaS.setTabla(tablaH);
			arbolS.setArbol(arbolH);
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
				arbolS.setArbol(arbolH);
			}
			else
			{
				if ( (sintactic.siguiente.accept(visitor).equals("procedimiento")) || (sintactic.siguiente.accept(visitor).equals("funcion")) )
				{
					TSHandler tablaSp = new TSHandler();
					ArbolHandler arbolSp = new ArbolHandler();
					producciones.set(2, new FP0());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH, tablaSp);
					arbolS.setArbol(arbolSp.getArbol());
					tablaS.setTabla(tablaH);
				}
			}
		}
		return r; 				
	}
}
