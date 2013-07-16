package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.FuncionNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Funcion;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.MetodoHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class FUNCION0 extends Produccion {
	
	public FUNCION0(){
		ENCABEZADOF0 encabezado = null; 
		producciones.add(encabezado);
		FUNCIONP0 funcionp = null; 
		producciones.add(funcionp);
	}

	//ENCABEZADOF.TablaSimbolosH = FUNCION.TablaSimbolosH
	//FUNCION'.TablaSimboloH = ENCABEZADO.TablaSimboloS
	//FUNCION'.ArbolH = FUNCION.ArbolH 
	//FUNCION.ArbolS = FUNCION'.ArbolS
			
	//FUNCION ->  ENCABEZADOF FUNCION'
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS) 
	{
		boolean r; 
		TablaDeSimbolos tablaHija = new TablaDeSimbolos();
		tablaHija.setPadre(tablaH);

		Metodo metodoH = new Metodo();
		MetodoHandler metodoS = new MetodoHandler();
		producciones.set(0, new ENCABEZADOF0()); 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, metodoH, metodoS);
		tablaH.addMethod(metodoS.getMetodo());
		
		Funcion fAux = arbolH.acceptFuncVisitor(new FuncionNodeVisitor());
		fAux.setNombreFuncion(metodoH.getNombre());
		
		if ( r )
		{
			TSHandler tablaSp = new TSHandler();
			ArbolHandler arbolSp = new ArbolHandler();
			producciones.set(1, new FUNCIONP0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaHija, tablaSp);
			arbolS.setArbol(arbolSp.getArbol());
			tablaS.setTabla(tablaSp.getTabla());
		}
		return r;
	}

}
