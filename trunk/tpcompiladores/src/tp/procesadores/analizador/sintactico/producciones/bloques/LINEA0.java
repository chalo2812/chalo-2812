package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.Produccion;

public class LINEA0 extends Produccion {

	public LINEA0()
	{
		LINEA1 linea1 = null;
		producciones.add(linea1);
		LINEA2 linea2 = null;
		producciones.add(linea2);
		LINEA3 linea3 = null;
		producciones.add(linea3);
		LINEA4 linea4 = null;
		producciones.add(linea4);
	}

	//LINEA ->   BLOQUESI | BLOQUEM | PALRES | PALABRA FPOSASIG
	
		//LINEA ->   BLOQUESI
			//	LINEA.ArbolS = BLOQUESI.ArbolS
		//LINEA ->   BLOQUEM
			//	LINEA.ArbolS = BLOQUEM.ArbolS
		//LINEA ->   PALRES 
			//	LINEA.ArbolS = PALRES.ArbolS
		//LINEA ->   PALABRA FPOSASIG
			//	FPOASIG.ArbolH = PALABRA.ArbolS
			//	LINEA.ArbolS = FPOASIG.ArbolS
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			 ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH)
	{
		boolean r;
//		System.out.println("LINEA0");
		if ( sintactic.siguiente.accept(visitor).equals("si"))
		{
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(0, new LINEA1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
			arbolS.setArbol(arbolSp1.getArbol());
		}
		else
		{
			if ( sintactic.siguiente.accept(visitor).equals("mientras"))
			{
				ArbolHandler arbolSp2 = new ArbolHandler();
				producciones.set(1, new LINEA2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}
			else
			{
				if (sintactic.siguiente.getClass() == PalabraReservada.class)
				{
					ArbolHandler arbolSp3 = new ArbolHandler();
					producciones.set(2, new LINEA3());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
					arbolS.setArbol(arbolSp3.getArbol());
				}
				else
				{
					if (sintactic.siguiente.getClass() == Palabra.class)
					{
						ArbolHandler arbolSp4 = new ArbolHandler();
						producciones.set(3, new LINEA4());
						r = producciones.get(3).reconocer(lexic, visitor, sintactic, arbolH, arbolSp4, tablaH);
						arbolS.setArbol(arbolSp4.getArbol());
					}
					else
					{
						r = false;
					}
				}
			}
		}
		return r;
	}
}