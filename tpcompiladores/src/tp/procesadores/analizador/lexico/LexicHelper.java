package tp.procesadores.analizador.lexico;

import java.util.Arrays;
import java.util.List;

import tp.procesadores.analizador.lexico.tokens.*;
import tp.procesadores.analizador.lexico.tokens.Error;

/**
 *	Helper para el Analizar Lexico  
 */
public class LexicHelper {
	
	private StringBuffer lexema = new StringBuffer(""); 
	private static final int EOF = -1;
	
	/** 
	 * Ignora los comentarios entre llaves, 
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar  
	 */
	public void tratarComentario(Coordenadas coord, FileReader f, Caracter c) 
	{
		while (f.peek()!=EOF && c.getSimbolo()!='}')
		{
			if (c.getSimbolo() == '\n'){
            	coord.setY(coord.getY() + 1);
            	coord.setX(1);
			}
			c.setSimbolo(f.read());
			coord.setX(coord.getX()+1);
		}
		if ( f.peek() != -1)
		{
			c.setSimbolo(f.read());
			coord.setX(coord.getX()+1);
		}	
	}

	
	/**
	 * Devuelve un token de Entero, Natural o Error 
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token de Numero que puede ser Entero o Natural 
	 */
	public Token tratarNumero(Coordenadas coord, FileReader f, Caracter c) 
	{
		lexema.setLength(0);
		int colmini=coord.getX();

		lexema.append((char)c.getSimbolo());
		while ((f.peek()!=-1 && !esCortePalabraONumero( (f.peek()) )) && Character.isDigit(f.peek()))
		{
			leerYAcumular(coord,f,c,lexema);
		}
		coord.setX(coord.getX()+1);
		if(f.peek()=='n'){ 
			//Tratar Natural 
			leerYAcumular(coord,f,c,lexema);
			if(!esCortePalabraONumero((f.peek())))
			{
				//Sigo leyendo y devuelvo el lexema de error 
				while ((f.peek()!=-1 && !esCortePalabraONumero( (f.peek()) )) )
				{
					leerYAcumular(coord,f,c,lexema);
				}
				return new Error(coord.getY(), colmini, lexema.toString(), 1);
			}else
			{
				Natural natural = new Natural(coord.getY(), colmini, Integer.parseInt(lexema.toString().substring(0, lexema.length()-1)));
				return natural; 
			}
		}else
		{  //Tratar entero 
			if(!esCortePalabraONumero((f.peek())))
			{
				while ((f.peek()!=-1 && !esCortePalabraONumero( (f.peek()) )) )
				{
					leerYAcumular(coord,f,c,lexema);
				}
				return new Error(coord.getY(), colmini, lexema.toString(), 1);
			}else
			{
				Entero entero = new Entero(coord.getY(), colmini, Integer.parseInt(lexema.toString()));  	
				return entero;
			}
		}
	
	}

	/** 
	 * Trata Cadena de caracteres y devuelve un token de tipo Cadena o Error si la cadena no cierra   
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Token Cadena con el string entre comillas simples 
	 */
	public Token tratarCadena(Coordenadas coord, FileReader f, Caracter c) 
	{
		lexema.setLength(0);
		int colmini=coord.getX();
		
		lexema.append((char)c.getSimbolo());
		while ( f.peek()!=-1 && f.peek()!='\'' && f.peek()!='\n')
		{
			leerYAcumularCadena(coord,f,c,lexema);
		}
		
		coord.setX(coord.getX()+1);
		if (f.peek()=='\'')
		{
			leerYAcumularCadena(coord,f,c,lexema);
			Cadena cadena = new Cadena(coord.getY(), colmini, lexema.toString());
			return	cadena; 
		}else
		{   
			//f.peek()!=-1 || f.peek()!='\n'
			Error tokenError = new Error(coord.getY(), colmini, lexema.toString(), 2); 
			return tokenError;
		}	
	}	

	/**
	 * Devuelve un token de Operador o Error si se trata de un operador no valido  
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token de Operador  
	 */
	public Token tratarOperador(Coordenadas coord, FileReader f, Caracter c) 
	{
		lexema.setLength(0);
		int colmini=coord.getX();
		boolean error=false;
		lexema.append((char)c.getSimbolo());
		if ( esOperadorDoble(c.getSimbolo()) && f.peek()!=-1 )  
		{
			if (c.getSimbolo()==f.peek() )
			{
				leerYAcumular(coord,f,c,lexema);
			}
			else
			{
				switch(c.getSimbolo())
				{ 
					case '!': 
						if (f.peek() == '=')
						{ 
							leerYAcumular(coord,f,c,lexema);
							if (f.peek() == '=')
							{ 
								leerYAcumular(coord,f,c,lexema);
							}
						}
						else
						{ 
								error = true; 
						}
						break;
					case ':':
						if (f.peek() == '=')
						{ 
							leerYAcumular(coord,f,c,lexema);
						}
						break; 
				}
			}
		}
		coord.setX(coord.getX()+1);
		if (error)
		{
			Error tokenError = new Error(coord.getY(), colmini, lexema.toString(), 2); 
			return tokenError;
		}else
		{
			Operador operador = new Operador(coord.getY(), colmini, lexema.toString()); 
			return operador; 
		}
	}
	
	
	/**
	 * Devuelve un de tipo Palabra o Error si contiene errores lexicos  
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token Palabra
	 */
	public Token tratarPalabra(Coordenadas coord, FileReader f, Caracter c)      // mientras fin-mientras 
	{
		lexema.setLength(0);
		int colmini=coord.getX();
		boolean evaluarFin = false;
		
		lexema.append((char)c.getSimbolo());
		List<String> fines = Arrays.asList("fin-si", "fin-proc", "fin-func", "fin-mientras", "fin-globales"); 
			
		while (f.peek()!=EOF && !esCortePalabraONumero(f.peek()) && (Character.isDigit(f.peek()) || Character.isLetter(f.peek())))  
		{
			leerYAcumular(coord,f,c,lexema);
		}
		
		//Acumular posible palabra reservada fin- 
		if( (new String("fin").equals(lexema.toString())) && (f.peek()=='-'))
		{
			leerYAcumular(coord,f,c,lexema);
			evaluarFin = true; 
			while ((f.peek()!=EOF && !esCortePalabraONumero( (f.peek()) ))) 
			{
				leerYAcumular(coord,f,c,lexema);
			}
		}
		coord.setX(coord.getX()+1);
		
		// Si sali del while por Palabras con simbolos no aceptados
		if(!Character.isDigit(f.peek()) && !Character.isLetter(f.peek()) && !esCortePalabraONumero(f.peek()) ) 
		{
			while ((f.peek()!=-1 && !esCortePalabraONumero( (f.peek()) ))) 
			{
				leerYAcumular(coord,f,c,lexema);
			}
			Error error = new Error(coord.getY(), colmini, lexema.toString(), 4);
			return error;
		}
		
		//Tratar posibles palabras reservadas con fin- o error lexico 
		if (evaluarFin) 
		{ 
			if (fines.contains(lexema.toString())) 
			{  
					PalabraReservada p = new PalabraReservada(coord.getY(), colmini, lexema.toString());
					return p;
			}else
		    {
		        	Error error = new Error(coord.getY(), colmini, lexema.toString(), 5);
		        	return error;
		    }
		}else
		{	
			if (new String("fin").equals(lexema.toString()))	//error lexico por uso de palabra reservada
			{
				Error error = new Error(coord.getY(), colmini, lexema.toString(), 5);
	        	return error;
			}else
			{
				if(esReservada(lexema.toString()))
				{ 
					PalabraReservada p = new PalabraReservada(coord.getY(), colmini, lexema.toString());
					return p;
				}
				else
				{
					Palabra p = new Palabra(coord.getY(), colmini, lexema.toString());
					return p;
				} 
			}
		}
	}
	
	/**
	 * Verdadero si el simbolo c puede llegar a ser operador doble 
	 * 
	 * @param c 	caracter con el simbolo a evaluar 
	 * @return true si el simbolo que se esta tratando puede ser un operador doble
	 */
	private boolean esOperadorDoble(int c) {
		if	( 	
				(c=='=') || 
				(c=='!') ||
				(c=='+') ||
				(c=='*') ||
				(c=='-') ||
				(c=='/') ||
				(c==':') ||
				(c=='<') 
			)
				return true;
		else
				return false;
	}

	/**	
	 * @param s 	caracter con el simbolo a evaluar 
	 * @return true si el simbolo que se esta tratando representa el fin de un lexema de tipo Palabra o Numero 
	 */
	private boolean esCortePalabraONumero(int s) {
		if	(
				(s=='=') ||
				(s==':') ||
				(s==',') ||
				(s=='+') ||
				(s=='-') ||
				(s=='/') ||
				(s=='*') ||
				(s==';') ||
				(s=='(') ||
				(s==')') ||
				(s=='<') ||
				(s=='{') ||
				(s==' ') ||
				(s=='\'') ||
				(s=='\n') ||
				(s=='\t') ||
				(s=='[') ||
				(s==']') ||
				(s=='!') || 
				(s == EOF)
			)
				return true;
		else
				return false;
	}

	/** Lee los caracteres de un error lexico y general el Token de Error    
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token de Error con el lexema de error 
	 */
	public Error tratarError(Coordenadas coord, FileReader fr, Caracter caracter) {
		lexema.setLength(0);
		int colmini=coord.getX();
		
		lexema.append((char)caracter.getSimbolo());
		while ((fr.peek()!=-1 && !esCortePalabraONumero( (fr.peek()) ))) 
		{
			leerYAcumular(coord,fr,caracter,lexema);
		}
//		coord.setX(coord.getX()+1);
		Error error = new Error(coord.getY(), colmini, lexema.toString(), 4);
		return error;
	}
	
	
	/**
	 * Procedimiento que lee un caracter, actualiza las coordenadas del cursor y agrega el simbolo leido 
	 * al lexema 
	 * 
	 * @param coord  Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		 Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	 Objeto Caracter con el primer simbolo a tratar
	 * @param lexema Objeto que contendra el lexma del Token  
	 * 
	 */
	private static void leerYAcumular(Coordenadas coord, FileReader f, Caracter c, StringBuffer lexema)
	{
		c.setSimbolo(f.read());
		coord.setX(coord.getX()+1);
		lexema.append((char)c.getSimbolo());
	}
	
	/**
	 * Procedimiento que lee un caracter, actualiza las coordenadas del cursor y agrega el simbolo leido 
	 * al lexema  
	 * 
	 * @param coord  Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		 Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	 Objeto Caracter con el primer simbolo a tratar
	 * @param lexema Objeto que contendra el lexma del Token  
	 * 
	 */
	private static void leerYAcumularCadena(Coordenadas coord, FileReader fr, Caracter caracter, StringBuffer lexema)
	{
		caracter.setSimbolo(fr.readInsensitive());
//		if(caracter.getSimbolo()=='\t') //Tratar TAB
//			coord.setX(coord.getX() + (4 - ((coord.getX()-1 )%4)));
//		else 
			coord.setX(coord.getX()+1);  
		lexema.append((char)caracter.getSimbolo());
	}
	
	/**
	 * Esta funcion privada devuelve true si palabra peretence al conjunto de Palabras Reservadas
	 * del lenugaje 
	 *  
	 * @param palabra	Es la palabra a evaluar 
	 * @return			true si ES reservada
	 */
	private boolean esReservada(String palabra)
	{
		boolean result = false; 
		List<String> palabrasReservadas = Arrays.asList("const", "adelantado", "var", "val", "ref", "natural", "entero",
		"procedimiento", "funcion", "comenzar", "globales", "mientras", "hacer", 
		"si", "sino", "entonces", "leer", "mostrar", "mostrarln","and", "or", "anatural", "aentero", "par", "impar");
		if (palabrasReservadas.contains(palabra.toLowerCase()))
			result = true; 
		return result; 
	}
} 
