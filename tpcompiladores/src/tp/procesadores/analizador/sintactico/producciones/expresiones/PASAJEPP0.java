package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PASAJEPP0 extends ProduccionC {

	public PASAJEPP0(){
		PASAJEPP1 pp1 = null; 
		producciones.add(pp1);
		PASAJEPP2 pp2 = null;
		producciones.add(pp2);
	}
	
	
	/**
	 * 	PASAJE'' ->   PALABRA PASAJE'|
	 * 				  NUMERO PASAJE'
	 */
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) {
		boolean r; 
		if ( sintactic.siguiente.getClass() == Palabra.class) 
		{
			producciones.set(0, new PASAJEPP1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{
			if (( sintactic.siguiente.getClass() == Entero.class) || ( sintactic.siguiente.getClass() == Natural.class))
			{
				producciones.set(1, new PASAJEPP2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				merrores.mostrarYSkipearError("Se espera identificador o parametro", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
