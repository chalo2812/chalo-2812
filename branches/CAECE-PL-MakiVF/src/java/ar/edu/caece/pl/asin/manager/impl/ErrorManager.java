package ar.edu.caece.pl.asin.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.alex.model.impl.Token;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.IErrorManager;
import ar.edu.caece.pl.asin.model.ITokenStream;
import ar.edu.caece.pl.asin.model.impl.BloqueVarParam;
import ar.edu.caece.pl.asin.model.impl.Expresion;
import ar.edu.caece.pl.asin.model.impl.Factor;
import ar.edu.caece.pl.asin.model.impl.Header;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;
import ar.edu.caece.pl.asin.model.impl.Sentencia;
import ar.edu.caece.pl.asin.model.impl.ShowElem;
import ar.edu.caece.pl.asin.model.impl.Termino;
import ar.edu.caece.pl.asin.model.impl.Tipo;
import ar.edu.caece.pl.asin.model.impl.TipoC;
import ar.edu.caece.pl.asin.model.impl.Var;

public class ErrorManager implements IErrorManager {

	private static ErrorManager instance = new ErrorManager();
	public static boolean debugEnabled = false;
	private static final Map<Class<? extends IAnalizadorSintactico>, List<IToken>> siguientesMap = new HashMap<Class<? extends IAnalizadorSintactico>, List<IToken>>();
	private static Map<String, String> mapErrors = new HashMap<String, String>();

	static {
		
//		mapErrors.put("TYPE", "Error de validacion de tipos");
		
		List <IToken> siguientes = new ArrayList<IToken>();
		
		//PROGRAMA
		siguientes.add(new Token(IToken.TYPE_FIN_DE_ARCHIVO));
		siguientesMap.put(AnalizadorSintactico.class, siguientes);
		siguientes.clear();
		
		//HEADER
		//S(HEADER) = { PROCEDURE, FUNCTION, CONST, VAR, BEGIN, EOF }
		siguientes.add(new Token(IToken.TYPE_FIN_DE_ARCHIVO));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_PROCEDURE));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_FUNCTION));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_BEGIN));
		siguientesMap.put(Header.class, siguientes);
		siguientes.clear();
		
		//TIPO_C
		//S(TIPO_C) = { CONST, VAR, NATURAL, INTEGER, ; }
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_NATURAL));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_INTEGER));
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientesMap.put(TipoC.class, siguientes);
		siguientes.clear();
		
//		//TIPO_C1
//		//S(TIPO_C1) = { CONST, VAR, NATURAL, INTEGER, ; }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_NATURAL));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_INTEGER));
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientesMap.put(TipoC1.class, siguientes);
//		siguientes.clear();
//		
		//VAR
		//S(VAR) = { CONST, VAR, IDENTIFICADOR }
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
		siguientes.add(new Token(IToken.TYPE_IDENTIFICADOR));
		siguientesMap.put(Var.class, siguientes);
		siguientes.clear();
		
//		//VAR_1
//		//S(VAR_1) = { CONST, VAR }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
//		siguientes.add(new Token(IToken.TYPE_IDENTIFICADOR));
//		siguientesMap.put(Var1.class, siguientes);
//		siguientes.clear();
		
//		//VAR_2
//		//S(VAR_2) = { CONST, VAR }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
//		siguientesMap.put(Var2.class, siguientes);
//		siguientes.clear();
		
//		//VAR_3
//		//S(VAR_3) = { CONST, VAR }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_CONST));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_VAR));
//		siguientesMap.put(Var3.class, siguientes);
//		siguientes.clear();
		
		//TIPO
		//S(TIPO) = { ";" , ",", ), BYREF, BYVAL, IDENTIFICADOR}
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientes.add(new Token(IToken.TYPE_COMA));
		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_BYREF));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_BYVAL));
		siguientes.add(new Token(IToken.TYPE_IDENTIFICADOR));
		siguientesMap.put(Tipo.class, siguientes);
		siguientes.clear();
		
		//PROCS_FUNCS
		//S(PROCS_FUNCS) = { EOF, PROCEDURE, FUNCTION }
		siguientes.add(new Token(IToken.TYPE_FIN_DE_ARCHIVO));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_PROCEDURE));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_FUNCTION));
		siguientesMap.put(ProcsFuncs.class, siguientes);
		siguientes.clear();
		
		//BLOQUE_VAR_PARAM
		//S(BLOQUE_VAR_PARAM) = { ), BYREF, BYVAL, IDENTIFICADOR}
		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_BYREF));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_BYVAL));
		siguientes.add(new Token(IToken.TYPE_IDENTIFICADOR));
		siguientesMap.put(BloqueVarParam.class, siguientes);
		siguientes.clear();
		
		//BLOQUE_VAR_PARAM_1
//		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientesMap.put(BloqueVarParam1.class, siguientes);
//		siguientes.clear();
		
		//SENTENCIA
		//S(SENTENCIA) = { ";" }
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientesMap.put(Sentencia.class, siguientes);
		siguientes.clear();
		
		//SENTENCIA_1
		//S(SENTENCIA_1) = { ";" }
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientesMap.put(Sentencia1.class, siguientes);
//		siguientes.clear();
		
		//SHOW_ELEM
		//S(SHOW_ELEM) = { ";", ","  }
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientes.add(new Token(IToken.TYPE_COMA));
		siguientesMap.put(ShowElem.class, siguientes);
		siguientes.clear();
		
		//SHOW_ELEM_NEXT
		//S(SHOW_ELEM_NEXT) = { ";", ","  }
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientes.add(new Token(IToken.TYPE_COMA));
//		siguientesMap.put(ShowElementNext.class, siguientes);
//		siguientes.clear();
		
//		//CALLING_PARAMS
//		//S(CALLING_PARAMS) = { ), ","  }
//		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
//		siguientes.add(new Token(IToken.TYPE_COMA));
//		siguientesMap.put(CallingParams.class, siguientes);
//		siguientes.clear();
//		
		//ELSE
		//S(ELSE) = { ENDIF }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_END_IF));
//		siguientesMap.put(Else.class, siguientes);
//		siguientes.clear();
//		
		
		//EXP 
		//S(EXP) = { ";" , ], ",", ), >, =, >=, <=, <, <>, THEN, DO }
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_THEN));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_DO));
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
		siguientes.add(new Token(IToken.TYPE_COMA));
		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOR));
		siguientes.add(new Token(IToken.TYPE_OPER_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOR_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_DISTINTO));
		siguientesMap.put(Expresion.class, siguientes);
		siguientes.clear();
		
		//C1
		//S(C1) = { TONATURAL, TOINTEGER, ( , IDENTIFICADOR, NUMERO, NUMERO_NAT }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_TONATURAL));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_TOINTEGER));
//		siguientes.add(new Token(IToken.TYPE_PARENTESIS_IZQ));
//		siguientes.add(new Token(IToken.TYPE_IDENTIFICADOR));
//		siguientes.add(new Token(IToken.TYPE_NUM_ENTERO));
//		siguientes.add(new Token(IToken.TYPE_NUM_NATURAL));
//		siguientesMap.put(C1.class, siguientes);
//		siguientes.clear();
		
		//TERMINO
		//S(TERMINO) = { ++, +, -, --, ";" , ], ",", ), >, =, >=, <=, <, <>, THEN, DO  }
		
		//fALTA ++ --		
		siguientes.add(new Token(IToken.TYPE_OPER_MAS_INT));
		siguientes.add(new Token(IToken.TYPE_OPER_MAS_NAT));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOS_NAT));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOS_INT));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_THEN));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_DO));
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
		siguientes.add(new Token(IToken.TYPE_COMA));
		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOR));
		siguientes.add(new Token(IToken.TYPE_OPER_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOR_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_DISTINTO));
		siguientesMap.put(Termino.class, siguientes);
		siguientes.clear();
		
//		//TERMINO1
//		//S(TERMINO1) = { ++, +, -, --, ";" , ], ",", ), >, =, >=, <=, <, <>, THEN, DO  }
//		
//		//fALTA ++ --	
//		siguientes.add(new Token(IToken.TYPE_OPER_MAS_INT));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAS_NAT));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOS_NAT));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOS_INT));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_THEN));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_DO));
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
//		siguientes.add(new Token(IToken.TYPE_COMA));
//		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOR));
//		siguientes.add(new Token(IToken.TYPE_OPER_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOR_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_DISTINTO));
//		siguientesMap.put(Termino1.class, siguientes);
//		siguientes.clear();
//		
//		//T1
//		//S(T1) = { ++, +, -, --, ";" , ], ",", ), >, =, >=, <=, <, <>, THEN, DO  }
//		
//		//fALTA ++ --		
//		siguientes.add(new Token(IToken.TYPE_OPER_MAS_INT));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAS_NAT));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOS_NAT));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOS_INT));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_THEN));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_DO));
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
//		siguientes.add(new Token(IToken.TYPE_COMA));
//		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOR));
//		siguientes.add(new Token(IToken.TYPE_OPER_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOR_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_DISTINTO));
//		siguientes.add(new Token(IToken.TYPE_FIN_DE_ARCHIVO));
//		siguientesMap.put(T1.class, siguientes);
//		siguientes.clear();
//		
//		//E1
//		//S(E1) = { ";" , ], ",", ), >, =, >=, <=, <, <>, THEN, DO }
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_THEN));
//		siguientes.add(new Token(IToken.PALABRA_RESERVADA_DO));
//		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
//		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
//		siguientes.add(new Token(IToken.TYPE_COMA));
//		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOR));
//		siguientes.add(new Token(IToken.TYPE_OPER_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_MENOR_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR_IGUAL));
//		siguientes.add(new Token(IToken.TYPE_OPER_DISTINTO));
//		siguientesMap.put(E1.class, siguientes);
//		siguientes.clear();
		
		
		
		//FACTOR
		//S(FACTOR) = { *, /, **, //, ++, +, -, --, ";" , ], ",", ), >, =, >=, <=, <, <>, THEN, DO, TONATURAL, TOINTEGER, ( , IDENTIFICADOR, NUMERO, NUMERO_NAT  }
		//fALTA ++ -- // **
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_THEN));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_DO));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_TOINTEGER));
		siguientes.add(new Token(IToken.PALABRA_RESERVADA_TONATURAL));
		siguientes.add(new Token(IToken.TYPE_IDENTIFICADOR));
		siguientes.add(new Token(IToken.TYPE_NUM_ENTERO));
		siguientes.add(new Token(IToken.TYPE_NUM_NATURAL));	
		siguientes.add(new Token(IToken.TYPE_PUNTO_COMA));
		siguientes.add(new Token(IToken.TYPE_OPER_MULT_INT));
		siguientes.add(new Token(IToken.TYPE_OPER_MULT_NAT));
		siguientes.add(new Token(IToken.TYPE_OPER_DIV_INT));
		siguientes.add(new Token(IToken.TYPE_OPER_DIV_NAT));
		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
		siguientes.add(new Token(IToken.TYPE_COMA));
		siguientes.add(new Token(IToken.TYPE_PARENTESIS_DER));
		siguientes.add(new Token(IToken.TYPE_PARENTESIS_IZQ));
		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOR));
		siguientes.add(new Token(IToken.TYPE_OPER_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_MENOR_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_MAYOR_IGUAL));
		siguientes.add(new Token(IToken.TYPE_OPER_DISTINTO));
		siguientesMap.put(Factor.class, siguientes);
		siguientes.clear();
		
//		//FACTOR1
//		//S(FACTOR1) = { ) }
//		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
//		siguientesMap.put(Factor1.class, siguientes);
//		siguientes.clear();
//		
//		//FACTOR2
//		//S(FACTOR2) = { ) }
//		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
//		siguientesMap.put(Factor2.class, siguientes);
//		siguientes.clear();
		
//		//CALLINGPARAMS
//		//S(CALLINGPARAMS) = { ), ], ","}
//		siguientes.add(new Token(IToken.TYPE_CORCHETE_DER));
//		siguientes.add(new Token(IToken.TYPE_COMA));
//		siguientesMap.put(CallingParams.class, siguientes);
//		siguientes.clear();
		
	}
	
	private ErrorManager() {
	} // Singleton

	public static IErrorManager getInstance(boolean debug) {

		instance.setDebugMode(debug);
		return instance;

	}

	public void setDebugMode(boolean status) {

		this.debugEnabled = status;
	}

	public void error(String message, IToken faultyToken) {
		this.consoleLog(message, faultyToken, false);
	}

	public void debug(String message, IToken debuggingToken) {
		if (this.debugEnabled) {
			this.consoleLog(message, debuggingToken, true);
		}
	}
	
	public void error(IToken faultyToken) {
		this.consoleLog(null, faultyToken, false);
	}

	public void debug(String message) {
		if (this.debugEnabled) {
			this.consoleLog(message, null, true);
		}
	}

	public void debug(IToken debuggingToken) {
		if (this.debugEnabled) {
			this.consoleLog(null, debuggingToken, true);
		}
	}

	/**
	 * Imprime por consola
	 * 
	 * @param message, Mensaje particular
	 * @param token, El token actual
	 * @param mode (sysout=true / syserr=false)
	 */
	private void consoleLog(String message, IToken token, boolean mode) {

		if (message == null && token == null) {
			return;
		}
		StringBuilder sb = new StringBuilder();

		if (message != null) {
			sb.append(message).append("\n");
		}

		if (token != null) {
			sb.append(token.toString());
		}

		if (mode) {
			System.out.println(sb.toString());
		} else {
			System.err.println(sb.toString());
		}

	}

	public void recover(Class<? extends IAnalizadorSintactico> noTerminalClass,
			ITokenStream tokenStream) {

		boolean encontrado = false;

		// El siguiente no era lo que buscaba, asi que avanzo hasta encontrar un siguiente a la produccion

		do {
			tokenStream.leer();

			// Agrego info de debug del token leido
			this.debug(tokenStream.getTokenSiguiente());

			// Busco en los siguientes
			for (IToken t : siguientesMap.get(noTerminalClass)) {
				if (t.equals(tokenStream.getTokenSiguiente())) {
					encontrado = true;
					break; // break del for()
				}
			}

		} while (!encontrado && !tokenStream.getTokenActual().equals(new Token(IToken.TYPE_FIN_DE_ARCHIVO)));
	}

	public Map<String, String> getErrors() {
		return mapErrors;
	}

	@Override
	public void error(String codeError, String message) {
		this.consoleLog(message + " ( Código de error: " +  codeError + " )", null, false);
		mapErrors.put(codeError, message);
	}
	
	@Override
	public void error(String codeError, String message, String env, String method) {
		this.consoleLog(message + " ( Código de error: " +  codeError + " ) + [Env: '"+ env +"', Method: '" + method+"']", null, false);
		mapErrors.put(codeError, message);
	}
	
	@Override
	public void error(String codeError, String message, String env) {
		this.consoleLog(message + " ( Código de error: " +  codeError + " ) + [Env: '"+ env +"']", null, false);
		mapErrors.put(codeError, message);
	}

	@Override
	public void cleanAll() {
		mapErrors.clear();
		instance = new ErrorManager();
	}
}