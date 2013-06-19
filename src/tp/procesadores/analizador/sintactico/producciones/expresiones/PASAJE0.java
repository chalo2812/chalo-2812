package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PASAJE0 extends ProduccionC {

	public PASAJE0() {
		PASAJE1 p1 = null;
		producciones.add(p1);
		PASAJE2 p2 = null;
		producciones.add(p2);
	}

	// PASAJE -> PALABRA PASAJE' | NUMERO PASAJE' | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
			TablaDeSimbolos tablaH) {
		boolean r;
		if (sintactic.siguiente.getClass() == Palabra.class) {
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(0, new PASAJE1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic,
					arbolH, arbolSp1, tablaH);
			arbolS.setArbol(arbolSp1.getArbol());
		} else {
			if ((sintactic.siguiente.getClass() == Entero.class)
					|| (sintactic.siguiente.getClass() == Natural.class)) {
				ArbolHandler arbolSp2 = new ArbolHandler();
				producciones.set(1, new PASAJE2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic,
						arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			} else {
				arbolS.setArbol(arbolH);
				r = true;
			}
		}
		return r;
	}
}
