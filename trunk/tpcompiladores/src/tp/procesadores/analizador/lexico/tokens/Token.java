package tp.procesadores.analizador.lexico.tokens;

import tp.procesadores.analizador.lexico.Coordenadas;
import tp.procesadores.analizador.lexico.tokens.visitor.*;

/**
 * Token Generico  
 */

public class Token implements Visitable {
	public Coordenadas coord; 
	
	public Token(int fila, int columna) { 
		coord = new Coordenadas(columna,fila); 
	} 

	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
}
