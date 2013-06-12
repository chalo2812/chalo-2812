package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.lexico.tokens.*;
import tp.procesadores.analizador.lexico.tokens.Error;

public class TokensVisitor implements Visitor{

	public String visit(Token token) 
	{
//		System.out.println("From Visitor - Token:\n" + token.getClass());
		return null; 
	}

	/**
	 * Vistita un objeto Palabra y accede a su lexema
	 * 
	 * @param palabra	objeto a ser visitado
	 */
	public String visit(Palabra palabra)  
	{
//		System.out.println("LEXEMA:\n" + palabra.getLexema());
		return palabra.getLexema(); 
	}

	/**
	 * Vistita un objeto PalabraReservada y accede a su lexema
	 * 
	 * @param palabra	objeto a ser visitado
	 */
	public String visit(PalabraReservada palabra) 
	{
//		System.out.println("LEXEMA:\n" + palabra.getLexema());
		return palabra.getLexema();
	}
	
	/**
	 * Vistita un objeto Operador y accede a su lexema
	 * 
	 * @param operador objeto a ser visitado
	 */
	public String visit(Operador operador) 
	{
//		System.out.println("LEXEMA:\n" + operador.getLexema());
		return operador.getLexema(); 
	}
	
	/**
	 * Vistita un objeto Entero y accede a su lexema
	 * 
	 *  @param Entero	objeto a ser visitado
	 */
	public String visit(Entero entero) 
	{
//		System.out.println("LEXEMA:\n" + Integer.toString(entero.getLexema()));
		return Integer.toString(entero.getLexema());
	}
	
	/**
	 * Vistita un objeto Natural y accede a su lexema
	 * 
	 *  @param natural 	objeto a ser visitado
	 */
	public String visit(Natural natural) 
	{
//		System.out.println("LEXEMA:\n" + Integer.toString(natural.getLexema()));
		return Integer.toString(natural.getLexema());
	}
	
	/**
	 * Vistita un objeto Eof y accede a su lexema
	 * 
	 * @param eof	objeto a ser visitado
	 */
	public String visit(Eof eof) 
	{
//		System.out.println("LEXEMA:\n" + eof.getLexema());
		return eof.getLexema(); 
	}
	
	/**
	 * Vistita un objeto Error y accede a su lexema y codigo de error
	 * 
	 * @param errpr objeto a ser visitado
	 */
	public String visit(Error error) 
	{
//		System.out.println("LEXEMA:\n" + error.getLexema() + "\nCODIGO ERROR: " + Integer.toString(error.getCodError()));
		return error.getLexema(); 
	}
	
	/**
	 * Vistita un objeto Cadena y accede a su lexema
	 * 
	 *  @param cadena	objeto a ser visitado
	 */
	public String visit(Cadena cadena) 
	{
//		System.out.println("LEXEMA:\n" + cadena.getLexema());
		return cadena.getLexema(); 
	}
} 
