package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.ProductoNatural;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TERMP3 extends ProduccionC{
	
	public TERMP3(){
		SimboloTerminal por = new SimboloTerminal("**");
		producciones.add(por);
		FACT0 fact = null; 
		producciones.add(fact);
		TERMP0 termp = null; 
		producciones.add(termp);
	}

	
	//TERM1'.ArbolH = CrearNodoMultiNatural ( TERM'.ArbolH, CrearNodoTerminal(FACT.valor()) ) 
	//TERM1'.ArbolS = EXP'1.ArbolS "" +

	//TERM' ->  ** FACT TERM'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//		System.out.println("TERMP3");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(1, new FACT0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
			if ( r )
			{ 
				ArbolHandler arbolSp2 = new ArbolHandler();
				ProductoNatural producto= new ProductoNatural(arbolH, arbolSp1.getArbol());
//				arbolH = arbolUtils.deepCopy(producto);
				arbolH = producto; 
				producciones.set(2, new TERMP0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}
		}
		return r;
	}	
}
