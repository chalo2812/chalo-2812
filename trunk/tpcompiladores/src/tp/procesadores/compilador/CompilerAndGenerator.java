package tp.procesadores.compilador;

import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class CompilerAndGenerator extends Compiler {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out
					.println("Este compilador del lenguaje LEB precisa de 1 parametro: "
							+ "\n\t1.- origen: archivo con el codigo fuente creado en LEB "
							+ "\n\nEJ: java -jar compilador.jar .\\fuente.leb "
							+ "\n\nLa salida quedara guardada en un archivo programaObjeto.asm en el mismo directorio donde se encuentra el jar.");
		} else {
			SintacticAnalyzer sa = new SintacticAnalyzer(args[0]);
			try {
				sa.compilar();
			} catch (Exception e) {
				System.out
						.println("Hay error\\es presente\\s en el archivo.. :'( ");
				return;
			}
			System.out.println("El archivo analizado se encuentra correcto sintacticamente, yay! :)\n" +
							   "*******************************************************************\n");
		}
	}

}
