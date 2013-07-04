package tp.procesadores.compilador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.compilador.generadorcodigo.constantes.Constants;

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
				sa.Compilar();
			} catch (Exception e) {
				return;
			}
			
			CompilerAndGenerator generar = new CompilerAndGenerator();
			System.out.println("El archivo analizado se encuentra correcto "
					+ "sintacticamente, yay! :) \n*****"
					+ "*********************************"
					+ "*****************************\n");
			generar.transformarAssembler(sa, args[0]);
		}
	}

	private void transformarAssembler(SintacticAnalyzer sa, String file) {
		borrarASM(file);
		crearASM(file);
	}

	private void crearASM(String file) {
		File output = new File(file + ".asm");

		PrintWriter fileWriter;
		try {
			fileWriter = new PrintWriter(output);
			String resultado = armadoEstructuraASM();

			// resultado = nodo.generarCodigo(new Codigo(), new TempManager(),
			// new LabelManager());
			fileWriter.write(resultado);// resultado.getCodigo());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String armadoEstructuraASM() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(encabezadoASM());
		mensaje.append(parteDelArchEntradaASM());
		mensaje.append(finASM());
		return mensaje.toString();
	}

	private String finASM() {
		StringBuilder mensaje = new StringBuilder();
		return mensaje.toString();
	}

	private String parteDelArchEntradaASM() {
		StringBuilder mensaje = new StringBuilder();
		return mensaje.toString();
	}

	private String encabezadoASM() {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(Constants.encabezado);
		mensaje.append(Constants.writeSTR);
		return mensaje.toString();
	}

	private void borrarASM(String file) {
		File archivo = new File(file);
		if (archivo.exists()) {
			if (archivo.getName().endsWith(".asm")) {
				if (archivo.delete()) {
					System.out.println("Se borro el archivo " + archivo
							+ " exitosamente.");
				}
			}
		}
	}
}
