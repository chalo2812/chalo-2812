package tp.procesadores.compilador;


import tp.procesadores.analizador.sintactico.*;

/**
 * Clase principal del programa Compilador 
 */
public class Compiler {

	public static void main(String[] args) {
		if ( args.length < 1) {
			System.out.println("Este compilador del lenguaje LEB precisa de 1 parametro: " +
					"\n\t1.- origen: archivo con el codigo fuente creado en LEB " +
					"\n\nEJ: java -jar compilador.jar .\\fuente.leb " +
					"\n\nLa salida quedara guardada en un archivo programaObjeto.asm en el mismo directorio donde se encuentra el jar.");
		}else 
		{ 
			SintacticAnalyzer sa = new SintacticAnalyzer(args[0]); 
			sa.Compilar();
		}
	}
}
