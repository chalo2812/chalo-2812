package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Natural;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class NATURAL extends ProduccionC {
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
//		System.out.println("NATURAL");
		if ( sintactic.siguiente.getClass() == Natural.class){
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera numero de tipo Natural", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}
}
