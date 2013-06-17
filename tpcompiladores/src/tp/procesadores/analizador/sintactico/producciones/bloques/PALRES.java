package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.LEER0;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.MOSTRAR0;
import tp.procesadores.analizador.sintactico.producciones.funcionesrequeridas.MOSTRARLN0;

public class PALRES extends ProduccionC {

	public PALRES() {
		LEER0 leer = null;
		producciones.add(leer);
		MOSTRAR0 mostrar = null;
		producciones.add(mostrar);
		MOSTRARLN0 mostrarln = null;
		producciones.add(mostrarln);
	}

	// PALRES -> LEER | MOSTRAR | MOSTRARLN
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r = false;
//		System.out.println("PALRES " + sintactic.siguiente.coord.getY() + "  token: " + sintactic.siguiente.accept(visitor));
		if (sintactic.siguiente.accept(visitor).equals("leer")) 
		{
			producciones.set(0, new LEER0());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else 
		{
			if (sintactic.siguiente.accept(visitor).equals("mostrar")) 
			{
				producciones.set(1, new MOSTRAR0());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				if (sintactic.siguiente.accept(visitor).equals("mostrarln"))
				{
					producciones.set(2, new MOSTRARLN0());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				}
			}
		}
		return r;
	}
}