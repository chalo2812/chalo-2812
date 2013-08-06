package ar.edu.caece.pl.asem.model.impl.visitors;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.asem.model.AbstractVisitor;
import ar.edu.caece.pl.asem.model.IVisitor;
import ar.edu.caece.pl.asem.model.impl.Ambiente;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametro;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AbstractAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.impl.BloqueVarParam;
import ar.edu.caece.pl.asin.model.impl.Condicion;
import ar.edu.caece.pl.asin.model.impl.Expresion;
import ar.edu.caece.pl.asin.model.impl.Factor;
import ar.edu.caece.pl.asin.model.impl.Header;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;
import ar.edu.caece.pl.asin.model.impl.Sentencia;
import ar.edu.caece.pl.asin.model.impl.ShowElem;
import ar.edu.caece.pl.asin.model.impl.Termino;
import ar.edu.caece.pl.asin.model.impl.Tipo;
import ar.edu.caece.pl.asin.model.impl.Var;

public class TypeCheckVisitor extends AbstractVisitor {
	
	SymbolTable symbolTable = SymbolTable.getInstance();
	
	//Singleton
	private static IVisitor instance = new TypeCheckVisitor();
	private TypeCheckVisitor(){}
	public static IVisitor getInstance() { return instance; }

	
	@Override
	protected void visit(AnalizadorSintactico nt) {
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		}
	}
	
	private void logError(AbstractAnalizadorSintactico nt, String message, String codeError) {
		nt.getErrorManager().error(codeError, message);
		nt.setValidated(false);
	}
	
	private void logError(AbstractAnalizadorSintactico nt, String message, String codeError, String env, String method) {
		nt.getErrorManager().error(codeError, message, env, method);
		nt.setValidated(false);
	}
	
	private void logError(AbstractAnalizadorSintactico nt, String message, String codeError, String env) {
		nt.getErrorManager().error(codeError, message, env);
		nt.setValidated(false);
	}
	
	
	@Override
	protected void visit(BloqueVarParam nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
	@Override
	protected void visit(Condicion nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// <CONDICION> -> ODD(<EXP>)
			
			if (nt.getTypeExp() != SymbolTable.INTEGER && nt.getTypeExp() != SymbolTable.NATURAL){
				logError(nt,"El tipo de dato de <EXP> en la producción <CONDICION> -> ODD(<EXP>), no es INTEGER o NATURAL. " +
							"<EXP> es del tipo " + SymbolTable.getInstance().getVerboseType(nt.getTypeExp()),
							"ASEM-CONDICION-1-TYPE_CHECK_001", nt.getEnvName() );
			}
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// <CONDICION> -> EVEN(<EXP>)
			
			if (nt.getTypeExp() != SymbolTable.INTEGER && nt.getTypeExp() != SymbolTable.NATURAL){
				logError(nt, 	"El tipo de dato de <EXP> en la producción <CONDICION> -> EVEN(<EXP>), no es INTEGER o NATURAL. " +
								"<EXP> es del tipo " + SymbolTable.getInstance().getVerboseType(nt.getTypeExp()),
							"ASEM-CONDICION-2-TYPE_CHECK_001", nt.getEnvName());
			}
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			// <CONDICION> -> <EXP> <C1> <EXP>
			
			if (nt.getTypeExp1() != SymbolTable.INTEGER && nt.getTypeExp1() != SymbolTable.NATURAL){
				logError(nt, 	"El tipo de dato de <EXP1> en la producción <CONDICION> -> <EXP1> <C1> <EXP2>, no es INTEGER o NATURAL" +
								"<EXP1> es del tipo: " + SymbolTable.getInstance().getVerboseType(nt.getTypeExp1()),
								"ASEM-CONDICION-3-TYPE_CHECK_001", nt.getEnvName());
			}
			else if (nt.getTypeExp2() != SymbolTable.INTEGER && nt.getTypeExp2() != SymbolTable.NATURAL){
				logError(nt, 	"El tipo de dato de <EXP2> en la producción <CONDICION> -> <EXP1> <C1> <EXP2>, no es INTEGER o NATURAL" +
								"<EXP2> es del tipo: " + SymbolTable.getInstance().getVerboseType(nt.getTypeExp2()),
								"ASEM-CONDICION-3-TYPE_CHECK_002", nt.getEnvName());
			}
			else if (nt.getTypeExp1() != nt.getTypeExp2() ){
				logError(nt, 	"El tipo de dato de <EXP1> y <EXP2> en la producción <CONDICION> -> <EXP1> <C1> <EXP2>, no coinciden" +
								"<EXP1> es del tipo: " + SymbolTable.getInstance().getVerboseType(nt.getTypeExp1()) + " y <EXP2> es del tipo: " + SymbolTable.getInstance().getVerboseType(nt.getTypeExp2()),
								"ASEM-CONDICION-3-TYPE_CHECK_003", nt.getEnvName());
			}
			break;
		}
	}
	
	@Override
	protected void visit(Expresion nt) {
		/* <EXP> ->	<TERMINO>  ++ <EXP>	|
				  	<TERMINO>   + <EXP>	|
				  	<TERMINO>  -- <EXP> |
				  	<TERMINO>   - <EXP>	|
			  		<TERMINO>
		*/
		
		// Hago un DISTINCT de los tipos de <Termino>
		LinkedHashSet<Integer> lhsTerminos = new LinkedHashSet<Integer>();
		lhsTerminos.addAll(nt.getTypeTerminoList());
		
		if (lhsTerminos.size() != 1){
			// Los tipos de dato de <Termino> producidos por <EXP> son diferentes.
			logError(nt, "Los tipos de dato de <Termino> producidos por <EXP> son diferentes.",
					"ASEM-EXPRESION-1-TYPE_CHECK_001", nt.getEnvName());
		}
		else if (lhsTerminos.size() == 1){
			// Los tipos de dato de los <Termino> son iguales! Bien!
			
			// Analizo si se reconoció algún Token operador
			if (nt.getTypeTokenList().size() > 0){
				// Existe al menos un token operador reconocido.
				
				// Hago un DISTINCT de los tipos de <Token> operador reconocidos.
				LinkedHashSet<Integer> lhsTokens = new LinkedHashSet<Integer>();
				lhsTokens.addAll(nt.getTypeTokenList());
				
				if (lhsTokens.size() != 1 ){
					// Los tipos de dato de los Token operador difieren.
					logError(nt, "Los tipos de dato de los Token operador difieren, al validar una <EXP>. Se encontraron: " + lhsTokens.size() + " tipos de datos: (" + lhsTokens.toString() + ")",
							"ASEM-EXPRESION-1-TYPE_CHECK_002", nt.getEnvName());	
				}
				else{
					// Bien! Seguimos en camino. Los tipos de dato de los Token operador coinciden, ó es único.
					
					// Ahora comparo el tipo de dato de los tokens operador con los de <Termino>
					if (nt.getTypeTerminoList().get(0) == SymbolTable.NATURAL && (nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_MAS_NAT || nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_MENOS_NAT )){
						// Produccion de Naturales.  Genial! Los tipo de dato de los tokens operador reconocidos coinciden con los de <Termino>
						nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(SymbolTable.NATURAL) + " en la produccion '<EXP> -> <TERMINO>  [++|+|--|-] <EXP> | <TERMINO>'" );
						nt.setTypeExpresion(SymbolTable.NATURAL);
					}
					else if (nt.getTypeTerminoList().get(0) == SymbolTable.INTEGER && (nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_MAS_INT || nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_MENOS_INT )){
						// Produccion de Enteros.  Genial! Los tipo de dato de los tokens operador reconocidos coinciden con los de <Termino>
						nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(SymbolTable.INTEGER) + " en la produccion '<EXP> -> <TERMINO>  [++|+|--|-] <EXP> | <TERMINO>'");
						nt.setTypeExpresion(SymbolTable.INTEGER);
					}
					else{
						// Todo mal! El tipo de dato de los Tokens operador reconocidos NO coinciden con los de <Termino>
						logError(nt,	"El tipo de dato de los Tokens operador reconocidos NO coinciden con los de <Termino>" +
										"Los Tipo de <Termino> son: " + SymbolTable.getInstance().getVerboseType(nt.getTypeTerminoList().get(0)),
										"ASEM-EXPRESION-1-TYPE_CHECK_003", nt.getEnvName());
					}
				}
			}
			else{
				// NO existe un token operador reconocido. -> El tipo de dato de <Expresion> pasa a ser el de los tipos de los terminos.
				nt.setTypeExpresion(nt.getTypeTerminoList().get(0));
			}
		}
	}
	
	@Override
	protected void visit(Factor nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// <FACTOR> -> ( <EXP> )
			
			if (nt.getTypeExpresion() == SymbolTable.INTEGER || nt.getTypeExpresion() == SymbolTable.NATURAL) {
				nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(nt.getTypeExpresion()) + " en la produccion <FACTOR> -> ( <EXP> )");
				nt.setTypeFactor(nt.getTypeExpresion());
			}
			else{
				logError(nt, "El tipo de dato de <EXP> en la producción <FACTOR> -> ( <EXP> ) no es Entero o Natural",
							"ASEM-FACTOR-1-TYPE_CHECK_001", nt.getEnvName());
			}
			
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// <FACTOR> -> NUMERO
			
			nt.setTypeFactor(SymbolTable.INTEGER);
			break;
		
		case IAnalizadorSintactico.PROD_TERCERA:
			// <FACTOR> -> NUMERO_NAT
			
			nt.setTypeFactor(SymbolTable.NATURAL);
			break;

		case IAnalizadorSintactico.PROD_CUARTA:
			// <FACTOR> ->	IDENTIFICADOR ( <FACTOR1> )
			
			String tokenIdentificador4 = nt.getIdentificador();
			Ambiente ambiente4 = SymbolTable.getInstance().getAmbiente(tokenIdentificador4);
			
			if (ambiente4 != null){
				// Bingo! Existe el Proc ó Func.
				
				// Valido que los tipos de dato de los parametros que se llama al procFunc coincidan en tipo, tamaÃ±o y orden.
				
				if (ambiente4.getParametros().size() == nt.getTypeFactorList().size()){
					// Yeah! La cantidad al menos de parametros coinciden... seguimos en camino.
					
					int i = 0;
					boolean hasErrors = false;
					for (Parametro p : ambiente4.getParametros()){
						if (p.getType() != nt.getTypeFactorList().get(i)){
							// Ja! Este parámetro no coincide en tipo!!!
							hasErrors = true;
							logError(nt, "La posicion " + i + " en los parametros del metodo " + tokenIdentificador4 + " no coincide la llamada con lo declarado en SymbolTable. " +
										"Declarado: '" + SymbolTable.getInstance().getVerboseType(p.getType()) + "' y se quiere invocar como '" + SymbolTable.getInstance().getVerboseType(nt.getTypeFactorList().get(i)) + "'",
										"ASEM-FACTOR-4-TYPE_CHECK_001", nt.getEnvName(), nt.getIdentificador());
						}
					}
					
					if (!hasErrors){
						// Groso! Congratulations! El ProcFunc ha pasado con éxito.
						nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(ambiente4.getReturnType().getType()) + " en la produccion <FACTOR> ->	IDENTIFICADOR ( <FACTOR1> )");
						nt.setTypeFactor(ambiente4.getReturnType().getType());
					}
				}
				else{
					// La cantidad de parámetros no coincide.
					logError(nt, "La cantidad de parámetros no coincide en la llamada al método " + tokenIdentificador4,
					"ASEM-FACTOR-4-TYPE_CHECK_002", nt.getEnvName(), nt.getIdentificador());
				}
			}
			else{
				// No se encontró el proc ó Func.
				logError(nt, "No se encontró el proc ó Func en la SymbolTable."+
							"El Metodo es " + tokenIdentificador4,
							"ASEM-FACTOR-4-TYPE_CHECK_003", nt.getEnvName(), nt.getIdentificador());
			}
			
			break;


		case IAnalizadorSintactico.PROD_QUINTA:
			// <FACTOR> -> IDENTIFICADOR [ <EXP> ]
			
			if (nt.getTypeExpresion() == SymbolTable.INTEGER || nt.getTypeExpresion() == SymbolTable.NATURAL){
				// Bien, ahora valido que exista el identificador.
				
				String tokenIdentificador5 = nt.getIdentificador();
				Ambiente ambiente5 = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
				Arreglo arreglo = ambiente5.getArreglo(tokenIdentificador5);
				
				if (arreglo != null){
					nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(arreglo.getType()) + " en la produccion <FACTOR> -> IDENTIFICADOR [ <EXP> ]");
					nt.setTypeFactor(arreglo.getType());
				}
				else{
					logError(nt, "El tipo de dato de <EXP> en la producción <FACTOR> -> IDENTIFICADOR [ <EXP> ] no es Entero o Natural. "+
								"El arreglo '" + tokenIdentificador5 + "' no se encontró en SymbolTable",
								"ASEM-FACTOR-5-TYPE_CHECK_001", nt.getEnvName(), nt.getIdentificador());
				}
				
			}
			else{
				logError(nt, "El tipo de dato de <EXP> en la producción <FACTOR> -> IDENTIFICADOR [ <EXP> ] no es Entero o Natural. "+
							"El tipo de dato es " + SymbolTable.getInstance().getVerboseType(nt.getTypeExpresion()),
							"ASEM-FACTOR-5-TYPE_CHECK_002", nt.getEnvName(), nt.getIdentificador());
			}
			break;
	
		case IAnalizadorSintactico.PROD_SEXTA:
			// <FACTOR> -> IDENTIFICADOR
			
			String tokenIdentificador6 = nt.getIdentificador();
			Ambiente ambiente6 = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
			
			if (ambiente6 != null ){
				// Bien! El Ambiente existe... paso a paso.
				
				// Ahora valido si existe el IDENTIFICADOR declarado en SymbolTable.
				ArrayList<Integer> IdTypes = ambiente6.getTypes(tokenIdentificador6);
				
				if (IdTypes.size() > 1){
					// Upps.. Hay mas de un Identificador cargado con el mismo nombre
					
					logError(nt, "Hay mas de un tipo definido con el nombre del IDENTIFICADOR '" + tokenIdentificador6 + "' definido en la tabla de simbolos." +
								"Tipos encontrados en el ambiente '" + nt.getEnvName()+ "': " + IdTypes.toString(),
								"ASEM-FACTOR-6-TYPE_CHECK_001", nt.getEnvName(), nt.getIdentificador());
				}
				else if (IdTypes.size() == 1){
					// Perfect x1000! 
					nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(IdTypes.get(0)) + " en la producción <FACTOR> -> IDENTIFICADOR");
					nt.setTypeFactor(IdTypes.get(0));
				}
				else{
					// Complicado man! No se encontró el IDENTIFICADOR en la Symbol Table.
					
					logError(nt, "No se encontró el IDENTIFICADOR: '" + tokenIdentificador6 + "' " +
							" en el ambiente: '" + nt.getEnvName() + "'",
							"ASEM-FACTOR-6-TYPE_CHECK_002", nt.getEnvName(), nt.getIdentificador());
				}
			}
			else{
				logError(nt, "El ambiente no fue encontrado en SymbolTable." +
							"No se encuentra '" + nt.getEnvName() + "' definido como ambiente en SymbolTable.",
							"ASEM-FACTOR-6-TYPE_CHECK_001", nt.getEnvName(), nt.getIdentificador());
			}
			
			break;
			
		}
	}
	
	@Override
	protected void visit(Header nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			break;			
		}
	}
	
	@Override
	protected void visit(ProcsFuncs nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			break;			
		}
	}
	
	@Override
	protected void visit(Sentencia nt) {
		
		switch (nt.getNroProduccion()) {		
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
	
		case IAnalizadorSintactico.PROD_PRIMERA:
			//<SENTENCIA> -> IDENTIFICADOR := <EXP>
			
			Ambiente ambiente = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
			ArrayList<Integer> IdTypes = ambiente.getTypes(nt.getIdentificador());
			
			if (ambiente != null){
				
				// Ahora valido si existe el IDENTIFICADOR declarado en SymbolTable.
				if (IdTypes.size() > 1){
					// Upps.. Hay mas de un Identificador cargado con el mismo nombre
					
					logError(nt, "Hay mas de un tipo definido con el nombre del IDENTIFICADOR '" + nt.getIdentificador() + "' definido en la tabla de simbolos." +
								"Tipos encontrados en el ambiente '" + nt.getEnvName() + "': " + IdTypes.toString(),
								"ASEM-SENTENCIA-1-TYPE_CHECK_001", nt.getEnvName());
				}
				else if (IdTypes.size() == 1){
					// Perfect x1000! Ahora lo comparo con <EXP>
					
					// Ahora valido que la asignacion corresponda en tipo de datos.
					if (IdTypes.get(0) != nt.getTypeExpresion1()){
						logError(nt, "Al hacer una asignacion en la producción '<SENTENCIA> -> IDENTIFICADOR := <EXP>', los tipos de dato no coinciden." +
								"El Identificador es del tipo: '" + SymbolTable.getInstance().getVerboseType(IdTypes.get(0)) + "' y <EXP> es: '" + SymbolTable.getInstance().getVerboseType(nt.getTypeExpresion1()) + "'",
								"ASEM-SENTENCIA-1-TYPE_CHECK_002", nt.getEnvName());
					}
					else{
						// No sintetizo mas..
					}
				}
				else{
					// Complicado man! No se encontró el IDENTIFICADOR en la Symbol Table.
					
					logError(nt, "No se encontró el IDENTIFICADOR: '" + nt.getIdentificador() + "' " +
								" en el ambiente: '" + nt.getEnvName() + "' en la produccion <SENTENCIA> -> IDENTIFICADOR := <EXP>",
								"ASEM-SENTENCIA-1-TYPE_CHECK_003", nt.getEnvName());
				}
			}
			else{
				// No se encontró el ambiente en la tabla de simbolos.
				logError(nt, "No se encontró el ambiente '" + nt.getEnvName() + "' en la tabla de simbolos.",
							"ASEM-SENTENCIA-1-TYPE_CHECK_004", nt.getEnvName());
			}
			
			break;
	
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// <SENTENCIA> -> IDENTIFICADOR [ <EXP> ] := <EXP> ;
			
			Ambiente ambiente2 = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
			Arreglo arreglo = ambiente2.getArreglo(nt.getIdentificador());
			
			if (ambiente2 != null){
				if (arreglo != null){
					// Bien! El arreglo está en la tabla de simbolos.
					
					if  (nt.getTypeExpresion1() == SymbolTable.INTEGER || nt.getTypeExpresion1() == SymbolTable.NATURAL){
						// Goood! El indice del arreglo es entero o natural.
						
						if (arreglo.getType() != nt.getTypeExpresion2()){
							// La asignacion al arreglo no coincide en el tipo de dato.
							logError(nt, "Al hacer una asignacion en la producción '<SENTENCIA> -> IDENTIFICADOR [ <EXP> ] := <EXP> ;', los tipos de dato no coinciden." +
									"El arreglo es: '" + SymbolTable.getInstance().getVerboseType(arreglo.getType()) + "' y <EXP2> es: '" + SymbolTable.getInstance().getVerboseType(nt.getTypeExpresion2()) + "'",
									"ASEM-SENTENCIA-2-TYPE_CHECK_001", nt.getEnvName());
						}
					}
					else{
						// El indice del arreglo no es entero o natural :/
						logError(nt, "El tipo de dato del indice del arreglo en la producción '<SENTENCIA> -> IDENTIFICADOR [ <EXP> ] := <EXP> ;' no es Entero o Natural" +
									"<EXP1> es del tipo: '" + nt.getTypeExpresion1() + "'",
									"ASEM-SENTENCIA-2-TYPE_CHECK_002", nt.getEnvName());
					}
				}
				else{
					// No se encontró el arreglo en la tabla de simbolos.
					logError(nt, "No se encontró la variable '" + nt.getIdentificador() + "' en el ambiente '" + nt.getEnvName() + "'",
					"ASEM-SENTENCIA-2-TYPE_CHECK_003", nt.getEnvName());
				}
			}
			else{
				// No se encontró el ambiente en la tabla de simbolos.
				logError(nt, "No se encontró el ambiente '" + nt.getEnvName() + "' en la tabla de simbolos.",
							"ASEM-SENTENCIA-2-TYPE_CHECK_004", nt.getEnvName());
			}
			
			break;
		
		case IAnalizadorSintactico.PROD_TERCERA:
			//<SENTENCIA> -> IDENTIFICADOR ( <FACTOR_1> )     ;
			
			Ambiente ambiente3 = SymbolTable.getInstance().getAmbiente(nt.getIdentificador());
			
			if (ambiente3 != null){
				
				if (ambiente3.getParametros().size() == nt.getTypeFactorList().size()){
					// Yeah! La cantidad al menos de parametros coinciden... seguimos en camino.
					
					int i = 0;
					for (Parametro p : ambiente3.getParametros()){
						//if (nt.getTypeFactorList().get(i) != SymbolTable.NO_SET && p.getType() != nt.getTypeFactorList().get(i)){
						if (p.getType() != nt.getTypeFactorList().get(i)){
							// Ja! Este parámetro no coincide en tipo!!!
							logError(nt, "La posicion " + i + " en los parametros de invocación del metodo '" + nt.getIdentificador() + "' desde el metodo: '" + nt.getEnvName() + "', no coincide la llamada con lo declarado en SymbolTable. " +
									"Declarado: '" + SymbolTable.getInstance().getVerboseType(p.getType()) + "' y se quiere invocar como '" + SymbolTable.getInstance().getVerboseType(nt.getTypeFactorList().get(i)) + "'",
									"ASEM-SENTENCIA-3-TYPE_CHECK_001", nt.getEnvName());
						}
					}
				}
				else{
					// La cantidad de parámetros no coincide.
					logError(nt, "La cantidad de parámetros no coincide en la llamada al método '" + nt.getIdentificador() + "'. Se esperaban: " + ambiente3.getParametros().size() + ", y se llama con: "+ nt.getTypeFactorList() + " desde el metodo" + nt.getEnvName(),
							"ASEM-SENTENCIA-3-TYPE_CHECK_002", nt.getEnvName());
				}
			}
			else{
				// No se encontró el ambiente en la tabla de simbolos.
				logError(nt, "No se encontró el ambiente '" + nt.getEnvName() + "' en la tabla de simbolos.",
						"ASEM-SENTENCIA-3-TYPE_CHECK_003", nt.getEnvName());
			}
			
			break;
			
		case IAnalizadorSintactico.PROD_CUARTA:
			break;
			
		case IAnalizadorSintactico.PROD_QUINTA:
			break;
			
		case IAnalizadorSintactico.PROD_SEXTA:
			break;			
			
		case IAnalizadorSintactico.PROD_SEPTIMA:
			break;
		
		case IAnalizadorSintactico.PROD_OCTAVA:
			break;
		
		case IAnalizadorSintactico.PROD_NOVENA:
			break;
		}

	}
	
	@Override
	protected void visit(ShowElem nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			break;
		}
	}	
	
	@Override
	protected void visit(Termino nt) {
		
		// Hago un DISTINCT de los tipos NT
		LinkedHashSet<Integer> lhsNT = new LinkedHashSet<Integer>();
		lhsNT.addAll(nt.getTypeNTList());
		
		if (lhsNT.size() != 1){
			// Los tipos de dato de los NT producidos por <Termino> son diferentes.
			logError(nt, "Los tipos de dato de los NT producidos por <Termino> son diferentes (" + lhsNT.toString() + ").",
							"ASEM-TERMINO-1-TYPE_CHECK_001", nt.getEnvName());
		}
		else if (lhsNT.size() == 1){
			// Los tipos de dato de los NT son iguales! Bien!
			
			// Analizo si se reconoció algún Token operador
			if (nt.getTypeTokenList().size() > 0){
				// Existe al menos un token operador reconocido.
				
				// Hago un DISTINCT de los tipos de <Token> operador reconocidos.
				LinkedHashSet<Integer> lhsTokens = new LinkedHashSet<Integer>();
				lhsTokens.addAll(nt.getTypeTokenList());
				
				if (lhsTokens.size() != 1 ){
					// Los tipos de dato de los Token operador difieren.
					logError(nt, "Los tipos de dato de los Token operador difieren (" + lhsTokens.toString() + ").",
							"ASEM-TERMINO-1-TYPE_CHECK_002", nt.getEnvName());	
				}
				else{
					// Bien! Seguimos en camino. Los tipos de dato de los Token operador coinciden, ó es único.
					
					// Ahora comparo el tipo de dato de los tokens operador con los de los NT
					if (nt.getTypeNTList().get(0) == SymbolTable.NATURAL && (nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_MULT_NAT || nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_DIV_NAT )){
						// Genial! Los tipo de dato de los tokens operador reconocidos coinciden con los de <Termino> que son NATURALES
						nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(SymbolTable.NATURAL) + " a <TERMINO>");
						nt.setTypeTermino(SymbolTable.NATURAL);
					}
					else if (nt.getTypeNTList().get(0) == SymbolTable.INTEGER && (nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_MULT_INT || nt.getTypeTokenList().get(0) == IToken.TYPE_OPER_DIV_INT )){
						// Genial! Los tipo de dato de los tokens operador reconocidos coinciden con los de <Termino> que son ENTEROS
						nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(SymbolTable.INTEGER) + " a <TERMINO>");
						nt.setTypeTermino(SymbolTable.INTEGER);
					}
					else{
						// Todo mal! El tipo de dato de los Tokens operador reconocidos NO coinciden con los de <Termino>
						logError(nt, 	"El tipo de dato de los Tokens operador reconocidos NO coinciden con los de <Termino>",
								"ASEM-TERMINO-1-TYPE_CHECK_003", nt.getEnvName());
					}
				}
			}
			else{
				// NO existe un token operador reconocido. -> El tipo de dato de <Termino> pasa a ser el de los tipos de los NT
				nt.getErrorManager().debug("Sintetizo el tipo: " + SymbolTable.getInstance().getVerboseType(nt.getTypeNTList().get(0)) + " a <TERMINO>");
				nt.setTypeTermino(nt.getTypeNTList().get(0));
			}
		}
	}
	
	@Override
	protected void visit(Tipo nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// <TIPO> -> INTEGER
			
			//nt.setTypeTipo(SymbolTable.INTEGER);
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// <TIPO> -> NATURAL
			
			//nt.setTypeTipo(SymbolTable.NATURAL);
			break;			
		}
	}	
		
	@Override
	protected void visit(Var nt) {

		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;		
		}	
	}
}
