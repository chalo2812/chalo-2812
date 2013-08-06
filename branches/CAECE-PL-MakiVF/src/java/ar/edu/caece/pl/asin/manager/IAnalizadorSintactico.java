package ar.edu.caece.pl.asin.manager;

public interface IAnalizadorSintactico {

	public static final int PROD_ENCABEZADO = 0;
	public static final int PROD_PRIMERA	= 1;
	public static final int PROD_SEGUNDA	= 2;
	public static final int PROD_TERCERA	= 3;
	public static final int PROD_CUARTA		= 4;
	public static final int PROD_QUINTA		= 5;
	public static final int PROD_SEXTA		= 6;
	public static final int PROD_SEPTIMA	= 7;
	public static final int PROD_OCTAVA		= 8;
	public static final int PROD_NOVENA		= 9;
	public static final int PROD_DECIMA		= 10;
	
	public boolean reconocer();
	public boolean validado();

	public IErrorManager getErrorManager();

}
