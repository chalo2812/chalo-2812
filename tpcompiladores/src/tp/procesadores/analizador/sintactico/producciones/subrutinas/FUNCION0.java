package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FUNCION0 extends ProduccionC {
	
	public FUNCION0(){
		ENCABEZADOF0 encabezado = null; 
		producciones.add(encabezado);
		FUNCIONP0 funcionp = null; 
		producciones.add(funcionp);
	}

	//FUNCION ->  ENCABEZADOF FUNCIONÕ
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r; 
//		System.out.println("FUNCION0");
		producciones.set(0, new ENCABEZADOF0()); 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			producciones.set(1, new FUNCIONP0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}

}
