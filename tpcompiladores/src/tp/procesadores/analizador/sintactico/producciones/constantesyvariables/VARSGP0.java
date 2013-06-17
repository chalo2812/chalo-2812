package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSGP0 extends ProduccionC
{
	public VARSGP0()
	{
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		TVARG0 tvarg = null;
		producciones.add(tvarg); 
	}
	
	//VARSGO ->   PALABRA TVARG
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("VARSGP0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{ 
			producciones.set(1, new TVARG0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}
		else
		{
			merrores.mostrarYSkipearError("Se esperaba un indentificador", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}	
		return r;
	}
}
