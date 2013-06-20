package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PROCEDIMIENTO0 extends ProduccionC {
	
	public PROCEDIMIENTO0(){
		ENCABEZADOP0 encabezadop = null;
		producciones.add(encabezadop);
		PROCEDIMIENTOP0 procp = null;
		producciones.add(procp);
	}
	
	//PROCEDIMIENTO   ->   ENCABEZADOP PROCEDIMIENTOÕ
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("PROCEDIMIENTO0");
		producciones.set(0, new ENCABEZADOP0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(0, new PROCEDIMIENTOP0());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}
	
}
