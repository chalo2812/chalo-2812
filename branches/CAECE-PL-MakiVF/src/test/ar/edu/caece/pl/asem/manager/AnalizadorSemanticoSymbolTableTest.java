package ar.edu.caece.pl.asem.manager;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Constante;
import ar.edu.caece.pl.asem.model.impl.treeelements.Variable;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.TokenStream;

public class AnalizadorSemanticoSymbolTableTest {

	InputStream istream;
	AnalizadorSintactico asin;
	ITokenStream tokenStream;
	
	private Map<String, String> reconocer (String code){
		
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(code.getBytes( "UTF-8" ));
		} catch (UnsupportedEncodingException e) {
			new AssertionError( "No se soporta el encoding: ["+ code +"]" );
		}
		
		tokenStream = new TokenStream(is);
		asin = new AnalizadorSintactico(tokenStream,true);
		
		assertTrue(asin.reconocer());
		
		return asin.getErrorManager().getErrors();
	}
	
	
	
	// SOLO CONSTANTES
	@Test
	public void testSymbolTableConst1(){
		String code = 	"const M : natural = 7n;";
		
		reconocer(code);
		
		Constante m = new Constante("m", SymbolTable.NATURAL, 7);

		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(m) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableConst2(){
		String code = 	"const M : natural = 7n; " +
						"const R : integer = 90; ";
		
		reconocer(code);
		
		Constante m = new Constante("m", SymbolTable.NATURAL, 7);
		Constante r = new Constante("r", SymbolTable.INTEGER, 90);

		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(m) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableConst3(){
		String code = 	"const M : integer = 7; " +
						"const R : integer = 90; ";
		
		reconocer(code);
		
		Constante m = new Constante("m", SymbolTable.INTEGER, 7);
		Constante r = new Constante("r", SymbolTable.INTEGER, 90);

		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(m) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableConst4(){
		String code = 	"const M : integer = 7, R : integer = 90; ";
		
		reconocer(code);
		
		Constante m = new Constante("m", SymbolTable.INTEGER, 7);
		Constante r = new Constante("r", SymbolTable.INTEGER, 90);

		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(m) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableConst5(){
		String code = 	"const M : natural = 7n, R : integer = 90; ";
		
		reconocer(code);
		
		Constante m = new Constante("m", SymbolTable.NATURAL, 7);
		Constante r = new Constante("r", SymbolTable.INTEGER, 90);

		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(m) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableConst6(){
		String code = 	"const M : natural = 7n, R : natural = 90n; ";
		
		reconocer(code);
		
		Constante m = new Constante("m", SymbolTable.NATURAL, 7);
		Constante r = new Constante("r", SymbolTable.NATURAL, 90);

		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(m) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	
	// SOLO VARIABLES
	@Test
	public void testSymbolTableVariables1() throws UnsupportedEncodingException{//no carga variables...

		String code = 	"var N : integer; ";
					
		reconocer(code);
		
		Variable n = new Variable("n", SymbolTable.INTEGER);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(n) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}

	@Test
	public void testSymbolTableVariables2() throws UnsupportedEncodingException{//no carga variables...

		String code = 	"var N : integer; " +
						"var R : integer; ";
					
		reconocer(code);
		
		Variable n = new Variable("n", SymbolTable.INTEGER);
		Variable r = new Variable("r", SymbolTable.INTEGER);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(n) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}

	@Test
	public void testSymbolTableVariables3() throws UnsupportedEncodingException{//no carga variables...

		String code = 	"var N : integer; " +
						"var R : natural; ";
					
		reconocer(code);
		
		Variable n = new Variable("n", SymbolTable.INTEGER);
		Variable r = new Variable("r", SymbolTable.NATURAL);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(n) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableVariables4() throws UnsupportedEncodingException{//no carga variables...

		String code = 	"var N, R : integer;";
					
		reconocer(code);
		
		Variable n = new Variable("n", SymbolTable.INTEGER);
		Variable r = new Variable("r", SymbolTable.INTEGER);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(n) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n"));
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	@Test
	public void testSymbolTableVariables5() throws UnsupportedEncodingException{//no carga variables...

		String code = 	"var N, R : natural;";
					
		reconocer(code);
		
		Variable n = new Variable("n", SymbolTable.NATURAL);
		Variable r = new Variable("r", SymbolTable.NATURAL);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(n) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(r) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("r") );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 2 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
	}
	
	///////////////////
	// Solo ARREGLOS
	///////////////////
	@Test
	public void testSymbolTable6() throws UnsupportedEncodingException{
		String code = 	"var A[12] : natural;";
					
		reconocer(code);
		
		Arreglo arreglo = new Arreglo("a", SymbolTable.NATURAL, 12);
	
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(arreglo) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 0 );
	}
	
	
	//////////////////////////////////////////
	// MIX Variables, Constantes y Arreglos.
	//////////////////////////////////////////
	@Test
	public void testSymbolTable7() throws UnsupportedEncodingException{
		String code = 	"var A[12] : natural; " +
						"var Achu[10] : natural; " +
						"var B[2] : integer;";
					
		reconocer(code);
		
		Arreglo arreglo = new Arreglo("a", SymbolTable.NATURAL, 12);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).contains(arreglo) );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 3 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 0 );
	}
	
	/////////////////////
	// PROCEDIMIENTOS
	/////////////////////
	@Test
	public void testDeEjemploMain0() throws UnsupportedEncodingException{

		String code = 	"procedure PROCA(); "  +
							" begin " +
						" end-proc;";
		reconocer(code);
		
		
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 0 );

		assertTrue(SymbolTable.getInstance().containsAmbiente("proca"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getParametrosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getVariablesSize() == 0 );
		
	}
	
	@Test
	public void testDeEjemploProcedimiento01() throws UnsupportedEncodingException{

		String code = 	"procedure PROCA(); "  +
						" const U : natural = 7n; "  +
							" begin " +
						" end-proc;";
		reconocer(code);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 0 );

		assertTrue(SymbolTable.getInstance().containsAmbiente("proca"));
		
		Constante u = new Constante("u",SymbolTable.NATURAL,7);
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").contains(u) );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsConst("u") );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getParametrosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getVariablesSize() == 0 );
	}
	@Test
	public void testDeEjemploProcedimiento02() throws UnsupportedEncodingException{

		String code = 	"procedure PROCA(); "  +
						"var v : natural; "  +
							" begin " +
						" end-proc;";
		reconocer(code);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 0 );

		assertTrue(SymbolTable.getInstance().containsAmbiente("proca"));
		
		Variable v = new Variable("v",SymbolTable.NATURAL);
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").contains(v) );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsVar("v") );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getParametrosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getVariablesSize() == 1 );
	}
	@Test
	public void testDeEjemploProcedimiento03() throws UnsupportedEncodingException{

		String code = 	"procedure PROCA(); "  +
						"var A[7] : natural; "  +
							" begin " +
						" end-proc;";
		reconocer(code);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 0 );

		assertTrue(SymbolTable.getInstance().containsAmbiente("proca"));
		
		Arreglo a = new Arreglo("a",SymbolTable.NATURAL,7);
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").contains(a) );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsArray("a") );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getParametrosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getVariablesSize() == 0 );
	}
	
	
	
	@Test
	public void testDeEjemploMain1() throws UnsupportedEncodingException{

		String code = 	"const M : natural = 7n; "  +
						"var N : integer; " +
						"var A[12] : natural; "  +
						
						"procedure PROCA(byref R : natural); "  +
							" const U : natural = 7n; "  +
							" var S : integer; " +
							" var T[12] : natural; "  +
							" begin " +
								" R := R * A[12]; " +
						" end-proc;";
		reconocer(code);
		
		
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsArray("a") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") == true );

		assertTrue(SymbolTable.getInstance().containsAmbiente("proca"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getParametrosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsArray("t") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsVar("s") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsConst("u") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("proca").containsParam("r") == true );
	}
	
	@Test
	public void testDeEjemploMain2() throws UnsupportedEncodingException{

		String code = 	"const M : natural = 7n; "  +
						"var N : integer; " +
						"var A[12] : natural; "  +
						
						"function FUNCA(byref R : natural):integer; "  +
							" const U : natural = 7n; "  +
							" var S : integer; " +
							" var T[12] : natural; "  +
							" begin " +
								" R := R * A[12]; " +
							" end-func S;"+
						
						"procedure main(); "  +
							" const U : natural = 7n; "  +
							" var S : integer; " +
							" var T[12] : natural; "  +
							" begin " +
								" R := R * A[12]; " +
							" end-proc;";
		reconocer(code);
		
		
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsArray("a") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") == true );

		assertTrue(SymbolTable.getInstance().containsAmbiente("funca"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getParametrosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsArray("t") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsVar("s") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsConst("u") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsParam("r") == true );
		
		assertTrue(SymbolTable.getInstance().containsAmbiente("main"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("main").containsArray("t") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").containsVar("s") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").containsConst("u") == true );
	}
	
	@Test
	public void testDeEjemploMain3() throws UnsupportedEncodingException{

		String code = 	" const M : natural = 7n; "  +
						" var N : integer; " +
						" var A[12] : natural; "  +
						
						" function funca(byref RR : natural):integer; "  +
							" const R : natural = 7n; "  +
							" var S : integer; " +
							" var T[12] : natural; "  +
							" begin " +
							" end-func S;"+
							
						" procedure main(); "  +
							" const U : natural = 7n; "  +
							" var S : natural; " +
							" var T[12] : natural; "  +
							" begin " +
								" S := U ** T[12]; " +
							" end-proc;";
		
		Map<String, String> results = reconocer(code);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsArray("a") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("n") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsConst("m") == true );

		assertTrue(SymbolTable.getInstance().containsAmbiente("funca"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getParametrosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getConstantesSize() == 1 );//FALLA por duplicado
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsArray("t") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsVar("s") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsConst("r") == true ); //FALLA por duplicado
		assertTrue(SymbolTable.getInstance().getAmbiente("funca").containsParam("rr") == true );
		
		//assertTrue(results.containsKey("Código de error: ASEM-PROCSFUNCS-2-BUILDSYMBOLTABLE_CHECK_003"));
		
		assertTrue(SymbolTable.getInstance().containsAmbiente("main"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getArreglosSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getConstantesSize() == 1 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getVariablesSize() == 1 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente("main").containsArray("t") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").containsVar("s") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").containsConst("u") == true );
	}
	
	@Test
	public void testDeEjemploMain4() throws UnsupportedEncodingException{

		String code = 	"var W : integer; " +
						"var numero1 : integer; " +
						"var numero2 : integer; " +
						"var numero3 : integer; " +
						
						" procedure main(); "  +
						" begin " +
							"numero1 := 1; "  +
							"numero2 := 2; "  +
							"numero3 := 3; "  +
							"W := numero1 + numero2;" +
							"W := W + numero3;" +
						" end-proc;";
		
		Map<String, String> results = reconocer(code);
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).getVariablesSize() == 4 );
		
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("w") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("numero1") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("numero2") == true );
		assertTrue(SymbolTable.getInstance().getAmbiente(SymbolTable.GLOBAL).containsVar("numero3") == true );

		assertTrue(SymbolTable.getInstance().containsAmbiente("main"));
		
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getParametrosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getArreglosSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getConstantesSize() == 0 );
		assertTrue(SymbolTable.getInstance().getAmbiente("main").getVariablesSize() == 0 );
	}
}

