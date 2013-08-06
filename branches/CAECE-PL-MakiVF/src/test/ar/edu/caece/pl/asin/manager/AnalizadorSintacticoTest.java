package ar.edu.caece.pl.asin.manager;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.TokenStream;

public class AnalizadorSintacticoTest {
	
	InputStream istream;
	IAnalizadorSintactico asin;
	ITokenStream tokenStream;
	
	@Before
	public void setUp() throws Exception {
		//istream = new FileInputStream( " /home/trodriguez/workspace/svn/trunk/AnalizadorLexico/src/source.lm " );
		//istream = new FileInputStream( " C:\\source.lm " );
	}
	
	@After
	public void tearDown() throws Exception {
		//istream.close();
	}
	
	
	private void recognizeFalse(String code) {
		reconocer(code, false);
	}
	private void recognizeTrue(String code) {
		reconocer(code, true);
	}
	
	private void reconocer(String code, boolean type) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(code.getBytes( "UTF-8" ));
		} catch (UnsupportedEncodingException e) {
			new AssertionError( "No se soporta el encoding: ["+ code +"]" );
		}
		
		tokenStream = new TokenStream(is);
		asin = new AnalizadorSintactico(tokenStream,true);
		boolean recognized = asin.reconocer();
		
		if (type == true){
			assertTrue(recognized);
		}
		else{
			assertTrue(!recognized);
		}
	}
	
	/* 
	 * Test iniciales
	 * 
	 */
	@Test
	public void testEmpty() throws UnsupportedEncodingException{
		String code =  "" ;
		recognizeTrue(code);
	}
	@Test
	public void testError() throws UnsupportedEncodingException{
		String code =  " A " ;
		recognizeFalse(code);
	}
	@Test
	public void testAssignmentErr() throws UnsupportedEncodingException{
		String code =  " B := M; " ;
		recognizeFalse(code);
	}
	
	/* 
	 * Tests CONSTS
	 * 
	 */
	@Test
	public void testOneNaturalConst() throws UnsupportedEncodingException{
		String code =  	" const M : natural = 7n; "+
						" const M : integer = 7; ";
		recognizeTrue(code);
	}
	
	@Test
	public void testTwoConsts() throws UnsupportedEncodingException{
		String code = 	" const M : natural = 7n, R : integer = 90; " +
						" const N : natural = 7n, S : integer = 90; " +
						" const O : integer = 7, P : natural = 90n; " ;
		//Cuidado con repetir las constantes... cuando integremos el ASem va a reventar el test case
		recognizeTrue(code);
	}
	@Test
	public void testThreeConsts() throws UnsupportedEncodingException{
		String code = 	" const M : natural = 7n, R : integer = 90; " +
						" const N : integer = 7, S : natural = 90n; " +
						" const O : natural = 7n; " ;
		recognizeTrue(code);
	}
	@Test
	public void testThreeCommaConsts() throws UnsupportedEncodingException{
		String code =  " const M : natural = 7n, P : natural = 990n, I : natural = 7n; " ;
		recognizeTrue(code);
	}
	
	@Test
	public void testConstMErr() throws UnsupportedEncodingException{
		String code =  " const M, N : natural = 7n, R : integer = 90; " ;
		recognizeFalse(code);
	} 
	
    @Test
	public void testConstNotInitializedErr() throws UnsupportedEncodingException{
		String code =  " const M : natural, P : natural, I : natural; " ;
		recognizeFalse(code);
	}
	
	/* 
	 * Tests VAR
	 * 
	 */
    @Test
	public void test1VarWithoutAssign1() throws UnsupportedEncodingException{
		String code =   " var A : integer; " +
						" var B : natural; " +
						" var C,D,E : integer; " +
						" var F,G,H : natural; " ;
		recognizeTrue(code);
	}
    
    @Test
	public void test1VarWithAssign() throws UnsupportedEncodingException{
		String code =   " var A : integer = 1; " +
						" var B : natural = 2n; " ;
		recognizeFalse(code);
	}
	
	@Test
	public void testVarArray() throws UnsupportedEncodingException{
		String code =  " var A[12] : natural; " ;
		recognizeTrue(code);
	}
	@Test
	public void testTwoVarArrays() throws UnsupportedEncodingException{
		String code =  " var A[80], b[7] : natural; " ;
		recognizeTrue(code);
	}
	@Test
	public void testThreeVars() throws UnsupportedEncodingException{
		String code =  " var N, W, T: integer; " ;
		recognizeTrue(code);
	}
	@Test
	public void testVarArrayErr() throws UnsupportedEncodingException{
		String code =  " var A(90) : natural; " ;
		recognizeFalse(code);
	}
	@Test
	public void testVarErr() throws UnsupportedEncodingException{
		String code =  " var R:: integer; " ;
		recognizeFalse(code);
	}
	@Test
	public void testVarComaErr() throws UnsupportedEncodingException{
		String code =  " var N: integer, var W: natural; " ;
		recognizeFalse(code);
	}
	
	/* 
	 * Test CONST + VAR
	 * 
	 */
	@Test
	public void testConstVar() throws UnsupportedEncodingException{
		String code = 	 " const M : natural = 7n, P : integer = 90; "  +
						 " const N : natural = 7n, Q : integer = 90; "  +
						 " var O: integer; var R: integer; " ;
		recognizeTrue(code);
	}
	
	
	/*
	 * COMIENZAN LOS TEST DE PROCESOS
	 * 
	 */
	@Test
	public void testProcErr() throws UnsupportedEncodingException{
		String code = 	 "  procedure test1();  "  +
						 "  var N : integer;  "  +
						 "  begin  "  +
						 "  N := 0;  "  +
						 "  end;  " ;   //Tiene que ser end-proc
		recognizeFalse(code);
	}
	@Test
	public void testProc() throws UnsupportedEncodingException{
		String code = 	 "  procedure PROC2();  "  +
						 "  var N : integer;  "  +
						 "  begin  "  +
						 "  N := 0;  "  +
						 "  end-proc;  " ;
		recognizeTrue(code);
	}
	
	@Test
	public void testProcByRef() throws UnsupportedEncodingException{
		String code = 	 "  procedure PROC2(byref R : integer);  "  +	//ojo con poner palabras en castellano!!! (entero)
						 "  var N : integer;  "  +
						 "  begin  "  +
						 "  N := 0;  "  +
						 "  end-proc;  " ;
		recognizeTrue(code);
	}
	
	@Test
	public void testProcByVal() throws UnsupportedEncodingException{
		String code = 	 "  procedure PROC2(byval R : integer);  "  +
						 "  var N : integer;  "  +
						 "  begin  "  +
						 "  N := 0;  "  +
						 "  end-proc;  " ;
		recognizeTrue(code);
	}
	
    @Test
	public void testProcTwoVars() throws UnsupportedEncodingException{
		String code = 	 "  procedure PROC1();  "  +
						 "  var A : integer;  "  +
						 "  var B : natural;  "  +
						 "  begin  "  +
							 "  a:=2;  "  +
						 "  end-proc;  " ;
		recognizeTrue(code);
	}	
	@Test
	public void testProc4() throws UnsupportedEncodingException{
		String code = 
				 "  procedure PROC1();  "  +
				 "  var A : integer;  "  +
				 "  var B : natural;  "  +
				 "  begin  "  +
				 "  B := 0;  "  +
				 "  while B > 0 do  "  +
					 "  if even(B) then  "  +
						 " A := A + 1;  "  +
					 " end-if;  "  +	//ojo con fin-si
					 " showLN 'Visualizacion', B, ' ', A;  "  +
					 " B := B - 1;  "  +
				 " end-while;  "  +
				 " end-proc;  " ;
		recognizeTrue(code);
	}
	@Test
	public void testProc5() throws UnsupportedEncodingException{
		String code =  
				"procedure PROC2(byref R : integer); " +
					"const T : natural = 67n; " +
					"var W11 : integer; " +
					"var W12, Q, R : natural; " +
					"var S,M : integer; " +
					"var A[5]: natural; " +
					
					"begin " +
					"S := (S ++ M) ** 2n; " +
					"Q := 1n; " +
					"read W11;  {lectura de teclado} " +
					"W12 := TONATURAL(AW11 * 2 + S); " +
					"while (W12 - 2) <= R + S do " +
						"W12 := W12 * 2; " +
					"end-while; " +
					"while W12 + M > Y / 2 do " +
						"Q := Q * 2; " +
						"W12 := W12 / 2; " +
						"if W12 <= R then " +
							"R := R - W12; " +
							"Q := Q + 1; " +
						"end-if; " +
					"end-while; " +
					"A[1] := Q; " +
				"end-proc; " ;
		recognizeTrue(code);
	}
	@Test /*test1(;*/
	public void testProcErr2() throws UnsupportedEncodingException{
		String code =   " procedure test1(; " +
						" begin var N : integer; " +
						" end-proc; " ;
		recognizeFalse(code);
	}
	@Test /*Error var A : integer, var B : natural;*/
	public void testProcErr3() throws UnsupportedEncodingException{
		String code =  	" procedure PROC2(byref R integer: integer); " +
						" begin " +
						" var N : integer; " +
						" end-proc; " ;
		recognizeFalse(code);
	}
	@Test /*Error var A : integer, var B : natural;*/
	public void testProcErr4() throws UnsupportedEncodingException{
		String code =  	" procedure PROC1(); " +
						" var A : integer, var B : natural; " +
						" begin a:=2; " +
						" end-proc; " ;
		recognizeFalse(code);
	}	
	@Test /*Error A = A + 1;*/
	public void testProcErr5() throws UnsupportedEncodingException{
		String code =  
				"procedure PROC1(); " +
					"var A,B : integer; " +
					
					"begin " +
					"B := 0; " +
					"while B > 0 do " +
						"if even(B) then " +
							"A := A + 1; " +
						"end-if; " +
						"showLN 'Visualizacion', B, ' ', A; " +
						"B := B - 1; " +
					"end-while; " +
				"end-proc; " ;
		recognizeTrue(code);
	}
	@Test /*Error: const : natural = 67n;*/
	public void testProcErr6() throws UnsupportedEncodingException{
		String code =  
				"procedure PROC2(byref R : integer); " +
					"const : natural = 67n; " +
					"var W11 : integer; " +
					"var W12, Q, R : natural; " +
				"begin S := (S ++ M) ** 2n; " +
				"Q := 1n; " +
				"read W11;  {lectura de teclado} " +
				"W12 := TONATURAL(AW11 * 2 + S); " +
				"while (W12 < 2) <= R + S do " +
					"W12 := W12 * 2; " +
				"end-while; " +
				"while W12 + M > Y / 2 do " +
					"Q := Q * 2; " +
					"W12 := W12 / 2; " +
					"if W12 <= R then " +
						"R := R - W12; " +
						"Q := Q + 1; " +
					"end-if; " +
				"end-while; " +
				"A[1] := Q; " +
				"end-proc; " ;
		recognizeFalse(code);
	}
	
	
	/*
	 * COMIENZAN LOS TEST DE FUNCIONES
	 * 
	 */
	@Test 
	public void testFunc() throws UnsupportedEncodingException{
		String code = 	 " function FUN1():integer; "  +
						 " var N: integer; "  +
						 " begin "  +
							 " N := 45; "  +
						 " end-func N; " ;
		recognizeTrue(code);
	}
	
	@Test
	public void testFunc11() throws UnsupportedEncodingException{
		String code = 	 " function FUN1() : integer; "  +
						 " var N: integer; "  +
						 " begin "  +
						 " N := 45; "  +
						 " end-func N; " ;
		recognizeTrue(code);
	}
	@Test
	public void testFunc2() throws UnsupportedEncodingException{
		String code = 	 " function FUN1() : integer; "  +
						 " var N,T: integer; "  +
						 " begin "  +
						 "  if T > 0 then "  +
							 " N := 45; "  +
						 " end-if; "  +
						 " end-func N; " ;
		recognizeTrue(code);
	}
	@Test 
	public void testFunc3() throws UnsupportedEncodingException{
		String code =  
				" function FUN1(T: integer, byval N2 : integer) : integer; " +
				"var N: integer; " +
				"begin " +
				"if T > 0 then " +
				"	N := 45; " +
				"else " +
					"N := 70; " +
				"end-if;" +
				"end-func 9; " ;
		recognizeTrue(code);
	}
	@Test 
	public void testFunc4() throws UnsupportedEncodingException{
		String code =  
				"function FUN1(T: integer, byval N2 : integer) : integer; " +
				"var N: integer; " +
				"begin " +
				"if T > 0 then " +
					"N := 45; " +
				"else " +
					"N := 70; " +
				"end-if;" +
				"end-func N * 2; " ;
		recognizeTrue(code);
	}
	@Test 
	public void testFunc5() throws UnsupportedEncodingException{
		String code =  
				"function FUN1(byref T: integer, byval N2 : integer, Y: integer) : integer; " +
				"var N: integer; " +
				"begin " +
				"if T > 0 then " +
					"N := 45; " +
				"else " +
					"N := 70; " +
				"end-if;" +
				"end-func N * 2; " ;
		recognizeTrue(code);
	}
	@Test 
	public void testFunc6() throws UnsupportedEncodingException{
		String code =  //Falla en T,W mientras no aceptemos <VAR> en BloqueVarParam
				"function FUN1(byref T,W: integer, byval N2 : integer, Y: integer) : integer; " + 
				"var N: integer; " +
				"begin " +
				"if T > 0 then " +
					"N := 45; " +
				"else " +
					"N := 70; " +
				"end-if;" +
				"end-func N * 2; " ;
		recognizeFalse(code);	//Cambiar a true si aceptamos <VAR> en BloqueVarParam
	}
	@Test 
	public void testFunc7() throws UnsupportedEncodingException{
		String code =  
				"function FUN1(byref T: integer, byval N2 : integer, Y: integer) : integer; " +
				"var N,W,R: integer:=9; " +	//1) Para inicializar se usa = y no :=
				"begin " +					//2) Una variable no puede ser inicializada en Header (al menos en este lenguaje)
				"if T > 0 then " +
					"N := 45; " +
				"else " +
					"N := 70; " +
				"end-if;" +
				"end-func N * 2; " ;
		recognizeFalse(code);
	}
	
	@Test /*Error var : integer; */
	public void testFuncErr() throws UnsupportedEncodingException{
		String code =  " function FUN1():integer; var : integer; begin N := 45; end-func N; " ;
		recognizeFalse(code);
	}
	
	@Test /*Error function FUN1();*/
	public void testFuncErr2() throws UnsupportedEncodingException{
		String code =  " function FUN1(); var N: integer; begin if T > 0 then N := 45; end-if;end-func N; " ;
		recognizeFalse(code);
	}
	
	@Test /*Error end-func;*/
	public void testFuncErr3() throws UnsupportedEncodingException{
		String code =  " function FUN1(T:integer, byval N2 : integer) : integer; var N: integer; begin if T > 0 then N := 45; else N := 70; end-if;end-func; " ;
		recognizeFalse(code);
	}
	
	@Test /*FUN1(T: integer, byval,byref N2 : integer)*/
	public void testFuncErr4() throws UnsupportedEncodingException{
		String code =  " function FUN1(T: integer, byval,byref N2 : integer) : integer; var N: integer; begin if T > 0 then N := 45; else N := 70; end-if;end-func N * 2; " ;
		recognizeFalse(code);
	}
	
	@Test /*function ; end-func N * 2;*/
	public void testFuncErr5() throws UnsupportedEncodingException{
		String code =  " function ; end-func N * 2; " ;
		recognizeFalse(code);
	}
	
	@Test /*FUN1(byref T,W: integer, byval N2 : integer, Y: integer) : integer; end-fun*/
	public void testFuncErr6() throws UnsupportedEncodingException{
		String code =  " function FUN1(byref T,W: integer, byval N2 : integer, Y: integer) : integer; end-func N * 2; " ;
		recognizeFalse(code);
	}
	
	@Test /*Error byval N2; M : integer*/
	public void testFuncErr7() throws UnsupportedEncodingException{
		String code =  
				"function FUN1(byref T,W: integer, byval N2; M : integer, Y: integer) : integer; " +
				"var N,W,R: integer:=9; " +
				"begin " +
				"if T > 0 then " +
					"N := 45; " +
				"else " +
				"N := 70; " +
				"end-if;" +
				"end-func N * 2; " ;
		recognizeFalse(code);
	}
	
	
	/*
	 * COMIENZAN LOS TEST COMBINADOS
	 * 
	 */
	@Test
	public void testSentCondWhile() throws UnsupportedEncodingException{
		String code =  	"procedure PROC1();" +
						"var A: integer; " +
						"var B: natural; " +
						"begin" +
							" while B > 0 do " +
							"A:=1;" +
							"end-while; " +
						"end-proc;" ;
		recognizeTrue(code);
	}
	@Test
	public void testSentCondIf() throws UnsupportedEncodingException{
		String code =  	"procedure PROC1(); " +
						"var A,B: integer; " +
						"begin " +
							"if even(B) then " +
								"A:=1; " +
							"end-if; " +
						"end-proc; " ;
		recognizeTrue(code);
	}
	@Test
	public void testShowln() throws UnsupportedEncodingException{
		String code =  	"procedure PROC1(); " +
						"var A,B: integer; " +
						"begin " +
							" showLN 'Visualizacion', B, ' ', A; " +
						"end-proc; ";
		recognizeTrue(code);
	}

	@Test 
	public void testPackage() throws UnsupportedEncodingException{
		String code = 	 " procedure test1(); begin var N : integer; "  +
						 " end-proc; "  +
						
						 " procedure test2(); "  +
						 " begin var N : integer; "  +
						 " end-proc; " ;
		recognizeFalse(code); //No se permite definir variables luego del begin
	}
	@Test 
	public void testPackage1() throws UnsupportedEncodingException{
		String code = 	 " function FUN1():integer; "  +
						 " var N: integer; "  +
						 " begin N := 45; "  +
						 " end-func N; ";
		recognizeTrue(code);
	}
	@Test 
	public void testPackage2() throws UnsupportedEncodingException{
		String code =  
				"const M : natural = 7n; " +
				"const R : integer = 90; " +
				"var N, S : integer; " +
				"var A[12] : natural; " +
				
				"procedure PROC1(); " +
					"begin " + 
				"end-proc;" +
				
				"procedure test1(); " +
					"var N : integer; " +
					"begin " +
				"end-proc; " +
				
				"procedure test2(); " +
					"var N : integer; " +
					"begin " +
				"end-proc; " ;
		recognizeTrue(code);
	}
	@Test 
	public void testPackage3() throws UnsupportedEncodingException{
		String code =	
						"const M : natural = 7n, R : integer = 90; "  +
						"var N, S : integer; "  +
						"var A[12] : natural; "  +
						
						"procedure PROC1(); "  +
						"var A : integer; "  +
						"var B : natural; "  +
						"begin " +
						"B := M; "  +
							"while B > 0 do " +
								"if even(B) then "  +
									"A := A + 1; "  +
								"end-if; "  +
								"B := B - 1; "  +
							"end-while; "  +
						 "end-proc; "  +
						
						 "procedure PROC2(byref R : integer); "  +
						 "const T : natural = 67n; "  +
						 "var W11 : integer; "  +
						 "var W12, Q, R : natural; "  +
						 "begin S := (S ++ M) ** 2n; "  +
							 "Q := 1n; read W11; "  +
							 "{lectura de teclado} W12 := TONATURAL(AW11 * 2 + S); "  +
							 "while (W12 - 2) <= R + S do  "  +
								 " W12 := W12 * 2; "  +
							 "end-while; "  +
							 "while W12 + M > Y / 2 do  "  +
								 "Q := Q * 2; W12 := W12 / 2; "  +
								 "if W12 <= R then "  +
									 "R := R - W12; "  +
									 "Q := Q + 1; " +
								"end-if; "  +
							 "end-while; "  +
							 "A[1] := Q; "  +
						 "end-proc; "  +
						
						 "function FUN1(T: integer, byval N2 : integer) : integer; "  +
						 "var N: integer; "  +
						 "begin if T > 0 then "  +
							 "N := 45; "  +
							 "else N := 70; "  +
							 "end-if; "  +
						 "end-func N * 2; "  +
						
						 "procedure MAIN(); "  +
						 "var X: integer; "  +
						 "begin " +
							 "PROC1(); "  +
							 "S := M + 1; "  +
							 "PROC2(S); "  +
							 "X := FUN1(5, 8); "  +
							 "A[5n] := X; "  +
							 "show A[TONATURAL(S) ++ 1n]; "  +
						 "end-proc; " ;
		recognizeTrue(code);
	}

	@Test /*Error PROC1;procedure*/
	public void testPackageErr() throws UnsupportedEncodingException{
		String code = 	 " procedure PROC1;procedure test1(); "  +
						 " begin var N : integer; "  +
						 " end-proc; "  +
						
						 " procedure test2(); "  +
						 " begin var N : integer; "  +
						 " end-proc; " ;
		recognizeFalse(code);
	}
	@Test /*procedure test1() begin*/
	public void testPackage1Err() throws UnsupportedEncodingException{
		String code = 	 " function FUN1():integer; "  +
						 " var N: integer; "  +
						 " begin N := 45; "  +
							 " end-func N; "  +
							 " procedure test1() begin var N : integer; "  +
						 " end-func; "  +
						
						 " procedure test2(); "  +
						 " begin var N : integer; "  +
						 " end-proc; " ;
		recognizeFalse(code);
	}
	@Test 
	public void testPackage2Err() throws UnsupportedEncodingException{
		String code =  
				"const M : natural = 7n; " +
				"const R : integer = 90; " +
				"var N, S : integer; " +
				"var A[12] : natural; " +
				"procedure PROC1();" +
				"procedure test1(); " +
				"begin " +
				"var N : integer; " +
				"end-proc;" +
				"procedure test2(); " +
				"begin " +
				"var N : integer; " +
				"end-proc; " ;
		recognizeFalse(code);
	}
	@Test /*Error while B > 0 do if even(B) then A := A + 1;  B := B - 1; end-while; {FALTA EL END-IF;}*/
	public void testPackage3Err() throws UnsupportedEncodingException{
		String code = 	 " const M : natural = 7n, R : integer = 90; "  +
						 " var N, S : integer; var A[12] : natural; "  +
						
						 " procedure PROC1();  "  +
							 " var A : integer; var B : natural;  "  +
							 " begin B := M;  "  +
							 " while B > 0 do  "  +
							 "  if even(B) then  "  +
								 " A := A + 1;  "  +
								 " B := B - 1;  "  +
							 " end-while;  "  +
						 " end-proc;  "  +
						
						 " procedure PROC2(byref R : integer);  "  +
						 " const T : natural = 67n;  "  +
						 " var W11 : integer;  "  +
						 " var W12, Q, R : natural;  "  +
						 " begin  "  +
							 " S := (S ++ M) ** 2n; "  +
							 " Q := 1n; "  +
							 " read W11; "  +
							 " {lectura de teclado} W12 := TONATURAL(AW11 * 2 + S); "  +
							 " while (W12 < 2) <= R + S do  "  +
								 " W12 := W12 * 2; "  +
							 " end-while; "  +
							 " while W12 + M > Y / 2 do  "  +
								 " Q := Q * 2; "  +
								 " W12 := W12 / 2; "  +
								 "  if W12 <= R then R := R - W12; "  +
									 " Q := Q + 1; "  +
								 " end-if; "  +
							 " end-while; "  +
							 " A[1] := Q; "  +
						 " end-proc; "  +
						
						 " function FUN1(T: integer, byval N2 : integer) : integer; "  +
						 " var N: integer; "  +
						 " begin if T > 0 then "  +
							 " N := 45; "  +
							 " else N := 70; "  +
							 " end-if; "  +
						 " end-func N * 2; "  +
						
						 " procedure MAIN(); "  +
						 " var X: integer; "  +
						 " begin PROC1(); "  +
							 " S := M + 1; "  +
							 " PROC2(S); "  +
							 " X := FUN1(5, 8); "  +
							 " A[5n] := X; "  +
							 " show A[TONATURAL(S) ++ 1n]; "  +
						 " end-proc; " ;
		recognizeFalse(code);
	}

}