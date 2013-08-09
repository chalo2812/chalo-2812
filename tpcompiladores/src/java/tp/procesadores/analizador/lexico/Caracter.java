package tp.procesadores.analizador.lexico;

/**
 * Esta clase guarda el simbolo que se esta tratando y nos da cierta informacion sobre el 
 */
public class Caracter{
	
	private int simbolo = -1;

	/**
	 * @return 	Devuelve el simbolo (int) que contiene el objeto Caracter 
	 */
	public int getSimbolo() {
		return simbolo;
	}

	/**
	 * Setter para el simbolo que se guardara en el objeto Caracter 
	 * @param caracter	simbolo a ser guardado 
	 */
	
	public void setSimbolo(int caracter) {
		this.simbolo = caracter;
	}
	
	/**
	 * Esta funcion devuelve true si el simbolo en este objeto se considera ignorable 
	 * @return	true si es ignorable 
	 */
	public boolean esIgnorable(){
		boolean result = false;  
		if((this.getSimbolo() == 32) || (this.getSimbolo() == '\n') || (this.getSimbolo() == '\t')
				|| (this.getSimbolo() == '{')){ 
			result = true;
		}
		return result;
	}
	
	/**
	 * Esta funcion devuelve un int que se corresponde a un tipo de lexema a tratar
	 * 
	 *  @return 	1=EsNumero 	2=EsCadena	3=EsOperador	4=EsEOF	5=EsIdentificador 
	 */
	public int getTipo(){
		int tipo=-1; 
		
		if (Character.isDigit(this.getSimbolo())){ 
				tipo=1; 							//EsNumero = 1
			}
		if (this.simbolo == '\''){
				tipo=2; 							//EsCadena = 2
			}
		if ( esOperador(this.simbolo))
				tipo=3; 							//EsOperador =3 
		if( this.simbolo == -1)
				tipo= 4; 							//EsEOF = 4 
		if( Character.isLetter(this.simbolo) )
				tipo=5; 							//EsIdentificador = 5   [Reservadas  y Variables] 
		return tipo; 
	}

	
	/**
	 * Esta funcion devuelve true si el simbolo en este objeto es una llave {, lo cual marca el 
	 * principio de un comentario en el codigo. 
	 * 
	 * @return	true si el simbolo es una llave { 
	 */
	
	public boolean esPpioComentario() {
		boolean result=false; 
		if(this.simbolo == '{')
			result = true; 
		return result;
	}
	
	
	/**
	 * Este funcion privada se utiliza para ver si el caracter que se encuentra en el objeto caracter 
	 * es o forma parte de un Operador 
	 * 
	 * @param s	simbolo 
	 * @return	true si se considera operador 
	 */
	private static boolean esOperador( int s){
		boolean result=false; 
		if (( s == '=') || ( s == '+') || ( s == '*') || ( s == '-') || ( s == '<') || ( s == '!') ||
		( s == '/') || ( s == ',') || ( s == ';') || ( s == ':') || ( s == '[') || ( s == ']') || ( s == '(') || ( s == ')') ){
			result = true;
		}
		return result; 
	} 
}
