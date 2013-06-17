package tp.procesadores.compilador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import tp.procesadores.analizador.lexico.FileReader;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

public class CompilerAndGenerator extends Compiler {

	public static File file;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out
					.println("Este compilador del lenguaje LEB precisa de 1 parametro: "
							+ "\n\t1.- origen: archivo con el codigo fuente creado en LEB "
							+ "\n\nEJ: java -jar compilador.jar .\\fuente.leb "
							+ "\n\nLa salida quedara guardada en un archivo programaObjeto.asm en el mismo directorio donde se encuentra el jar.");
		} else {
			SintacticAnalyzer sa = new SintacticAnalyzer(args[0]);
			sa.Compilar();
			if (sa.getEstadoAnalisis()) {
				CompilerAndGenerator generar = new CompilerAndGenerator();
				String salida = generar.transformarAssembler(sa, args[0]);
				file = sa.file;
				try {
					generar.writeAsmFile(salida, sa.file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

			}
		}
	}

	private String transformarAssembler(SintacticAnalyzer sa, String file) {
		borrarAsm(sa);
		String salida = crearAsm(sa);
		return salida;
	}

	private void borrarAsm(SintacticAnalyzer sa) {
		File archivo = new File(sa.file.getName());
		if (sa.file.exists()) {
			if (sa.file.getName().endsWith(".asm")) {
				if (archivo.delete()) {
					System.out.println("Se borro el archivo " + archivo
							+ " de salida exitosamente.");
				} else {
					System.out.println("No se borro el archivo " + archivo
							+ "de salida.");
				}
			} 
		}

	}

	private String crearAsm(SintacticAnalyzer sa) {
		StringBuilder entrada = new StringBuilder();
		Assembler assembler = new Assembler();
		assembler.generateGlobalMemory();
		entrada.append(assembler.start());
		entrada.append(assembler.finish());

		return entrada.toString();

	}

	private void writeAsmFile(String code, String archivo)
			throws FileNotFoundException {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(archivo + ".asm", "UTF-8");
			writer.println(code);
			writer.close();
		} catch (UnsupportedEncodingException e1) {
			System.err.println("Error generando el archivo asm");
			e1.printStackTrace();
		}
	}

}
