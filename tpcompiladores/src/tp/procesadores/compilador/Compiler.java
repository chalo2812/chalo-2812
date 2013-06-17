package tp.procesadores.compilador;


import tp.procesadores.analizador.sintactico.*;

/**
 * Clase principal del programa Compilador 
 */
public class Compiler {

	public static void main(String[] args) {
		//./src/testFile3
		SintacticAnalyzer sa = new SintacticAnalyzer(args[0]); 
		sa.Compilar();
	}
	
}
