package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NATURAL;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TVARG3 extends ProduccionC {

	
	public TVARG3(){
		SimboloTerminal corchete1 = new SimboloTerminal("[");
		producciones.add(corchete1);
		NATURAL numero = new NATURAL();
		producciones.add(numero);
		SimboloTerminal corchete2 = new SimboloTerminal("]");
		producciones.add(corchete2);
		VECT0 vect = null;
		producciones.add(vect);
		VARSGPP0 varsgpp = null;
		producciones.add(varsgpp);
	}
	
	//TVARG ->   [ NATURAL ] VECT VARSGPP
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) 
	{
		boolean r; 
//		System.out.println("TVARG3");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					if (sintactic.siguiente.accept(visitor).equals(":"))
					{
						producciones.set(3, new VECT0());
						r = producciones.get(3).reconocer(lexic, visitor, sintactic);
						if ( r )
						{
							producciones.set(4, new VARSGPP0());
							r = producciones.get(4).reconocer(lexic, visitor, sintactic);
						}
					}
					else
					{
						if ( sintactic.siguiente.accept(visitor).equals(";"))
						{
							merrores.mostrarYSkipearError("Se esperaba un ':' mas definicion de variables/vector o ',' y otro identificador ", lexic, sintactic, visitor);
							sintactic.setEstadoAnalisis(false);
							r = true;
						}
						else
						{
						producciones.set(4, new VARSGPP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic);
						}
					}
				}
				else
				{
					merrores.mostrarYSkipearError("Se esperaba simbolo ']'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}
}