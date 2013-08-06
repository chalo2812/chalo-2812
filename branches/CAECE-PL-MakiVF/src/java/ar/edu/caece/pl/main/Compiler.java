/**
 * 
 */
package ar.edu.caece.pl.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ar.edu.caece.pl.asem.codegenerator.CodeGenerator;
import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;
import ar.edu.caece.pl.asem.model.tree.Tree;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.TokenStream;

public class Compiler {

	private File file;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			swingInput();
		} else {
			consoleInput(args);
		}
	}

	private static void consoleInput(String[] args) {
		File file = new File(args[0]);
		try {
			if (!file.isFile()) {
				System.err.println(args[0] + ": no es un archivo");
			} else if (!file.canRead()) {
				System.err.println(args[0] + ": no se puede leer");
			} else {
				Compiler compiler = new Compiler();
				compiler.setFile(file);
				compiler.compile(false);
			}
		} catch (RuntimeException e) {
			System.err.println(args[0] + ": ocurrió un error con el archivo");
		}
	}

	private static void swingInput() {
		final JFrame frame = new JFrame("Compilador");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JButton btnFile = new JButton("Abrir Codigo Fuente");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					Compiler compiler = new Compiler();
					compiler.setFile(fc.getSelectedFile());
					compiler.compile(false);
				} else {
					System.out.println("Se cancelo la accion por el usuario");
				}
				System.out.println(returnVal);
			}
		});

		frame.getContentPane().add(btnFile);
		frame.getContentPane().add(btnFile);
		
		frame.setSize(300, 100);
		frame.setVisible(true);
	}

	protected void compile(boolean debugMode) {
		System.out.println("Compiling: " + this.file.getName() + "...");
		try {
			InputStream source = new FileInputStream(file.getAbsolutePath());
			ITokenStream tokenStream = new TokenStream(source);

			// Comienza la ejecucion del analizador sintaxis
			AnalizadorSintactico asin = new AnalizadorSintactico(tokenStream,debugMode);
			boolean codigoValido = asin.reconocer();

			// Arbol generado
			if (codigoValido) {
				Tree<AbstractElement> semanticTree = new Tree<AbstractElement>();
				semanticTree.setRoot(asin.getTreeNode());
				System.err.println(semanticTree.toString());
				
				System.out.println(
				"__--== Comienza la generacion del codigo ==--__");
				
				CodeGenerator codeGen = CodeGenerator.getInstance();
				codeGen.setTree(semanticTree);
				String code = codeGen.generateCode();

				writeAsmFile(code);

			} else {
				prettyPrint(asin.getErrorManager().getErrors());
			}

		} catch (FileNotFoundException e1) {
			System.out.println("No se encontro el codigo fuente");
		}
		System.out.println("DONE!");
	}

	private void writeAsmFile(String code) throws FileNotFoundException {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file.getAbsolutePath() + ".asm","UTF-8");
			writer.println(code);
			writer.close();
		} catch (UnsupportedEncodingException e1) {
			System.err.println("Error generando el archivo asm");
			e1.printStackTrace();
		}
	}

	private void prettyPrint(Map<String, String> errors) {
		System.err.println("Errors:");
		System.err.println("=======");
		for(Map.Entry<String, String> error : errors.entrySet()){
			System.err.println("Name: "+ error.getKey());
			System.err.println("Desc: "+ error.getValue());
		}
		
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
