package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class FACTP0 extends Produccion{
	
	public FACTP0(){
		FACTP1 factp1 = new FACTP1();
		producciones.add(factp1);
		FACTP2 factp2 = new FACTP2(); 
		producciones.add(factp2);
	}
	

	
	//FACT' ->	[EXP] | (PASAJE) | e 
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//		System.out.println("FACTP0");
		if (sintactic.siguiente.accept(visitor).equals("[")){
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(0, new FACTP1()); 
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
			arbolS.setArbol(arbolSp1.getArbol());
		}else
		{
			if (sintactic.siguiente.accept(visitor).equals("("))
			{
				NodeVisitor identVisitor = new NodeVisitor();
				if (!tablaH.existeMetodo(arbolH.accept(identVisitor)) ) {
					merrores.mostrarErrorSemantico("El metodo \'"+ arbolH.accept(identVisitor) + "\' NO este declarado", sintactic);
					sintactic.setEstadoAnalisis(false);
				}
				
				ArbolHandler arbolSp2 = new ArbolHandler();
				producciones.set(1, new FACTP2()); 
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}else
			{
				arbolS.setArbol(arbolH);
				r = true; 
			}
		}
		return r; 
	} 
}
