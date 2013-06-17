package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.bloques.BLOQUEP0;
import tp.procesadores.analizador.sintactico.producciones.declaraciones.DECL0;

public class PROCEDIMIENTOP2 extends ProduccionC {

	public PROCEDIMIENTOP2(){
		DECL0 decl = null;
		producciones.add(decl);
		BLOQUEP0 bloquep = null; 
		producciones.add(bloquep);
	}
	
	/**
	 * PROCEDIMIENTOÕ  -> DECL BLOQUEP
	 */
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("PROCEDIMIENTOP2");
		producciones.set(0, new DECL0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( sintactic.siguiente.accept(visitor).equals("comenzar"))
		{
			producciones.set(1, new BLOQUEP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera palabra reservada 'comenzar'", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}
		return r;
	}
	
}
