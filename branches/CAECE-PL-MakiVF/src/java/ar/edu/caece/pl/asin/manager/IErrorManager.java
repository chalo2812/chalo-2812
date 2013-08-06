package ar.edu.caece.pl.asin.manager;

import java.util.Map;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.asin.model.ITokenStream;

public interface IErrorManager {
	
	public void error(String message, IToken faultyToken);
	public void error(String codeError, String message);
	public void error(String codeError, String message, String env);
	public void error(String codeError, String message, String env, String method);
	public void error(IToken faultyToken);
	
	public void debug(String message, IToken debuggingToken);
	public void debug(String message);
	public void debug(IToken debuggingToken);
	
	public void recover(Class<? extends IAnalizadorSintactico> noTerminalClass, ITokenStream tokenStream);
	
	/**
	 * Devuelve un mapa con los errores semanticos. Key es el codigo de error y Value es la descripcion
	 * @return Map<String,String>
	 */
	public Map<String, String> getErrors();
	public void cleanAll();
	
}
