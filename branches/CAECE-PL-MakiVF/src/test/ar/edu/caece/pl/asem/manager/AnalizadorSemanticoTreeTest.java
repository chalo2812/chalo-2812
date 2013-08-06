package ar.edu.caece.pl.asem.manager;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;

import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;
import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Asignacion;
import ar.edu.caece.pl.asem.model.impl.treeelements.Cadena;
import ar.edu.caece.pl.asem.model.impl.treeelements.Codigo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Constante;
import ar.edu.caece.pl.asem.model.impl.treeelements.Cuerpo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Declaraciones;
import ar.edu.caece.pl.asem.model.impl.treeelements.Distinto;
import ar.edu.caece.pl.asem.model.impl.treeelements.DivisionInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.DivisionNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.Igual;
import ar.edu.caece.pl.asem.model.impl.treeelements.Mayor;
import ar.edu.caece.pl.asem.model.impl.treeelements.MayorIgual;
import ar.edu.caece.pl.asem.model.impl.treeelements.Menor;
import ar.edu.caece.pl.asem.model.impl.treeelements.MenorIgual;
import ar.edu.caece.pl.asem.model.impl.treeelements.Mientras;
import ar.edu.caece.pl.asem.model.impl.treeelements.NumeroInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.NumeroNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametros;
import ar.edu.caece.pl.asem.model.impl.treeelements.Procedimiento;
import ar.edu.caece.pl.asem.model.impl.treeelements.ProductoInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.ProductoNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.Programa;
import ar.edu.caece.pl.asem.model.impl.treeelements.ReturnType;
import ar.edu.caece.pl.asem.model.impl.treeelements.ShowLn;
import ar.edu.caece.pl.asem.model.impl.treeelements.Si;
import ar.edu.caece.pl.asem.model.impl.treeelements.Sino;
import ar.edu.caece.pl.asem.model.impl.treeelements.SumaInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.Variable;
import ar.edu.caece.pl.asem.model.tree.Tree;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.TokenStream;

public class AnalizadorSemanticoTreeTest {

	InputStream istream;
	AnalizadorSintactico asin;
	ITokenStream tokenStream;
	
	private Map<String, String> reconocer (String code, Tree<AbstractElement> arbolM){
		
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(code.getBytes( "UTF-8" ));
		} catch (UnsupportedEncodingException e) {
			new AssertionError( "No se soporta el encoding: ["+ code +"]" );
		}
		
		tokenStream = new TokenStream(is);
		asin = new AnalizadorSintactico(tokenStream,false);
		
		assertTrue(asin.reconocer());
		
		//Arbol generado
		Tree<AbstractElement> arbolG = new Tree<AbstractElement>();
		arbolG.setRoot(asin.getTreeNode());
		
		asin.getErrorManager().debug("Arbol generado:");
		asin.getErrorManager().debug(arbolG.toString());
		
		assertTrue(arbolG.equals(arbolM));
		
		return asin.getErrorManager().getErrors();
	}

	@Test
	public void testArbol1() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());

		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode suma1 = new TreeNode(new SumaInt());
		TreeNode suma2 = new TreeNode(new SumaInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		declaraciones.addChild(variable1);
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		main.addChild(parametrosMain);
		//declaraciones main y cuerpo
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		suma1.addChild(suma2);
		suma1.addChild(numero3);
		suma2.addChild(numero1);
		suma2.addChild(numero2);
		//w = 1 + 2 + 3;
		asignacion.addChild(suma1);
		
		String code =
			" var w : integer; " +
			" procedure main(); "  +
			" begin " +
			" w := 1 + 2 + 3;" +
			" end-proc;";
		
		this.reconocer(code, tree);
	}
	
	@Test
    public void testArbol2() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode vW = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		TreeNode vNumero1 = new TreeNode(new Variable("numero1",SymbolTable.INTEGER));
		TreeNode vNumero2 = new TreeNode(new Variable("numero2",SymbolTable.INTEGER));
		TreeNode vNumero3 = new TreeNode(new Variable("numero3",SymbolTable.INTEGER));
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode asignacion3 = new TreeNode(new Asignacion());
		TreeNode asignacion4 = new TreeNode(new Asignacion());
		TreeNode asignacion5 = new TreeNode(new Asignacion());
		TreeNode suma1   = new TreeNode(new SumaInt());
		TreeNode suma2   = new TreeNode(new SumaInt());
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		
		declaraciones.addChild(vW); //var w : integer;
		declaraciones.addChild(vNumero1); //var numero1 : integer;
		declaraciones.addChild(vNumero2); //var numero2 : integer;
		declaraciones.addChild(vNumero3); //var numero3 : integer;
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		
		//numero1 := 1;
		asignacion1.addChild(vNumero1);
		asignacion1.addChild(numero1);
		//numero2 := 2;
		asignacion2.addChild(vNumero2);
		asignacion2.addChild(numero2);
		//numero3 := 3;
		asignacion3.addChild(vNumero3);
		asignacion3.addChild(numero3);
		//w := numero1 + numero2;
		suma1.addChild(vNumero1);
		suma1.addChild(vNumero2);
		asignacion4.addChild(vW);
		asignacion4.addChild(suma1);
		//w := w + numero3;
		suma2.addChild(vW);
		suma2.addChild(vNumero3);
		asignacion5.addChild(vW);
		asignacion5.addChild(suma2);
		//Juntar sentencias en cuerpoMain
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(asignacion2);
		cuerpoMain.addChild(asignacion3);
		cuerpoMain.addChild(asignacion4);
		cuerpoMain.addChild(asignacion5);
		
		String code =
			" var w : integer; " +
			" var numero1 : integer; " +
			" var numero2 : integer; " +
			" var numero3 : integer; " +
			" procedure main(); "  +
			" begin " +
			" numero1 := 1; "  +
			" numero2 := 2; "  +
			" numero3 := 3; "  +
			" w := numero1 + numero2;" +
			" w := w + numero3;" +
			" end-proc;";
		
		this.reconocer(code, tree);
	}
	
	
	@Test
    public void testArbol3() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode menorIgual = new TreeNode(new MenorIgual());
		TreeNode cuerpoSi   = new TreeNode(new Cuerpo());
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		declaraciones.addChild(asignacion1);
		declaraciones.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(menorIgual);
		menorIgual.addChild(variable1);
		menorIgual.addChild(constante1);
		
		si.addChild(cuerpoSi);
		
		variable1.addChild(asignacion2);
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorvariable2);
		
		String code =
				" const C : integer = 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" if X <= C then "  +
				" X := 6; "  +
				" end-if;" +
				" end-proc;";
			
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	
	}
	
	@Test
    public void testArbol4() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode mientras = new TreeNode(new Mientras());
		TreeNode menorIgual = new TreeNode(new MenorIgual());
		TreeNode cuerpoMientras  = new TreeNode(new Cuerpo());
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(mientras);
		mientras.addChild(menorIgual);
		menorIgual.addChild(variable1);
		menorIgual.addChild(constante1);
		
		mientras.addChild(cuerpoMientras);
		
		variable1.addChild(asignacion2);
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorvariable2);
		
		String code =
				" const C : integer := 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" while X <= C then "  +
				" X := 6; "  +
				" end-while;" +
				" end-proc;";
			
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol5() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode menorIgual = new TreeNode(new MenorIgual());
		TreeNode sino = new TreeNode(new Sino());
		TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(menorIgual);
		menorIgual.addChild(variable1);
		menorIgual.addChild(constante1);
		
		si.addChild(cuerpoSi);
		si.addChild(sino);
		
		asignacion1.addChild(variable2);
		asignacion1.addChild(valorvariable2);
		
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorVariable1);
		
		cuerpoSi.addChild(asignacion1);	
		sino.addChild(asignacion2);	
		
		String code =
				" const C : integer := 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" si X <= C then "  +
				" X := 6; "  +
				" sino" +
				" Y := 6;" +
				" end-if;" +
				" end-proc;";
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol6() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode menorIgual = new TreeNode(new MenorIgual());
		TreeNode sino = new TreeNode(new Sino());
		TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
		
		TreeNode suma1 = new TreeNode(new SumaInt());
		TreeNode suma2 = new TreeNode(new SumaInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(menorIgual);
		menorIgual.addChild(variable1);
		menorIgual.addChild(constante1);
		
		si.addChild(cuerpoSi);
		si.addChild(sino);
		
		asignacion1.addChild(variable2);
		asignacion1.addChild(valorvariable2);
		
		asignacion2.addChild(variable2);
		suma1.addChild(suma2);
		suma1.addChild(numero3);
		suma2.addChild(numero1);
		suma2.addChild(numero2);
		
		asignacion2.addChild(suma2);
		
		cuerpoSi.addChild(asignacion1);	
		sino.addChild(asignacion2);	
		
		assertTrue(false); // Falta la validacion con CODE
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol7() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode showLn = new TreeNode(new ShowLn());
		TreeNode cadena = new TreeNode(new Cadena("PRUEBA"));
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(showLn);
		cuerpoMain.addChild(cadena);
		
		String code = " procedure main(); "  +
					" begin " +
					" ShowLn (''PRUEBA'')" +
					" end-proc;";
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	

	@Test
    public void testArbol8() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode arreglo = new TreeNode(new Arreglo("AR", SymbolTable.INTEGER, 2));
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		cuerpoMain.addChild(arreglo);
		arreglo.addChild(numero1);
		arreglo.addChild(numero2);	
		programa.addChild(codigo);
		codigo.addChild(main);
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		String code = 	" const M : natural = 7n; "  +
				" var N : integer; " +
				" var AR[2] : integer; "  +
				" procedure main(); "  +
					" begin " +
					" AR[0] := 1;" +
					" AR[1] := 2;" +
					" end-proc;";
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol9() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode arreglo = new TreeNode(new Arreglo("AR", SymbolTable.INTEGER, 2));
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		
		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode suma1 = new TreeNode(new SumaInt());
		TreeNode suma2 = new TreeNode(new SumaInt());
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
	
		tree.setRoot(programa);
		
		programa.addChild(declaraciones);
		cuerpoMain.addChild(arreglo);
		arreglo.addChild(numero1);
		arreglo.addChild(numero2);	
		programa.addChild(codigo);
		codigo.addChild(main);
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		
		cuerpoMain.addChild(asignacion);
		
		asignacion.addChild(variable1);
		
		suma1.addChild(suma2);
		suma1.addChild(numero3);
		suma2.addChild(numero1);
		suma2.addChild(numero2);
		
		asignacion.addChild(suma1);
		
		String code =
				" var w : natural; " +
				" procedure main(); "  +
				" begin " +
				" w := 1 + 2 + 3;" +
				" end-proc;";
			
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol10() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode suma1 = new TreeNode(new SumaInt());
		TreeNode suma2 = new TreeNode(new SumaInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		suma1.addChild(suma2);
		suma1.addChild(numero3);
		suma2.addChild(numero1);
		suma2.addChild(numero2);
		
		asignacion.addChild(suma1);
		
		String code =
				" var w : natural; " +
				" procedure main(); "  +
				" begin " +
				" w := 1 + 2 + 3;" +
				" end-proc;";
			
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol12() {
	Tree<AbstractElement> tree = new Tree<AbstractElement>();
	
	TreeNode programa = new TreeNode(new Programa());
	TreeNode declaraciones = new TreeNode(new Declaraciones());
	TreeNode codigo = new TreeNode(new Codigo());
	
	TreeNode main = new TreeNode(new Procedimiento("main"));
	TreeNode parametrosMain = new TreeNode(new Parametros());
	TreeNode declaracionesMain = new TreeNode(new Declaraciones());
	TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
	TreeNode cuerpoMain = new TreeNode(new Cuerpo());

	TreeNode asignacion1 = new TreeNode(new Asignacion());
	TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
	TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
	TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
	TreeNode asignacion2 = new TreeNode(new Asignacion());
	TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
	TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
	
	TreeNode si = new TreeNode(new Si());
	TreeNode mayorIgual = new TreeNode(new MayorIgual());
	TreeNode sino = new TreeNode(new Sino());
	TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
	
	//Root
	tree.setRoot(programa);
	//Agregar declaraciones globales y codigo
	programa.addChild(declaraciones);
	programa.addChild(codigo);

	
	codigo.addChild(main);
	//declaraciones main y cuerpo
	main.addChild(parametrosMain);
	main.addChild(declaracionesMain);
	main.addChild(returnTypeMain);
	main.addChild(cuerpoMain);
		
	asignacion1.addChild(variable1);
	asignacion1.addChild(valorVariable1);
	
	cuerpoMain.addChild(asignacion1);
	cuerpoMain.addChild(constante1);
	
	cuerpoMain.addChild(si);
	si.addChild(mayorIgual);
	mayorIgual.addChild(variable1);
	mayorIgual.addChild(constante1);
	
	si.addChild(cuerpoSi);
	si.addChild(sino);
	
	asignacion1.addChild(variable2);
	asignacion1.addChild(valorvariable2);
	
	asignacion2.addChild(variable2);
	asignacion2.addChild(valorVariable1);
	
	cuerpoSi.addChild(asignacion1);	
	sino.addChild(asignacion2);	
	
	String code =
			" const C : integer := 1; " +
			" var X : natural; " +
		//	" var Y : natural; " +
			" procedure main(); "  +
			" begin " +
			" si X >= C then "  +
			" X := 6; "  +
			" sino" +
			" Y := 6;" +
			" end-if;" +
			" end-proc;";
		
	this.reconocer(code, tree);
	
	System.out.println(tree.toString());
}
	
	@Test
    public void testArbol13() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode mayor = new TreeNode(new Mayor());
		TreeNode sino = new TreeNode(new Sino());
		TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(mayor);
		mayor.addChild(variable1);
		mayor.addChild(constante1);
		
		si.addChild(cuerpoSi);
		si.addChild(sino);
		
		asignacion1.addChild(variable2);
		asignacion1.addChild(valorvariable2);
		
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorVariable1);
		
		cuerpoSi.addChild(asignacion1);	
		sino.addChild(asignacion2);	
		
		String code =
				" const C : integer := 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" si X > C then "  +
				" X := 6; "  +
				" sino" +
				" Y := 6;" +
				" end-if;" +
				" end-proc;";
			
		this.reconocer(code, tree);
	}
	
	@Test
    public void testArbol14() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode menor= new TreeNode(new Menor());
		TreeNode sino = new TreeNode(new Sino());
		TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(menor);
		menor.addChild(variable1);
		menor.addChild(constante1);
		
		si.addChild(cuerpoSi);
		si.addChild(sino);
		
		asignacion1.addChild(variable2);
		asignacion1.addChild(valorvariable2);
		
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorVariable1);
		
		cuerpoSi.addChild(asignacion1);	
		sino.addChild(asignacion2);	
		
		String code =
				" const C : integer := 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" si X < C then "  +
				" X := 6; "  +
				" sino" +
				" Y := 6;" +
				" end-if;" +
				" end-proc;";
			
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol15() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode distinto = new TreeNode(new Distinto());
		TreeNode sino = new TreeNode(new Sino());
		TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(distinto);
		distinto.addChild(variable1);
		distinto.addChild(constante1);
		
		si.addChild(cuerpoSi);
		si.addChild(sino);
		
		asignacion1.addChild(variable2);
		asignacion1.addChild(valorvariable2);
		
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorVariable1);
		
		cuerpoSi.addChild(asignacion1);	
		sino.addChild(asignacion2);	
		
		String code =
				" const C : integer := 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" si X != C then "  +
				" X := 6; "  +
				" sino" +
				" Y := 6;" +
				" end-if;" +
				" end-proc;";
			
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol16() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());

		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode divisionInt1 = new TreeNode(new DivisionInt());
		TreeNode divisionInt2 = new TreeNode(new DivisionInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		divisionInt1.addChild(divisionInt2);
		divisionInt1.addChild(numero3);
		divisionInt2.addChild(numero1);
		divisionInt2.addChild(numero2);
		
		asignacion.addChild(divisionInt1);
		String code =
				" var w : natural; " +
				" procedure main(); "  +
				" begin " +
				" w := 1 / 2 / 3;" +
				" end-proc;";
			
		this.reconocer(code, tree);
	}
	
	@Test
    public void testArbol17() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode divisionNat1 = new TreeNode(new DivisionNat());
		TreeNode divisionNat2 = new TreeNode(new DivisionNat());
		TreeNode numero1 = new TreeNode(new NumeroNat(1));
		TreeNode numero2 = new TreeNode(new NumeroNat(2));
		TreeNode numero3 = new TreeNode(new NumeroNat(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		divisionNat1.addChild(divisionNat2);
		divisionNat1.addChild(numero3);
		divisionNat2.addChild(numero1);
		divisionNat2.addChild(numero2);
		
		asignacion.addChild(divisionNat1);
		
		String code =
				" var w : natural; " +
				" procedure main(); "  +
				" begin " +
				" w := 1 // 2 // 3;" +
				" end-proc;";
			
		this.reconocer(code, tree);
	}
	
	@Test
    public void testArbol18() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());
		
		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion1 = new TreeNode(new Asignacion());
		TreeNode constante1 = new TreeNode(new Constante("C",SymbolTable.INTEGER,1));
		TreeNode variable1 = new TreeNode(new Variable("X",SymbolTable.NATURAL));
		TreeNode valorVariable1 = new TreeNode(new NumeroNat(6));
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("Y",SymbolTable.NATURAL));
		TreeNode valorvariable2 = new TreeNode(new NumeroNat(6));
		
		TreeNode si = new TreeNode(new Si());
		TreeNode igual = new TreeNode(new Igual());
		TreeNode sino = new TreeNode(new Sino());
		TreeNode cuerpoSi  = new TreeNode(new Cuerpo());
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
	
		
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
			
		asignacion1.addChild(variable1);
		asignacion1.addChild(valorVariable1);
		
		cuerpoMain.addChild(asignacion1);
		cuerpoMain.addChild(constante1);
		
		cuerpoMain.addChild(si);
		si.addChild(igual);
		igual.addChild(variable1);
		igual.addChild(constante1);
		
		si.addChild(cuerpoSi);
		si.addChild(sino);
		
		asignacion1.addChild(variable2);
		asignacion1.addChild(valorvariable2);
		
		asignacion2.addChild(variable2);
		asignacion2.addChild(valorVariable1);
		
		cuerpoSi.addChild(asignacion1);	
		sino.addChild(asignacion2);	
		
		String code =
				" const C : integer := 1; " +
				" var X : natural; " +
			//	" var Y : natural; " +
				" procedure main(); "  +
				" begin " +
				" si X = C then "  +
				" X := 6; "  +
				" sino" +
				" Y := 6;" +
				" end-if;" +
				" end-proc;";
			
		this.reconocer(code, tree);
		
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol19() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());

		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());
		
		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode productoNat1 = new TreeNode(new ProductoNat());
		TreeNode productoNat2 = new TreeNode(new ProductoNat());
		TreeNode numero1 = new TreeNode(new NumeroNat(1));
		TreeNode numero2 = new TreeNode(new NumeroNat(2));
		TreeNode numero3 = new TreeNode(new NumeroNat(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		productoNat1.addChild(productoNat2);
		productoNat1.addChild(numero3);
		productoNat2.addChild(numero1);
		productoNat2.addChild(numero2);
		
		asignacion.addChild(productoNat1);
		
		
		String code =
			" var w : natural; " +
			" procedure main(); "  +
			" begin " +
			" w := 1 ** 2 ** 3;" +
			" end-proc;";
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testArbol20() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());

		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		TreeNode productoInt1 = new TreeNode(new ProductoInt());
		TreeNode productoInt2 = new TreeNode(new ProductoInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		productoInt1.addChild(productoInt2);
		productoInt1.addChild(numero3);
		productoInt2.addChild(numero1);
		productoInt2.addChild(numero2);
		
		asignacion.addChild(productoInt1);
		
		
		String code =
			" var w : integer; " +
			" procedure main(); "  +
			" begin " +
			" w := 1 * 2 * 3;" +
			" end-proc;";
		
		this.reconocer(code, tree);
		
		System.out.println(tree.toString());
	}
	
	@Test
    public void testCompareArbol1() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());

		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode productoInt1 = new TreeNode(new ProductoInt());
		TreeNode productoInt2 = new TreeNode(new ProductoInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		productoInt1.addChild(productoInt2);
		productoInt1.addChild(numero3);
		productoInt2.addChild(numero1);
		productoInt2.addChild(numero2);
		
		asignacion.addChild(productoInt1);
		
		//--------------- tree2 ------
		
		Tree<AbstractElement> tree2 = new Tree<AbstractElement>();
		
		//Root
		tree2.setRoot(programa);
		
		//tree.equals(tree2);
		assertTrue(tree.equals(tree2));
		
		System.out.println(tree2.toString());
	}
	
	
	@Test
    public void testCompareArbol2() {
		Tree<AbstractElement> tree = new Tree<AbstractElement>();
		
		TreeNode programa = new TreeNode(new Programa());
		TreeNode declaraciones = new TreeNode(new Declaraciones());
		TreeNode codigo = new TreeNode(new Codigo());

		TreeNode main = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain = new TreeNode(new Parametros());
		TreeNode declaracionesMain = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain = new TreeNode(new Cuerpo());

		TreeNode asignacion = new TreeNode(new Asignacion());
		TreeNode variable1 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode productoInt1 = new TreeNode(new ProductoInt());
		TreeNode productoInt2 = new TreeNode(new ProductoInt());
		TreeNode numero1 = new TreeNode(new NumeroInt(1));
		TreeNode numero2 = new TreeNode(new NumeroInt(2));
		TreeNode numero3 = new TreeNode(new NumeroInt(3));
		
		
		//Root
		tree.setRoot(programa);
		//Agregar declaraciones globales y codigo
		programa.addChild(declaraciones);
		programa.addChild(codigo);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo.addChild(main);
		//declaraciones main y cuerpo
		main.addChild(parametrosMain);
		main.addChild(declaracionesMain);
		main.addChild(returnTypeMain);
		main.addChild(cuerpoMain);
		//sentencia1
		cuerpoMain.addChild(asignacion);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia1
		asignacion.addChild(variable1);
		//agregar la suma que se asigna a variable W 
		productoInt1.addChild(productoInt2);
		productoInt1.addChild(numero3);
		productoInt2.addChild(numero1);
		productoInt2.addChild(numero2);
		
		asignacion.addChild(productoInt1);
		
		//--------------- tree2 ------
		
		Tree<AbstractElement> tree2 = new Tree<AbstractElement>();
		
		TreeNode programa2 = new TreeNode(new Programa());
		TreeNode declaraciones2 = new TreeNode(new Declaraciones());
		TreeNode codigo2 = new TreeNode(new Codigo());
		
		TreeNode main2 = new TreeNode(new Procedimiento("main"));
		TreeNode parametrosMain2 = new TreeNode(new Parametros());
		TreeNode declaracionesMain2 = new TreeNode(new Declaraciones());
		TreeNode returnTypeMain2 = new TreeNode(new ReturnType(SymbolTable.VOID));
		TreeNode cuerpoMain2 = new TreeNode(new Cuerpo());
		
		TreeNode asignacion2 = new TreeNode(new Asignacion());
		TreeNode variable2 = new TreeNode(new Variable("w",SymbolTable.INTEGER));
		
		TreeNode productoInt3 = new TreeNode(new ProductoInt());
		TreeNode productoInt4 = new TreeNode(new ProductoInt());
		TreeNode numero4 = new TreeNode(new NumeroInt(1));
		TreeNode numero5 = new TreeNode(new NumeroInt(2));
		TreeNode numero6 = new TreeNode(new NumeroInt(2));
		
		
		//Root
		tree2.setRoot(programa2);
		//Agregar declaraciones globales y codigo
		programa2.addChild(declaraciones2);
		programa2.addChild(codigo2);
		//Agregar procedimientos
		//proc1
		//proc2
		///....
		//Main
		codigo2.addChild(main2);
		//declaraciones main y cuerpo
		main2.addChild(parametrosMain2);
		main2.addChild(declaracionesMain2);
		main2.addChild(returnTypeMain2);
		main2.addChild(cuerpoMain2);
		//sentencia1
		cuerpoMain2.addChild(asignacion2);
		//sentencia2
		//...
		//endproc
		//sub-arbol de sentencia2
		asignacion2.addChild(variable2);
		//agregar la suma que se asigna a variable W 
		productoInt3.addChild(productoInt4);
		productoInt3.addChild(numero6);
		productoInt4.addChild(numero4);
		productoInt4.addChild(numero5);
		
		asignacion2.addChild(productoInt3);
		
		//Compare	
		assertTrue(tree.equals(tree2));
		
		//print
		System.out.println(tree.toString());
		System.out.println(tree2.toString());
	}
	
	
	// Cadena - Funcion - Impar - Invocacion - Par - Parametro - Parametros - Read - ReturnType - Show - ToInteger - ToNatural -
	@Test
	public void testArbolFull1() {
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
								"A := A + 1n; "  +
								"else " +
									"if odd(B) then "  +
										"A := A + 2n; "  +
									"end-if; "  +
							"end-if; "  +
							"B := B - 1; "  +
						"end-while; "  +
					 "end-proc; " +
					
					 "procedure PROC2(byref Y : integer); "  +
					 "const T : natural = 67n; "  +
					 "var W11 : integer; "  +
					 "var W12, Q, R : natural; "  +
					 "begin S := (S ++ M) ** 2n; "  +
						 "Q := 1n; read W11; "  +
						 "{lectura de teclado} W12 := TONATURAL(W11 * 2 + S); "  +
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
						 "A[5] := X; "  +
						 "show A[TONATURAL(S) ++ 1n]; "  +
					 "end-proc; "
						 ;
		 
		 Tree<AbstractElement> tree = new Tree<AbstractElement>();
		 
		 this.reconocer(code, tree);
	}
}
