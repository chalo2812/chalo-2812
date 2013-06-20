package tp.procesadores.analizador.sintactico;

import java.io.File;
import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.Eof;
import tp.procesadores.analizador.lexico.tokens.Token;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolUtils;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.sintactico.producciones.SP;

public class SintacticAnalyzer {

	private File file;
	public Token actual = new Token(0, 0);
	public Token siguiente = new Token(0, 0);
	private boolean estadoAnalisis = true;
	private String archivo;

	public SintacticAnalyzer(String filePath) {
		file = new File(filePath);
	}

	public void setActual(Token actual) {
		this.actual = actual;
	}

	public void setSiguiente(Token siguiente) {
		this.siguiente = siguiente;
	}

	public void Compilar() {
		boolean r;
		TokensVisitor visitor = new TokensVisitor();
		LexicAnalyzer lexic = new LexicAnalyzer(file);
		siguiente = lexic.getToken();

		SP sp = new SP();
		ClaseNodo arbolH = new ClaseNodo();
		ArbolHandler arbolS = new ArbolHandler();

		if (siguiente.getClass() != Eof.class) {
			// r = sp.reconocer(lexic, visitor, this, new Programa(), arbolS,
			// new TablaDeSimbolos());
			r = sp.reconocer(lexic, visitor, this, arbolH, arbolS);
			if (r && this.getEstadoAnalisis()) {
				System.out
						.println("El archivo analizado se encuentra correcto "
								+ "sintacticamente, yay! :) \n*****"
								+ "*********************************"
								+ "*****************************\n");
				ArbolUtils a = new ArbolUtils();
				// a.showArbol(arbolS.getArbol());
				a.escribirArchivoSalida(file.getAbsolutePath(), arbolS.getArbol());
				// tablaS.getTabla().mostrarTabla("");
			} else {
				System.out
						.println("Hay error\\es presente\\s en el archivo.. :'( ");
			}
		}
	}

	public void consumir(LexicAnalyzer lexic) {
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
