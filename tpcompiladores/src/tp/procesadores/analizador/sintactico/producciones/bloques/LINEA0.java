package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class LINEA0 extends ProduccionC {

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
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("LINEA0");
		if ( sintactic.siguiente.accept(visitor).equals("si"))
		{
			producciones.set(0, new LINEA1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{
			if ( sintactic.siguiente.accept(visitor).equals("mientras"))
			{
				producciones.set(1, new LINEA2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				if (sintactic.siguiente.getClass() == PalabraReservada.class)
				{
					producciones.set(2, new LINEA3());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				}else
				{
					if (sintactic.siguiente.getClass() == Palabra.class)
					{
						producciones.set(3, new LINEA4());
						r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					}else
					{
						r = false;
					}
				}
			}
		}
		return r;
	}
}