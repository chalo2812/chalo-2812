package tp.procesadores.compilador.generadorcodigo.constantes;

public class Constants {

	//Variables utiles
	public static final String TAB = "\t";
	public static final String ENTER = "\n";
	
	//Funciones Generales
	public static final String encabezado = "ORG 0100h" + ENTER + ENTER;
	public static final String writeSTR = "; ***Comienzo rutina writeSTR***"
			+ ENTER + "; Imprime por pantalla un string sin <Enter> al final"
			+ ENTER + "; Parametros:"
			+ ENTER + "; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador)"
			+ ENTER + "; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador)"
			+ ENTER + "writeSTR PROC NEAR"
			+ ENTER + ENTER + "PUSH	bp"
			+ ENTER + "MOV	bp, sp"
			+ ENTER + "PUSH	ax"
			+ ENTER + "PUSH	bx"
			+ ENTER + "PUSH	cx"
			+ ENTER + "PUSH	si" + ENTER +  "; a SI se le asigna el primer parametro (direccion)"
			+ ENTER + "MOV	si, [bp+6]" + ENTER + "; a CX se le asigna el segundo parametro (cantidad de caracteres)"
			+ ENTER + "MOV	cx, [bp+4]"  
			+ ENTER + "xor bx,bx" 
			+ ENTER + "loop:" + ENTER  
			+ ENTER + "MOV	al, [si]"
			+ ENTER + "MOV	ah, 0Eh" 
			+ ENTER + "int 10h" 
			+ ENTER + "inc bx" 
			+ ENTER + "inc si" 
			+ ENTER + "CMP	bx, cx"
			+ ENTER + "jne loop"  
			+ ENTER + "POP	si"  
			+ ENTER + "POP	cx"  
			+ ENTER + "POP	bx"  
			+ ENTER + "POP	ax"  
			+ ENTER + "POP	bp"
			+ ENTER + "RET 4" 
			+ ENTER + "writeSTR ENDP" + ENTER  
			+ ENTER + "; ***Fin rutina writeSTR***";
	
	
	
}
