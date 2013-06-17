package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class PASAJEP0 extends ProduccionC{

	public PASAJEP0(){
		SimboloTerminal comma = new SimboloTerminal(","); 
		producciones.add(comma);
		PASAJEPP0 pasajepp = new PASAJEPP0(); 
		producciones.add(pasajepp);
	}
	
	
	//PASAJE' -> ,PASAJE'' | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
				SintacticAnalyzer sintactic) {
		boolean r; 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}else
		{
			r = true; 
		}
		return r;
	}
}
