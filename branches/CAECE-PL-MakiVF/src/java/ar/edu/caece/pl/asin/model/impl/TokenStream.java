package ar.edu.caece.pl.asin.model.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ar.edu.caece.pl.alex.manager.IAnalizadorLexico;
import ar.edu.caece.pl.alex.manager.impl.AnalizadorLexico;
import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asin.model.ITokenStream;

public class TokenStream implements ITokenStream{
	
	/* TOKENS PRINCIPALES */
	private IToken tokenActual;
	private IToken tokenSiguiente;
	
	/* ACTORES PRINCIPALES */
	private IAnalizadorLexico alex;
	
	/* ELEMENTOS AUXILIARES */
	private static final String INIT_ERROR = "No se asigno fuente para leer";
	
	/* INICIALIZACION */	
	/** Utilizar este constructor si se lo quiere hacer abrir un archivo
	 * @throws FileNotFoundException */
	public TokenStream ( String filename) throws FileNotFoundException {
		
		this(new FileInputStream( filename ));
	}
	
	public TokenStream (InputStream is) {
		
		if (is != null) {
			
			//Inicializo el Analizador Lexico
			alex = new AnalizadorLexico(is);
			
			//Efectuo la primera lectura para inicializar el token Siguiente y quedar parado antes de comenzar
			this.tokenSiguiente = alex.obtSiguienteToken();
			
		} else {
			
			throw new IllegalArgumentException( INIT_ERROR );
		}
	}

	
	public void leer() {
		
		this.tokenActual = this.tokenSiguiente;

		//Si lo que viene es EOF no leer siguiente
		if (this.tokenActual.equals(new Token(IToken.TYPE_FIN_DE_ARCHIVO))) {
			
			return; 
			
		} else {
			
			this.tokenSiguiente = alex.obtSiguienteToken();
		}
	}
	
	public IToken getTokenActual() {
		return this.tokenActual;
	}
	
	public IToken getTokenSiguiente() {
		return this.tokenSiguiente;
	}

}
