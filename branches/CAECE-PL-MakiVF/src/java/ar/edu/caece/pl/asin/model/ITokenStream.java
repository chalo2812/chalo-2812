package ar.edu.caece.pl.asin.model;

import ar.edu.caece.pl.alex.model.IToken;

public interface ITokenStream {

	public void leer();
	
	public IToken getTokenActual();
	
	public IToken getTokenSiguiente();
	
}
