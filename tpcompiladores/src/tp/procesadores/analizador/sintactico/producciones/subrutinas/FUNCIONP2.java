package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.bloques.BLOQUEF0;
import tp.procesadores.analizador.sintactico.producciones.declaraciones.DECL0;

public class FUNCIONP2 extends ProduccionC {
	
	public FUNCIONP2(){
		DECL0 decl =  null;
		producciones.add(decl);
		BLOQUEF0 bloquef = null;
		producciones.add(bloquef);
	}
	
	//FUNCIONÕ-> DECL BLOQUEF
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("FUNCIONP2");
		producciones.set(0, new DECL0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( sintactic.siguiente.accept(visitor).equals("comenzar"))
		{
			producciones.set(1, new BLOQUEF0());
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
