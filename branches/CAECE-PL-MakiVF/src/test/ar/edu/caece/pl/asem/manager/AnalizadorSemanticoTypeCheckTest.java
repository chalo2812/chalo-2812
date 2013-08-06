package ar.edu.caece.pl.asem.manager;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.TokenStream;

public class AnalizadorSemanticoTypeCheckTest {
	
	InputStream istream;
	IAnalizadorSintactico asin;
	ITokenStream tokenStream;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	
	private Map<String, String> recognize(String code) {
		return recognize(code, false);
	}
	
	private Map<String, String> recognize(String code, boolean validate) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(code.getBytes( "UTF-8" ));
		} catch (UnsupportedEncodingException e) {
			new AssertionError( "No se soporta el encoding: ["+ code +"]" );
		}
		
		tokenStream = new TokenStream(is);
		asin = new AnalizadorSintactico(tokenStream,true);
		
		assertTrue(asin.reconocer());
		if (validate){
			assertTrue(asin.validado());
		}
		
		return asin.getErrorManager().getErrors();
	}	

	
	@Test
	public void testDeEjemploMain0() throws UnsupportedEncodingException{

		String code = 	"const M : natural = 7n; "  +
						"var N : integer ; " +
						"var A[12] : natural ; "  +
						
						"procedure MAIN(byref R : natural); "  +
							" begin " +
						"end-proc;";
					
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	@Test
	public void testDeEjemploMain1() throws UnsupportedEncodingException{

		String code = 	" const M : natural = 7n ; "  +
						" var N : integer ; " +
						" var A[12] : natural ; "  +
						
						" procedure MAIN(byref R : natural); "  +
						" begin " +
							" R := R ** A[12]; " +
						" end-proc;";
					
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Asigno un integer a un natural, debería dar error.
	@Test
	public void test01() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ;"  + 						
						"procedure MAIN(); "  +
						
						"begin " +
							"S := 7; "  +  
						"end-proc;";
							
		Map<String, String> mapErrors = recognize(code);
				
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002"));
		assertTrue(mapErrors.size() == 1);		
	}
	
	// Asigno un natural a S que es natural, debería aceptarlo.
	@Test
	public void test02() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ;"  + 						
						"procedure MAIN(); "  +
						"begin " +
						"S := 7n; "  +  
						"end-proc;";
							
		Map<String, String> mapErrors = recognize(code, true);
		
		assertTrue(mapErrors.size() == 0);		
	}
			
	// Validacion de suma de naturales por verdadero
	@Test
	public void test04() throws UnsupportedEncodingException{

		String code = 	"var S:	natural; "  + 	
						"var M:	natural; "  + 
						"procedure MAIN(); "  +
						"begin " +
						"S := 1n; " +
						"M := 1n; " +
						
						"S := (S ++ M); "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Validacion de suma de naturales por falso
	@Test
	public void test05() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var M:	integer ; "  + 
						
						"procedure MAIN(); "  +
						"begin " +
							"S := (S ++ M); "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
			
		assertTrue(mapErrors.containsKey("ASEM-EXPRESION-1-TYPE_CHECK_001")); // Error en la suma
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002")); // Error en la asignacion
		assertTrue(mapErrors.size() == 2); // Por la suma y por la asignacion.		
	}
	
	// Validacion de suma de naturales por falso
	@Test
	public void test06() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var M:	natural ; "  + 
						"procedure MAIN(); "  +
						"begin " +
							"S := (S ++ M) ** 9; "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
			
		assertTrue(mapErrors.containsKey("ASEM-TERMINO-1-TYPE_CHECK_001")); // La multiplicacion **
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002")); // La asignacion que no pudo ser
		assertTrue(mapErrors.size() == 2);
	}
	
	// Validacion de suma de enteros, debería salir por falso
	@Test
	public void test08() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 	
						"var M:	integer ; "  + 
						"procedure MAIN(); "  +
						"begin " +
						"S := (S + 2n) ** M; "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
			
		assertTrue(mapErrors.containsKey("ASEM-EXPRESION-1-TYPE_CHECK_001")); // La suma de S + 2n
		assertTrue(mapErrors.containsKey("ASEM-TERMINO-1-TYPE_CHECK_001")); // La mult ** de algo que no se reconoció
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002")); // La asignacion de algo que no se reconoció.
		assertTrue(mapErrors.size() == 3);
	}
	
	// Validacion del to_natural, debería salir por verdadero
	@Test
	public void test09() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 	
						"var M:	integer ; "  + 
						"procedure MAIN(); "  +
						"begin " +
							"S := TOINTEGER((S + M)*9); "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Validacion del to_natural, debería salir por falso
	@Test
	public void test10() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var M:	natural ; "  + 
						
						"procedure MAIN(); "  +
						"begin " +
							"S := TONATURAL((S ++ M) ** 9n); "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Validacion del to_integer, debería salir por verdadero
	@Test
	public void test11() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 	
						"var M:	integer ; "  + 
						
						"procedure MAIN(); "  +
						"begin " +
							"S := TOINTEGER((S + M) * 9); "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Validacion del to_integer, debería salir por falso
	@Test
	public void test12() throws UnsupportedEncodingException{

		String code = 	"var S:	natural  ; "  + 	
						"var M:	natural  ; "  + 
						
						"procedure MAIN(); "  +
						"begin " +
							"S := TOINTEGER((S ++ M) ** 9n); "  +  
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
			
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002")); // El TOINTEGER Falla
		assertTrue(mapErrors.size() == 1);		
	}
	
	// Validacion de condicion if, debería salir por falso porque comparo un integer con un natural
	@Test
	public void test13() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var M:	integer ; "  + 
						
						"procedure MAIN(); "  +
						"begin " +
							"if S < M then " +
								"S := 2n; "  +
							"end-if;" +
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
			
		assertTrue(mapErrors.containsKey("ASEM-CONDICION-3-TYPE_CHECK_003"));
		assertTrue(mapErrors.size() == 1);		
	}
	
	// Validacion de condicion if, debería salir por verdadero comparando NATURALs
	@Test
	public void test15() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 	
						"var M:	integer ; "  + 
						
						"procedure MAIN();"  +
						"begin " +
							"if S = M then " +
								"S := 2;"  +
							"end-if;" +
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	@Test
	// Validacion de condicion if, debería salir por verdadero comparando NATURALs
	public void test16() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var M:	natural ; "  + 
						
						"procedure MAIN();"  +
						"begin " +
							"if S < M then " +
								"S := 2n;"  +
							"end-if;" +
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Validacion de while, debería salir por falso
	@Test
	public void test17() throws UnsupportedEncodingException{

	String code = 	"var S:	integer ; "  + 	
					"var M:	natural ; "  + 
					
					"procedure MAIN();"  +
					"begin " +
						"while S < M do " +
							"M := 2n; "  +
						"end-while; " +
					"end-proc;";
							
	Map<String, String> mapErrors = recognize(code);
		
	assertTrue(mapErrors.containsKey("ASEM-CONDICION-3-TYPE_CHECK_003"));
	assertTrue(mapErrors.size() == 1);		
	}
	
	
	// Validacion de while, debería salir por verdadero
	@Test
	public void test18() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var M:	natural ; "  + 
						
						"procedure MAIN();"  +
						"begin " +
							"while S < M do " +
								"S := 2n;"  +
							"end-while;" +
						"end-proc;";
								
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Validacion de if, debería salir por verdadero
	@Test
	public void test19() throws UnsupportedEncodingException{

	String code = 	"var S:	integer ; "  + 	
					"var M:	integer ; "  + 
					
					"procedure MAIN();"  +
					"begin " +
						"if S < M then " +
						"S := 2; "  +
						"end-if;" +
					"end-proc;";
							
	Map<String, String> mapErrors = recognize(code);
	
	assertTrue(mapErrors.size() == 0);		
	}
	
	//Factor1 -- Reconozco llamado a función por verdadero.
	@Test
	public void test20() throws UnsupportedEncodingException{

		String code = 	"var N, S : integer; "  +						
						 
						"function FUN1(T: integer, byval N2 : integer) : integer; "  +
						"begin "  +
							"N := 45; "  +
						"end-func N * 2; "  +
						
						"procedure MAIN(); "  +
						"begin " +
							"S := FUN1(7, 8); "  +  
						"end-proc; ";
					
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}


	//Llamado a la función FUN1 correcta, debería aceptarlo.
	@Test
	public void test21() throws UnsupportedEncodingException{

		String code = 	 "function FUN1(T: integer) : integer; "  +
						 "begin "  +
							 "T := T + 1; "  +
						 "end-func T * 2; "  +
						
						 "procedure MAIN(); "  +
						 "begin " +
							 "S := FUN1(2); "  +  //Estoy llamando a FUN1 pasandole por parámetro un string en lugar de un integer
						 "end-proc; ";
					
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_003")); // s en MAIN() no está declarado.
		assertTrue(mapErrors.size() == 1);
	}
	
	// Llamo a la función FUN1 pasandole por parámetro un INTEGER.
	@Test
	public void test22() throws UnsupportedEncodingException{

		String code = 	"var S:	 integer; "  + 						
						
						"function FUN1(T: integer) : integer; "  +
						"begin "  +
							"T := 45; "  +
						"end-func T * 2; "  +
								
						"procedure MAIN(); "  +
						"begin " +
							"S := FUN1(S); "  +  //Estoy llamando a FUN1 pasandole por parámetro un integer
						"end-proc;";
							
		Map<String, String> mapErrors = recognize(code);
				
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Llamada a procedimiento pasandole por parámetro un INTEGER.
	@Test
	public void test23() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 						
						
						"Procedure PROC1(T: integer); "  +
						"begin "  +
							"T := 45; "  +
						"end-proc; "  +
								
						"procedure MAIN(); "  +
						"begin " +
							"PROC1(S); "  +
						"end-proc;";
							
		Map<String, String> mapErrors = recognize(code);
				
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Llamada a procedimiento pasandole por parámetro una operación con error de tipos.
	@Test
	public void test24() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var T:	natural ; "  + 
						
						"Procedure PROC1(R: integer) ; "  +
						"begin "  +
							"R := 45; "  +
						"end-proc; "  +
								
						"procedure MAIN(); "  +
						"begin " +
							"PROC1(S ++ T); "  +
						"end-proc;";
							
		Map<String, String> mapErrors = recognize(code);
				
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-3-TYPE_CHECK_001"));
		assertTrue(mapErrors.size() == 1);		
	}
	
	// Llamada a procedimiento pasandole por parámetro una operación correctamente
	@Test
	public void test25() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var T:	natural ; "  + 
						
						"Procedure PROC1(R: natural); "  +
						"begin "  +
							"R := 45n; "  +
						"end-proc; "  +
								
						"procedure MAIN(); "  +
						"begin " +
							"PROC1(S ++ T); "  +
						"end-proc;";
							
		Map<String, String> mapErrors = recognize(code);
				
		assertTrue(mapErrors.size() == 0);		
	}
	
	// Llamada a procedimiento pasandole por parámetro una operación correctamente
	@Test
	public void test26() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 	
						"var T:	integer ; "  + 
						
						"Procedure PROC1(R: natural); "  +
						"begin "  +
							"R := 45n; "  +
						"end-proc; "  +
								
						"procedure MAIN(); "  +
						"begin " +
							"PROC1(TONATURAL(S + T)); "  +
						"end-proc;";
							
		
		Map<String, String> mapErrors = recognize(code);

		assertTrue(mapErrors.size() == 0);		
	}
	
	// Error en la asignación de tipo en T
	@Test
	public void test27() throws UnsupportedEncodingException{

		String code = 	"var S:	integer ; "  + 	
						"var T:	integer ; "  + 
						
						"procedure MAIN(); " +
						"begin " +
							"T := S -- 2n; "  +
						"end-proc; " ;
				
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.containsKey("ASEM-EXPRESION-1-TYPE_CHECK_001"));
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002"));
		assertTrue(mapErrors.size() == 2);	
	}
	
	// Error en la asignación de tipo en T
	@Test
	public void test29() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var T:	integer ; "  + 
						
						"procedure MAIN();" +
						"var R: natural ; "  +
						"begin "  +
							"T := S ** 2n ** R; "  +
						"end-proc; " ;
				
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.containsKey("ASEM-SENTENCIA-1-TYPE_CHECK_002"));
		assertTrue(mapErrors.size() == 1);	
	}
	
	// Error en la asignación de tipo en T
	@Test
	public void test30() throws UnsupportedEncodingException{

		String code = 	"var S:	natural ; "  + 	
						"var T:	natural ; "  + 
						
						"procedure MAIN(); " +
						"var R: natural ; "  +
						"begin "  +
							"R := S ** T; "  +
						"end-proc; " ;
				
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);	
	}
	
	// Error en la asignación de tipo en T
	@Test
	public void test31() throws UnsupportedEncodingException{

		String code = 	"procedure MAIN(); " +
						"var A : integer; " +
						"var B : integer; " +
						"begin B := 2; " +
							"while B > 0 do " +
								"if even(B) then " +
									"A := A + 1; " +
								"end-if; " +
								"B := B - 1 ; " +
							"end-while; " +
						"end-proc; " ;
			
			Map<String, String> mapErrors = recognize(code);
			
			assertTrue(mapErrors.size() == 0);		
	}
	
	@Test
	public void test32() throws UnsupportedEncodingException{

		String code =	"function FUN1(T: integer, byval N2 : integer) : integer; "  +
						"var N: integer; "  +
						"begin " +
							"if T > 0 then "  +
								"N := 45; "  +
							"else " +
								"N := 70; "  +
							"end-if; "  +
						"end-func N * 2; "  +
						
						"procedure MAIN(); "  +
						"var S: integer; "  +
						"var X: integer; "  +
						"begin " +
							"S := 1; "  +
							"X := FUN1(S, 8); "  +
							"show TONATURAL(S), 1n; "  +
						"end-proc; " ;
		
		Map<String, String> mapErrors = recognize(code);
		
		assertTrue(mapErrors.size() == 0);		
	}
}




