package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.ProcedimientoNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.MetodoHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PROCEDIMIENTO0 extends ProduccionC {
	
	public PROCEDIMIENTO0(){
		ENCABEZADOP0 encabezadop = null;
		producciones.add(encabezadop);
		PROCEDIMIENTOP0 procp = null;
		producciones.add(procp);
	}
	
	//ENCABEZADOP.TablaSimbolosH = PROCEDIMIENTO.TablaSimbolosH
	//PROCEDIMIENTO'.TablaSimboloH = ENCABEZADO.TablaSimboloS
	//PROCEDIMIENTO'.ArbolH = PROCEDIMIENTO.ArbolH 
	//PROCEDIMIENTO.ArbolS = FUNCPROCEDIMIENTOION'.ArbolS 
	
	//PROCEDIMIENTO   ->   ENCABEZADOP PROCEDIMIENTO�
//	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS){
		boolean r; 
//		System.out.println("PROCEDIMIENTO0");
		TablaDeSimbolos tablaHija = new TablaDeSimbolos();
		tablaHija.setPadre(tablaH);

		Metodo metodoH = new Metodo();
		MetodoHandler metodoS = new MetodoHandler();
		producciones.set(0, new ENCABEZADOP0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, metodoH, metodoS);
		if (tablaH.equals(null)){
			tablaH = new TablaDeSimbolos(); 
		}
		tablaH.addMethod(metodoS.getMetodo());

		Procedimiento pAux = arbolH.acceptProcVisitor(new ProcedimientoNodeVisitor());
		pAux.setNombreProcedimiento(metodoH.getNombre());
		
		if ( r )
		{
			TSHandler tablaSp = new TSHandler();
			ArbolHandler arbolSp = new ArbolHandler();
			producciones.set(1, new PROCEDIMIENTOP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaHija, tablaSp);
			arbolS.setArbol(arbolSp.getArbol());
			tablaS.setTabla(tablaSp.getTabla());
		}
		return r;
	}
	
}
