package tp.procesadores.manejador.errores;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Eof;
import tp.procesadores.analizador.lexico.tokens.Palabra;
import tp.procesadores.analizador.lexico.tokens.Token;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class ManejadorErrores {

	public void mostrarYSkipearError(String texto, LexicAnalyzer lexic, SintacticAnalyzer sintactic, TokensVisitor visitor){
		System.out.println("Error en linea: " + sintactic.actual.coord.getY()
				+ " columna: " + sintactic.actual.coord.getX());
		System.out.println(texto);
		if(sintactic.actual.getClass() == Token.class){
			sintactic.setActual(new Palabra(0,0,"first token"));
		}
		while(!sintactic.actual.accept(visitor).equals(";") && (sintactic.siguiente.getClass() != Eof.class))
		{
			sintactic.consumir(lexic);
		}
	}
}
