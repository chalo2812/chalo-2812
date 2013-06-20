package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PASAJE2 extends ProduccionC{

	public PASAJE2(){
		NUMERO numero = new NUMERO();  
		producciones.add(numero);
		PASAJEP0 pasajep = null;
		producciones.add(pasajep);
	}
	
	//PASAJE -> NUMERO PASAJE'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) {
		boolean r; 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ){
			producciones.set(1, new PASAJEP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		return r;
	}
}
