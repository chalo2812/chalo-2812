package ar.edu.caece.pl.main;

import java.io.File;

public class CompileAll extends Compiler{

	public static void main(String[] args) {
		boolean debugMode = true;
		
		File filePath1 = new File("tests/");
		File[] files1 = filePath1.listFiles();
		compileFiles(files1, debugMode);
		
		File filePath2 = new File("tests/functional/");
		File[] files2 = filePath2.listFiles();
		compileFiles(files2, debugMode);
	}

	private static void compileFiles(File[] files, boolean debugMode) {
		if (files != null) {
        	for (int i = 0; i < files.length; i++) {
        		try{
        			if (files[i].getName().endsWith("Test")){
                        Compiler compiler = new Compiler();
        				compiler.setFile(files[i]);
        				compiler.compile(debugMode);
            		}
        		}catch (Exception e){
        			System.err.println("Error al compilar: " + files[i] + ". "+ e.toString());
        		}
            }
        }
		
	}
	
}
