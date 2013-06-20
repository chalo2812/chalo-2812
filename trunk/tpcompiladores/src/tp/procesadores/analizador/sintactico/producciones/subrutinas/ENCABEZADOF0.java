package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;
import tp.procesadores.analizador.sintactico.producciones.constantesyvariables.TIPO0;
import tp.procesadores.analizador.sintactico.producciones.parametrizaciones.PARAMS0;

public class ENCABEZADOF0 extends ProduccionC {
	
	public ENCABEZADOF0(){
		PalabraReservada funcion = new PalabraReservada("funcion");
		producciones.add(funcion);
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		SimboloTerminal parentesis1 = new SimboloTerminal("(");
		producciones.add(parentesis1);
		PARAMS0 params =  null;
		producciones.add(params);
		SimboloTerminal parentesis2 = new SimboloTerminal(")");
		producciones.add(parentesis2);
		SimboloTerminal dospuntos = new SimboloTerminal(":");
		producciones.add(dospuntos);
		TIPO0 tipo = null;
		producciones.add(tipo);
		SimboloTerminal puntoycoma = new SimboloTerminal(";");
		producciones.add(puntoycoma);
	}
	
	//ENCABEZADOF -> funcion PALABRA(PARAMS): TIPO;
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r;
//		System.out.println("ENCABEZADOF");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{ 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{ 
				r = producciones.get(2).reconocer(lexic, visitor, sintactic); 
				if ( r )
				{
					producciones.set(3, new PARAMS0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						r = producciones.get(4).reconocer(lexic, visitor, sintactic); 
						if ( r )
						{
							r = producciones.get(5).reconocer(lexic, visitor, sintactic);
							if ( r )
							{ 
								producciones.set(6, new TIPO0());
								r = producciones.get(6).reconocer(lexic, visitor, sintactic);
								if ( r )
								{ 
									r = producciones.get(7).reconocer(lexic, visitor, sintactic);
									if (!r)
									{
										merrores.mostrarYSkipearError("Se espera punto y coma ';'", lexic, sintactic, visitor);
										sintactic.setEstadoAnalisis(false);
										r = true;
									}
								}
							}else
							{
								merrores.mostrarYSkipearError("Se espera asignacion de tipo ': tipo'", lexic, sintactic, visitor);
								sintactic.setEstadoAnalisis(false);
								r = true;
							}
						}
					}
				}else{
					merrores.mostrarYSkipearError("Se espera parentesis '(' ", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		if(!r)
		{
			sintactic.setEstadoAnalisis(false);
		}
		return r;
	}
}
