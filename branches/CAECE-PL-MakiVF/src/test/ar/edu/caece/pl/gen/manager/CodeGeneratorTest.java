package ar.edu.caece.pl.gen.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;

import ar.edu.caece.pl.asem.codegenerator.CodeGenerator;
import ar.edu.caece.pl.asem.codegenerator.PreDefinedFunctions;
import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;
import ar.edu.caece.pl.asem.model.tree.Tree;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.TokenStream;

public class CodeGeneratorTest {
	
	private final static String TAB = "\t";
	private final static String ENTER = "\n";
	
	private static final String ASM_TEMPLATE = 
//		"; ASM-Start - Init " + ENTER +
		"ORG 100h " + ENTER +
		"JMP main " + ENTER +
//		"; ASM-Start - Finish \n" + ENTER +
		
		PreDefinedFunctions.sb+

		//		"; GlobalMemory - Init " + ENTER +
		"{GLOBAL_MEMORY}"+ 
//		"; GlobalMemory - Finish \n" + ENTER +
		
//		"; TemporalAssemblerCode - Init " + ENTER +
		"{TEMPORAL}"+
//		"; TemporalAssemblerCode - Finish \n" + ENTER +
		
//		"; CG-Start - Init " + ENTER +
//		"; Child Init - label: PROCEDIMIENTO, envName: null. " + ENTER +
		"{PROCFUNC}"+
//		Agrego un lugar par los procedimientos y funciones
		
		"main proc near" +
	//		"; Child Init - label: PARAMETROS, envName: null. "+
			"{MAIN_PARAMETROS}" + ENTER +
	//		"; Child End - PARAMETROS" + ENTER +
	//		"; Child Init - label: DECLARACIONES, envName: null. " + ENTER +
			"{MAIN_DECLARACIONES}"+
	//		"; Child End - DECLARACIONES" + ENTER +
	//		"; Child Init - label: VOID, envName: null. " + ENTER +
			TAB + "int 21h" + ENTER +
		"main endp" + ENTER;
	
//		"; Child End - VOID" + ENTER +
//		"; Child End - PROCEDIMIENTO" + ENTER +
//		"; CG-Start - Finish \n" + ENTER;
	
	InputStream istream;
	AnalizadorSintactico asin;
	ITokenStream tokenStream;
	
	private Map<String, String> reconocer (String code, String asm){
		return reconocer(code, asm, false);
	}
	
	private Map<String, String> reconocer (String code, String asm, boolean debugMode){
		
		// Reconozco el código LM
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(code.getBytes( "UTF-8" ));
		} catch (UnsupportedEncodingException e) {
			new AssertionError( "No se soporta el encoding: ["+ code +"]" );
		}
		tokenStream = new TokenStream(is);
		asin = new AnalizadorSintactico(tokenStream, debugMode);
		
		// Valido si se reocnoció el código
		assertTrue(asin.reconocer());
		
		// Arbol generado
		Tree<AbstractElement> semanticTree = new Tree<AbstractElement>();
		semanticTree.setRoot(asin.getTreeNode());
		
		// Imprimo el arbol
		System.err.println(semanticTree.toString());
		
		// CodeGenerator
		CodeGenerator codeGen = CodeGenerator.getInstance();
		codeGen.setTree(semanticTree);
		String gen = codeGen.generateCode();
		
		assertEquals(asm.trim(), gen.trim());
		
		return asin.getErrorManager().getErrors();
	}
	
	/////////////////////////////////
	// Auxiliar Methods to Generate ASM Code
	/////////////////////////////////
	/**
	 * Metodo auxiliar, para asignar un valor a una variable
	 * 
	 *  assignValueToVar(String var, int value)
	 * 	{ mov ax, value
	 * 	  mov var, ax
	 *  }
	 * 
	 * @param var
	 * @param value
	 * @return
	 */
	private String assignValueToVar(String var, int value) {
		StringBuffer sb = new StringBuffer();

		sb.append(TAB + "mov ax, " + value + ENTER );
		sb.append(TAB + "mov " + var + ", ax" + ENTER );
	
		return sb.toString();
	}
	
	
	
	/////////////////////////////////
	// BaseLine Test
	/////////////////////////////////
	@Test
	public void baseTest() {
				
		String code =
			" procedure main(); "  +
			" begin " +
			" end-proc;";
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "")
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void variablesGlobalesFullTest() {
		String code =
				" const M : natural = 7n, R : integer = 90;"+
				" var N, S : integer;"+
				" var A[12] : natural;"+
				
				" procedure MAIN(); "  +
					" begin " +
				" end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", 
							"GLOBAL_a dw 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0" + ENTER +
							"GLOBAL_n dw 0" + ENTER + 
							"GLOBAL_s dw 0" + ENTER +
							"GLOBAL_m dw 7" + ENTER +
							"GLOBAL_r dw 90" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"")
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void procsFuncsTest() {
		String code =
				" procedure PROC1(); " +
						" var A : integer;" +
						" var B : natural;"+
				" begin " +
				" end-proc; " +
				" procedure PROC2(byref Y : integer);" +
					" const T : natural = 67n;" +
					" var W11 : integer;" +
					" var W12, Q, R : natural;" +
				" begin " +
				" end-proc; " +
				" function FUNC1(T: integer, byval N2 : integer) : integer;" +
					" var N: integer;" +
				" begin " +
				" end-func 2;" +
				" procedure MAIN(); "  +
					" begin " +
				" end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "")
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",
					"proc1 proc near" + ENTER + 
					TAB + "push bp" + ENTER +
					TAB + "mov bp, sp" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE a" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE b" + ENTER +
					TAB + "sub sp, 4 ;TOTAL" + ENTER +
					TAB + "add sp, 4" + ENTER +
					TAB + "pop bp" + ENTER +
					TAB + "ret " + ENTER +
					"proc1 endp" + ENTER +
					"proc2 proc near" + ENTER +
					TAB + "push bp" + ENTER +
					TAB + "mov bp, sp" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE w11" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE w12" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE q" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE r" + ENTER +
					TAB + "sub sp, 8 ;TOTAL" + ENTER +
					TAB + "add sp, 8" + ENTER +
					TAB + "pop bp" + ENTER +
					TAB + "add sp, 2" + ENTER +
					TAB + "ret " + ENTER +
					"proc2 endp" + ENTER +
					"func1 proc near" + ENTER +
					TAB + "push bp" + ENTER +
					TAB + "mov bp, sp" + ENTER +
					TAB + ";sub sp, 2 ;VARIABLE n" + ENTER +
					TAB + "sub sp, 2 ;TOTAL" + ENTER +
					TAB + "add sp, 2" + ENTER +
					TAB + "pop bp" + ENTER +
					TAB + "add sp, 4" + ENTER +
					TAB + "ret " + ENTER +
					"func1 endp" + ENTER )
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void asignacionTest() {
		
		String code =
				" procedure main(); "  +
						" const n : integer = 67;" +
						" var a : integer;" +
						" begin " +
						" a := n;" +
						" end-proc;";
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "")
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void asignacionYSumaTest() {
		
		String code =
				" procedure main(); "  +
						" const n : integer = 67;" +
						" var a : integer;" +
						" begin " +
						" a := n + n;" +
						" end-proc;";
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "")
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void baseTest2() {
				
		String code =
			" procedure main(); "  +
			" begin " +
			" showLn 'hi';" +
			" end-proc;";
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "")
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	/////////////////////////////////
	// Vars Tests
	/////////////////////////////////
	@Test
	public void variablesGlobalesTest() {
		String code =
				" var A, B: integer;" +
				" var C, D: natural;" +
				" var E: natural;" +
				" var F: integer;" +
				
				" procedure MAIN(); "  +
					" begin " +
				" end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0" + ENTER + 
											"GLOBAL_c dw 0" + ENTER + 
											"GLOBAL_d dw 0" + ENTER + 
											"GLOBAL_e dw 0" + ENTER + 
											"GLOBAL_f dw 0" + ENTER 
											)
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"")
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void asignarVariablesGlobalesTest() {
		String code =
				" var A, B: integer;" +
				" var C, D: natural;" +
				" var E: natural;" +
				" var F: integer;" +
				
				" procedure MAIN(); "  +
					" begin " +
					" a := 1;" +
					" b := 2;" +
					" c := 3n;" +
					" d := 4n;" +
					" e := 5n;" +
					" f := 6;" +
				" end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0" + ENTER + 
											"GLOBAL_c dw 0" + ENTER + 
											"GLOBAL_d dw 0" + ENTER + 
											"GLOBAL_e dw 0" + ENTER + 
											"GLOBAL_f dw 0" + ENTER 
											)
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"")
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", 	assignValueToVar("GLOBAL_a", 1) + 
													assignValueToVar("GLOBAL_b", 2) +
													assignValueToVar("GLOBAL_c", 3) +
													assignValueToVar("GLOBAL_d", 4) +
													assignValueToVar("GLOBAL_e", 5) +
													assignValueToVar("GLOBAL_f", 6)
						
						);
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void pisarVariablesGlobalesTest() {
		String code =
				" var A, B: integer;" +
				" var C, D: natural;" +
				" var E: natural;" +
				" var F: integer;" +
				" procedure MAIN(); "  +
					" var A, B: integer;" +
					" var C, D: natural;" +
					" var E: natural;" +
					" var F: integer;" +
					
					" begin " +
					" a := 1;" +
					" b := 2;" +
					" c := 3n;" +
					" d := 4n;" +
					" e := 5n;" +
					" f := 6;" +
				" end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0" + ENTER + 
											"GLOBAL_c dw 0" + ENTER + 
											"GLOBAL_d dw 0" + ENTER + 
											"GLOBAL_e dw 0" + ENTER + 
											"GLOBAL_f dw 0" + ENTER 
											)
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"")
				.replace("{MAIN_PARAMETROS}", "")
				/* OJO con DW que no es recursivo: 
				〚label〛 DW expression 〚, expression ...〛
				The DW statement initializes memory with one or more word (2-byte) values. 
				label is a symbol that is assigned the current memory address. 
				expression is a word value that is stored in memory.*/
				.replace("{MAIN_DECLARACIONES}", 	TAB + "main_a dw 0" + ENTER +
													TAB + "main_b dw 0" + ENTER +
													TAB + "main_c dw 0" + ENTER +
													TAB + "main_d dw 0" + ENTER +
													TAB + "main_e dw 0" + ENTER +
													TAB + "main_f dw 0" + ENTER +
													assignValueToVar("main_a", 1) + 
													assignValueToVar("main_b", 2) +
													assignValueToVar("main_c", 3) +
													assignValueToVar("main_d", 4) +
													assignValueToVar("main_e", 5) +
													assignValueToVar("main_f", 6)
						
						);
		
		this.reconocer(code, asm);
	}

	@Test
	public void varGlobalesYProcedimientoTest() {
				
		String code =
				" var A, B: integer;" +
				" var C, D: natural;" +
				" var E: natural;" +
				" var F: integer;" +
					
				" procedure MAIN(); "  +
					" var A, B: integer;" +
					" var C, D: natural;" +
					" var E: natural;" +
					" var F: integer;" +
					" begin " +
				" end-proc; " ;

		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0" + ENTER + 
											"GLOBAL_c dw 0" + ENTER + 
											"GLOBAL_d dw 0" + ENTER + 
											"GLOBAL_e dw 0" + ENTER + 
											"GLOBAL_f dw 0" + ENTER 
											)
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"")
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", TAB + "main_a dw 0" + ENTER +
											TAB + "main_b dw 0" + ENTER + 
											TAB + "main_c dw 0" + ENTER + 
											TAB + "main_d dw 0" + ENTER + 
											TAB + "main_e dw 0" + ENTER + 
											TAB + "main_f dw 0" + ENTER 
											);
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void varGlobalesYDosProcedimientosTest() {
				
		String code =
				" var A, B: integer;" +
					
				" procedure proc1(); "  +
					" var A, B: integer;" +
					" begin " +
				" end-proc; " +
				
				" procedure MAIN(); "  +
					" var A, B: integer;" +
					" begin " +
				" end-proc; " ;

		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0" + ENTER 
											)
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"proc1 proc near" + ENTER + 
											TAB + "proc1_a dw 0" + ENTER +
											TAB + "proc1_b dw 0" + ENTER +
											TAB + "ret" + ENTER +
										"main2 endp" + ENTER)
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", 	TAB + "main_a dw 0" + ENTER +
													TAB + "main_b dw 0" + ENTER 
													);
		
		this.reconocer(code, asm);
	}

	@Test
	public void funcionSinParametrosTest() {
				
		String code =
				" function FIBONACCI() : integer;" +
					" var A, B: integer;" +
					" var C, D: natural;" +
					" var E: natural;" +
					" var F: integer;" +
					" begin " +
				" end-func A;" +
					
				" procedure MAIN(); "  +
					" begin " +
				" end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "")
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"fibonacci proc near" + ENTER + 
										TAB + "fibonacci_a dw 0" + ENTER +
										TAB + "fibonacci_b dw 0" + ENTER +
										TAB + "fibonacci_c dw 0" + ENTER +
										TAB + "fibonacci_d dw 0" + ENTER +
										TAB + "fibonacci_e dw 0" + ENTER +
										TAB + "fibonacci_f dw 0" + ENTER +
										
										TAB + "ret" + ENTER +
										"fibonacci endp" + ENTER)
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", "");
		
		this.reconocer(code, asm);
	}

	@Test
	public void functionConUnParametroTest() {
				
		String code =
				" var A, B: integer;" +
				" var C, D: natural;" +
				" var E: natural;" +
				" var F: integer;" +
				
				" function FIBONACCI( n: natural) : integer;" +
					" var A, B: integer;" +
					" var C, D: natural;" +
					" var E: natural;" +
					" var F: integer;" +
					" begin " +
				" end-func N;" +
					
				" procedure MAIN(); "  +
					" var A, B: integer;" +
					" var C, D: natural;" +
					" var E: natural;" +
					" var F: integer;" +
					" begin " +
				" end-proc; " ;

		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_x dw 0" + ENTER +
											"GLOBAL_y dw 0" + ENTER + 
											"tmp_0 dw ?" + ENTER)
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}",	"fibonacci proc near" + ENTER + 
										TAB + "fibonacci_a dw 0" + ENTER +
										TAB + "fibonacci_b dw 0" + ENTER +
										TAB + "fibonacci_c dw 0" + ENTER +
										TAB + "fibonacci_d dw 0" + ENTER +
//										TAB + "mov ax, main_n" + ENTER + 
//										TAB + "mov tmp_0,ax" + ENTER +
//										TAB + "mov ret_null_fibonacci, ax" + ENTER + 
										TAB + "ret" + ENTER +
										"fibonacci endp" + ENTER)
				.replace("{MAIN_PARAMETROS}", "") 	
				.replace("{MAIN_DECLARACIONES}", TAB + "main_x dw 0" + ENTER +
											TAB + "main_y dw 0" + ENTER);
		
		this.reconocer(code, asm);
	}
	
	
	/////////////////////////////////
	// Constants Tests
	/////////////////////////////////
	@Test
	public void constantesGlobalesTest() {
				
		String code =
				"const A: integer = 1; "  +
				"const B: integer = 2, C: integer = 3; "  +
				"const D: natural = 4n; " +
				"const E: natural = 5n, F: natural = 6n; " +
				
				"procedure MAIN(); "  +
					"begin " +
				"end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 1" + ENTER +
											"GLOBAL_b dw 2" + ENTER +
											"GLOBAL_c dw 3" + ENTER +
											"GLOBAL_d dw 4" + ENTER +
											"GLOBAL_e dw 5" + ENTER +
											"GLOBAL_f dw 6" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "" );
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void pisarConstantesGlobalesTest() {
				
		String code =
				"const A: integer = 1; "  +
				"const B: integer = 2, C: integer = 3; "  +
				"const D: natural = 4n; " +
				"const E: natural = 5n, F: natural = 6n; " +
				
				"procedure MAIN(); "  +
					"const A: integer = 1; "  +
					"const B: integer = 2, C: integer = 3; "  +
					"const D: natural = 4n; " +
					"const E: natural = 5n, F: natural = 6n; " +
					"begin " +
				"end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 1" + ENTER +
											"GLOBAL_b dw 2" + ENTER +
											"GLOBAL_c dw 3" + ENTER +
											"GLOBAL_d dw 4" + ENTER +
											"GLOBAL_e dw 5" + ENTER +
											"GLOBAL_f dw 6" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", 	TAB + "main_a dw 1" + ENTER +
													TAB + "main_b dw 2" + ENTER +
													TAB + "main_c dw 3" + ENTER +
													TAB + "main_d dw 4" + ENTER +
													TAB + "main_e dw 5" + ENTER +
													TAB + "main_f dw 6" + ENTER );
		
		this.reconocer(code, asm);
	}

	@Test
	public void pisarConstantesConVariablesTest() {
				
		String code =
				"const A: integer = 1; "  +
				"procedure MAIN(); "  +
					"var A: integer; "  +
				"begin " +
					" a := 1;" +
				"end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 1" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", 	TAB + "main_a dw 1" + ENTER);
		
		this.reconocer(code, asm);
	}
	
	/////////////////////////////////
	// Array Tests
	/////////////////////////////////
	@Test
	public void arreglosGlobalesTest() {
				
		String code =
				"var A[1]: integer; "  +
				"var B[2]: natural; "  +
				"var C[3],D[3]: integer; "  +
				"var E[2],F[2]: natural; "  +
						
				"procedure MAIN(); "  +
					"begin " +
				"end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0, 0" + ENTER +
											"GLOBAL_d dw 0, 0, 0" + ENTER +
											"GLOBAL_c dw 0, 0, 0" + ENTER +
											"GLOBAL_f dw 0, 0" + ENTER +
											"GLOBAL_e dw 0, 0" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "" );
		
		this.reconocer(code, asm);
	}
	
	@Test
	public void asignacionDeArreglosGlobalesTest() {
				
		String code =
				"var A[1]: integer; "  +
				"var B[2]: natural; "  +
				"var C[3],D[3]: integer; "  +
				"var E[2],F[2]: natural; "  +
						
				"procedure MAIN(); "  +
					" begin " +
					" a[1] := 1;" +
					" b[1] := 11;" +
					" b[2] := 12;" +
					" c[1] := 21;" +
					" c[2] := 22;" +
					" c[3] := 23;" +
					" d[1] := 31;" +
					" d[2] := 32;" +
					" d[3] := 33;" +
					" e[1] := 41;" +
					" e[2] := 42;" +
					" f[1] := 51;" +
					" f[2] := 52;" +
					
				"end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0, 0" + ENTER +
											"GLOBAL_d dw 0, 0, 0" + ENTER +
											"GLOBAL_c dw 0, 0, 0" + ENTER +
											"GLOBAL_f dw 0, 0" + ENTER +
											"GLOBAL_e dw 0, 0" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", "" );
		
		this.reconocer(code, asm);
	}

	@Test
	public void pisarArreglosGlobalesTest() {
				
		String code =
				"var A[1]: integer; "  +
				"var B[2]: natural; "  +
				"var C[3],D[3]: integer; "  +
				"var E[2],F[2]: natural; "  +
						
				"procedure MAIN(); "  +
					"var A[1]: integer; "  +
					"var B[2]: natural; "  +
					"var C[3],D[3]: integer; "  +
					"var E[2],F[2]: natural; "  +
					"begin " +
				"end-proc; " ;
		
		String asm = ASM_TEMPLATE
				.replace("{GLOBAL_MEMORY}", "GLOBAL_a dw 0" + ENTER +
											"GLOBAL_b dw 0, 0" + ENTER +
											"GLOBAL_d dw 0, 0, 0" + ENTER +
											"GLOBAL_c dw 0, 0, 0" + ENTER +
											"GLOBAL_f dw 0, 0" + ENTER +
											"GLOBAL_e dw 0, 0" + ENTER )
				.replace("{TEMPORAL}", "")
				.replace("{PROCFUNC}","")
				.replace("{MAIN_PARAMETROS}", "")
				.replace("{MAIN_DECLARACIONES}", 	TAB + "main_a dw 0" + ENTER +
													TAB + "main_b dw 0, 0" + ENTER +
													TAB + "main_d dw 0, 0, 0" + ENTER +
													TAB + "main_c dw 0, 0, 0" + ENTER +
													TAB + "main_f dw 0, 0" + ENTER +
													TAB + "main_e dw 0, 0" + ENTER );
		
		this.reconocer(code, asm);
	}
	
		
	///////////////////////////////////////////
	// Llamadas a procedimientos o funciones
	///////////////////////////////////////////
	@Test
	public void llamarProcedimientoSinParametrosTest() {
	
	String code =
			
			"procedure proc1(); "  +
				"begin " +
			"end-proc; " +
			"procedure MAIN(); " +  
				"begin " +
				"proc1();" +
			"end-proc; " ;
	
	String asm = ASM_TEMPLATE
	.replace("{GLOBAL_MEMORY}", "" )
	.replace("{TEMPORAL}", "")
	.replace("{PROCFUNC}",	"proc1 proc near" + ENTER +
							TAB + "ret" + ENTER +
							"proc1 endp" + ENTER
			)
	.replace("{MAIN_PARAMETROS}", "")
	.replace("{MAIN_DECLARACIONES}", 	TAB + "CALL proc1" + ENTER
			);
	
	this.reconocer(code, asm);
	}
	
	@Test
	public void llamarProcedimientoConUnParametroTest() {
	
	String code =
			"procedure proc1(n: integer); "  +
					"begin " +
				"end-proc; " +
			"procedure MAIN(); " +  
				"var a: integer;" +
				"begin " +
				"proc1(5);" +
			"end-proc; " ;
	
	String asm = ASM_TEMPLATE
	.replace("{GLOBAL_MEMORY}", "" )
	.replace("{TEMPORAL}", "")
	.replace("{PROCFUNC}",	"proc1 proc near" + ENTER +
							TAB + "ret" + ENTER +
							"proc1 endp" + ENTER
			)
	.replace("{MAIN_PARAMETROS}", "")
	.replace("{MAIN_DECLARACIONES}", 	TAB + "CALL proc1" + ENTER
			);
	
	this.reconocer(code, asm);
	}

	@Test
	public void llamarProcedimientoYFuncionConUnParametroTest() {
	
	String code =
			"procedure proc1(n: integer); "  +
					"begin " +
				"end-proc; " +
				
			"function func1(n: integer): integer; "  +
				"begin " +
			"end-func 2; " +
			
			"procedure MAIN(); " +  
				"var a: integer;" +
				"begin " +
				"proc1(5);" +
				"a = func1(5);" +
			"end-proc; " ;
	
	String asm = ASM_TEMPLATE
	.replace("{GLOBAL_MEMORY}", "" )
	.replace("{TEMPORAL}", "")
	.replace("{PROCFUNC}",	
			"proc1 proc near" + ENTER +
			TAB + "ret" + ENTER +
			"proc1 endp" + ENTER
			)
	.replace("{MAIN_PARAMETROS}", "")
	.replace("{MAIN_DECLARACIONES}",
			TAB + "main_a dw 0" + ENTER +
			TAB + "CALL proc1" + ENTER +
			TAB + "CALL proc2" + ENTER
			);
	
	this.reconocer(code, asm, true);
	}
	
}
