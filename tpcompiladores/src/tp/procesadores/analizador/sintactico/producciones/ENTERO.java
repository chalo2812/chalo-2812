package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Entero;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class ENTERO extends ProduccionC {
	
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
//		System.out.println("ENTERO");
		if ( sintactic.siguiente.getClass() == Entero.class){
			sintactic.consumir(lexic);
		}else
		{
			merrores.mostrarYSkipearError("Se espera numero de tipo Entero", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
		}
		return true;
	}
}
