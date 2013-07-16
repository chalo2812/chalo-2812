package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;
import tp.procesadores.analizador.sintactico.producciones.bloques.BLOQUEF0;
import tp.procesadores.analizador.sintactico.producciones.declaraciones.DECL0;

public class FUNCIONP2 extends Produccion {
	
	public FUNCIONP2(){
		DECL0 decl =  null;
		producciones.add(decl);
		BLOQUEF0 bloquef = null;
		producciones.add(bloquef);
	}
	
	//DECL.TablaSimbolosH = FUNCION'.TablaSimbolosH 
	//FUNCION'.ArbolH.add ( DECL.TablaSimbolosS ) 
	//BLOQUEF.ArbolH = new NodoBloque() 
	//FUNCION'.ArbolH.add ( BLOQUEF.ArbolS) 
	//FUNCION'.ArbolS = FUNCION'.ArbolH
	
	//FUNCION�-> DECL BLOQUEF
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS)
	{
		boolean r; 
//		System.out.println("FUNCIONP2");
		TSHandler tablaSp = new TSHandler();
		ArbolHandler arbolSp1 = new ArbolHandler();
		producciones.set(0, new DECL0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH, tablaSp);
		arbolH.add(tablaSp.getTabla());
		if ( sintactic.siguiente.accept(visitor).equals("comenzar"))
		{
			Bloque bloque = new Bloque();
			ArbolHandler arbolSp2 = new ArbolHandler();
			producciones.set(1, new BLOQUEF0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, bloque, arbolSp2, tablaSp.getTabla());
			arbolH.add(arbolSp2.getArbol());
			arbolS.setArbol(arbolH);
			tablaS.setTabla(tablaSp.getTabla());
		}else
		{
			merrores.mostrarYSkipearError("Se espera palabra reservada 'comenzar'", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}
		return r;
	}
}
