package tp.procesadores.analizador.sintactico;

import java.io.File;
import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Eof;
import tp.procesadores.analizador.lexico.tokens.Token;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.producciones.SP;

public class SintacticAnalyzer {

	public File file;
	public Token actual = new Token(0,0); 
	public Token siguiente = new Token(0,0); 
	private boolean estadoAnalisis = true;

	public SintacticAnalyzer(String filePath){
			file = new File (filePath); 
	}  
	
	public void setActual(Token actual) {
		this.actual = actual;
	}

	public void setSiguiente(Token siguiente) {
		this.siguiente = siguiente;
	}
	
	public void Compilar()
	{
		boolean r;
		TokensVisitor visitor = new TokensVisitor(); 
		LexicAnalyzer lexic = new LexicAnalyzer(file);
		siguiente = lexic.getToken();
		SP sp = new SP(); 
		if( siguiente.getClass()!= Eof.class) 
		{ 
			r = sp.reconocer(lexic, visitor, this);
			if (r && this.getEstadoAnalisis()){
				System.out.println("El archivo analizado se encuentra correcto sintacticamente, yay! :) ");
			}else{
				System.out.println("Hay error\\es sintactico\\s presente\\s en el archivo.. :'( ");
			}
		} 
	} 

	
	public void consumir(LexicAnalyzer lexic)
	{
		setActual(siguiente);
		setSiguiente(lexic.getToken()); 
	}

	public boolean getEstadoAnalisis() {
		return estadoAnalisis;
	}

	public void setEstadoAnalisis(boolean estadoAnalisis) {
		this.estadoAnalisis = estadoAnalisis;
	}	
}
