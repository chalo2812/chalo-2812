package tp.procesadores.analizador.sintactico.producciones.declaraciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class DECL0 extends Produccion 
{
	public DECL0 ()
	{
		DECLARACIONES0 declaraciones = null;
		producciones.add(declaraciones);
		DECL0 decl = null;
		producciones.add(decl);
	}
	
	//DECL -> DECLARACIONES DECL | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS)  
	{
		boolean reconoce; 

		TSHandler tablaSp1 = new TSHandler();
		ArbolHandler arbolSp1 = new ArbolHandler();
		producciones.set(0, new DECLARACIONES0());
		reconoce = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH, tablaSp1);
		if ( reconoce ) 
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			TSHandler tablaSp2 = new TSHandler();
			producciones.set(1, new DECL0());
			reconoce = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaSp1.getTabla(), tablaSp2);
			tablaS.setTabla(tablaSp2.getTabla());
			arbolS.setArbol(arbolSp2.getArbol());
		}else{
		   reconoce = true;
			tablaS.setTabla(tablaH);
			arbolS.setArbol(arbolH);
		}
		return reconoce; 				
	}		
}
