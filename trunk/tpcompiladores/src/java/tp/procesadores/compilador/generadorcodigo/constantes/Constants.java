package tp.procesadores.compilador.generadorcodigo.constantes;

public class Constants {

	// Variables utiles
	public static final String TAB = "\t";
	public static final String ENTER = "\n";

	public static final String FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO_MAS_TAB = ENTER
			+ ENTER + TAB;
	public static final String FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO = ENTER
			+ ENTER;
	public static final String FIN_DE_LINEA_MAS_TAB = ENTER + TAB;
	public static final String FIN_DE_LINEA = ENTER;
	// Funciones Generales
	private static final String descripcionAsm = ";" + ENTER;
	public static final String jumpprocedimientoPrincipal = "JMP ";
	// Falta nombre del procedimiento,
	// que viene en el codigo

	public static final String encabezado = descripcionAsm + "ORG 0100h"
			+ ENTER + ENTER;
	public static final String writeSTR = "; ***Comienzo rutina writeSTR***"
			+ ENTER
			+ "; Imprime por pantalla un string sin <Enter> al final"
			+ ENTER
			+ "; Parametros:"
			+ ENTER
			+ "; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador)"
			+ ENTER
			+ "; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador)"
			+ ENTER
			+ "writeSTR PROC NEAR"
			+ ENTER
			+ ENTER
			+ TAB
			+ "PUSH	bp"
			+ ENTER
			+ TAB
			+ "MOV		bp, sp"
			+ ENTER
			+ TAB
			+ "PUSH	ax"
			+ ENTER
			+ TAB
			+ "PUSH	bx"
			+ ENTER
			+ TAB
			+ "PUSH	cx"
			+ ENTER
			+ TAB
			+ "PUSH	si; a SI se le asigna el primer parametro (direccion)"
			+ ENTER
			+ TAB
			+ "MOV		si, [bp+6]; a CX se le asigna el segundo parametro (cantidad de caracteres)"
			+ ENTER + TAB + "MOV		cx, [bp+4]" + ENTER + TAB + "xor 	bx,bx"
			+ ENTER + "loop:" + ENTER + ENTER + TAB + "MOV		al, [si]" + ENTER
			+ TAB + "MOV		ah, 0Eh" + ENTER + TAB + "int 	10h" + ENTER + TAB
			+ "inc 	bx" + ENTER + TAB + "inc 	si" + ENTER + TAB + "CMP		bx, cx"
			+ ENTER + TAB + "jne 	loop" + ENTER + TAB + "POP		si" + ENTER + TAB
			+ "POP		cx" + ENTER + TAB + "POP		bx" + ENTER + TAB + "POP		ax"
			+ ENTER + TAB + "POP		bp" + ENTER + TAB + "RET 4" + ENTER
			+ "writeSTR ENDP" + ENTER + ENTER + "; ***Fin rutina writeSTR***"
			+ ENTER + ENTER;
	public static final String writeCRLF = "; ***Comienzo rutina writeCRLF***"
			+ ENTER + "; Imprime por pantalla un caracter <Enter> (<CR><LF>)"
			+ ENTER + "; Parametros: -" + ENTER + "writeCRLF PROC NEAR" + ENTER
			+ ENTER + TAB + "PUSH	AX" + ENTER + TAB + "MOV		al, 0Dh" + ENTER
			+ TAB + "MOV		ah, 0Eh" + ENTER + TAB + "int 10h" + ENTER + TAB
			+ "MOV		al, 0Ah" + ENTER + TAB + "MOV		ah, 0Eh" + ENTER + TAB
			+ "int 10h" + ENTER + TAB + "POP		AX" + ENTER + TAB + "RET" + ENTER
			+ "writeCRLF ENDP" + ENTER + ENTER + "; ***Fin rutina writeCRLF***"
			+ ENTER + ENTER;
	public static final String writeNUM = "; ***Comienzo rutina writeNUM*** "
			+ ENTER
			+ "; Imprime por pantalla un numero (word con signo) sin <Enter> al final "
			+ ENTER
			+ "; Parametros: "
			+ ENTER
			+ "; Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) "
			+ ENTER
			+ "; Parametro entrada 2: numero a imprimir (word/via push del llamador) "
			+ ENTER
			+ "writeNUM PROC NEAR"
			+ ENTER
			+ ENTER
			+ TAB
			+ "PUSH	bp"
			+ ENTER
			+ TAB
			+ "MOV		bp, sp; variable local/ [bp-1] = signo (00h=positivo, 01h=negativo)"
			+ ENTER
			+ TAB
			+ "sub 	sp,1; variable local/ [bp-7] = espacio para imprimir (db 6 dup(?))"
			+ ENTER + TAB + "sub 	sp,6" + ENTER + TAB + "PUSH	ax" + ENTER + TAB
			+ "PUSH	bx" + ENTER + TAB + "PUSH	cx" + ENTER + TAB + "PUSH	dx"
			+ ENTER + TAB + "PUSH	si" + ENTER + TAB + "MOV		[bp-1], 00h"
			+ ENTER + TAB + "MOV		ax, [bp+4]" + ENTER + TAB
			+ "CMP		[bp+6], 0; Si no es aritmetica con signo, comienza" + ENTER
			+ TAB + "je 		comenzar" + ENTER + TAB
			+ "CMP		ax, 0; Si no es negativo, comienza" + ENTER + TAB
			+ "jge 	comenzar; Realiza ax = -ax" + ENTER + TAB + "neg 	ax"
			+ ENTER + TAB + "MOV		[bp-1], 01h" + ENTER + "comenzar:" + ENTER
			+ ENTER + TAB + "MOV		bx, 10 " + ENTER + TAB + "MOV		cx, 0" + ENTER
			+ TAB + "MOV		si, bp" + ENTER + TAB + "sub 	si,8" + ENTER
			+ "proxdiv:" + ENTER + ENTER + TAB + "dec 	si" + ENTER + TAB
			+ "xor 	dx,dx" + ENTER + TAB + "div 	bx" + ENTER + TAB
			+ "add 	dl,48" + ENTER + TAB + "MOV		[si], dl" + ENTER + TAB
			+ "inc 	cx" + ENTER + TAB + "CMP		ax, 0" + ENTER + TAB
			+ "jnz 	proxdiv" + ENTER + TAB + "CMP		[bp-1], 00h" + ENTER + TAB
			+ "jz 		mostrar" + ENTER + TAB + "dec 	si" + ENTER + TAB
			+ "MOV		[si], '-'" + ENTER + TAB + "inc 	cx" + ENTER + "mostrar:"
			+ ENTER + ENTER + TAB + "PUSH	si" + ENTER + TAB + "PUSH	cx" + ENTER
			+ TAB + "CALL	writeSTR" + ENTER + TAB + "POP		si" + ENTER + TAB
			+ "POP		dx" + ENTER + TAB + "POP		cx" + ENTER + TAB + "POP		bx"
			+ ENTER + TAB + "POP		ax" + ENTER + TAB + "MOV		sp, bp" + ENTER
			+ TAB + "POP		bp" + ENTER + TAB + "RET 4" + ENTER + "writeNUM ENDP"
			+ ENTER + ENTER + "; ***Fin rutina writeNUM***" + ENTER;

	public static final String readln = "; ***Comienzo rutina readln***"
			+ ENTER
			+ "; Obtiene por teclado un numero (word con o sin signo)"
			+ ENTER
			+ "; (usa rutina writeSTR, notar que posee dos macros)"
			+ ENTER
			+ "; Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden)"
			+ ENTER
			+ "; No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa"
			+ ENTER
			+ "; Parametros:; Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)"
			+ ENTER
			+ "; Parametro salida: numero obtenido (word/via pop del llamador)"
			+ ENTER
			+ "; (el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi)"
			+ ENTER
			+ "dig macro digbase"
			+ ENTER
			+ TAB
			+ "CMP		al, digbase"
			+ ENTER
			+ TAB
			+ "jl 		inicioread"
			+ ENTER
			+ TAB
			+ "CMP		al, '9'"
			+ ENTER
			+ TAB
			+ "jg 		inicioread"
			+ ENTER
			+ TAB
			+ "MOV		ah, 0Eh"
			+ ENTER
			+ TAB
			+ "int 10h"
			+ ENTER
			+ TAB
			+ "MOV		[bp-1], 03h"
			+ ENTER
			+ TAB
			+ "MOV		cl, al"
			+ ENTER
			+ TAB
			+ "sub 	cl,48"
			+ ENTER
			+ TAB
			+ "MOV		ax, si"
			+ ENTER
			+ TAB
			+ "MOV		bx, 000Ah; AX = AX * 10"
			+ ENTER
			+ TAB
			+ "mul 	bx; AX = AX + CX (digito)"
			+ ENTER
			+ TAB
			+ "add 	ax,cx"
			+ ENTER
			+ TAB
			+ "MOV		si, ax"
			+ ENTER
			+ "endm"
			+ ENTER
			+ "writeBS macro"
			+ ENTER
			+ TAB
			+ "MOV		ah, 0Eh"
			+ ENTER
			+ TAB
			+ "int 10h"
			+ ENTER
			+ TAB
			+ "MOV		al, ' '"
			+ ENTER
			+ TAB
			+ "int 10h"
			+ ENTER
			+ TAB
			+ "MOV		ah, 08h"
			+ ENTER
			+ TAB
			+ "int 10h"
			+ ENTER
			+ "endm"
			+ ENTER
			+ "readln PROC NEAR"
			+ ENTER
			+ ENTER
			+ TAB
			+ "PUSH	bp"
			+ ENTER
			+ TAB
			+ "MOV		bp, sp; [bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos)"
			+ ENTER + TAB
			+ "sub 	sp,1; [bp-2] = signo (00h=positivo, 01h=negativo)" + ENTER
			+ TAB + "sub 	sp,1" + ENTER + TAB + "PUSH	ax" + ENTER + TAB
			+ "PUSH	bx" + ENTER + TAB + "PUSH	cx" + ENTER + TAB + "PUSH	dx"
			+ ENTER + TAB + "PUSH	si" + ENTER + TAB + "MOV		[bp-1], 00h"
			+ ENTER + TAB + "MOV		[bp-2], 00h; valor" + ENTER + TAB
			+ "MOV		si, 0000h" + ENTER + TAB + "MOV		bx, 0" + ENTER + TAB
			+ "MOV		cx, 0" + ENTER + "inicioread:" + ENTER + ENTER + TAB
			+ "MOV		ah, 0" + ENTER + TAB + "int 16h" + ENTER + TAB
			+ "CMP		[bp-1], 00h" + ENTER + TAB + "je 		estado0" + ENTER + TAB
			+ "CMP		[bp-1], 01h" + ENTER + TAB + "je 		estado1" + ENTER + TAB
			+ "CMP		[bp-1], 02h" + ENTER + TAB + "je 		estado2" + ENTER + TAB
			+ "CMP		[bp-1], 03h" + ENTER + TAB + "je 		estado3" + ENTER
			+ "estado0:" + ENTER + ENTER + TAB + "CMP		al, 0Dh" + ENTER + TAB
			+ "je 		inicioread" + ENTER + TAB + "CMP		al, '0'" + ENTER + TAB
			+ "jne 	estado0a" + ENTER + TAB + "MOV		[bp-1], 01h" + ENTER + TAB
			+ "MOV		ah, 0Eh" + ENTER + TAB + "int 10h" + ENTER + TAB
			+ "JMP		inicioread" + ENTER + "estado0a:" + ENTER + ENTER + TAB
			+ "CMP		al, '-'" + ENTER + TAB + "jne 	estado0b" + ENTER + TAB
			+ "CMP		[bp+4], 0000h" + ENTER + TAB + "je 		inicioread" + ENTER
			+ TAB + "MOV		[bp-1], 02h" + ENTER + TAB + "MOV		[bp-2], 01h"
			+ ENTER + TAB + "MOV		ah, 0Eh" + ENTER + TAB + "int 10h" + ENTER
			+ TAB + "JMP		inicioread" + ENTER + "estado0b:" + ENTER + ENTER
			+ TAB + "dig 	'1'" + ENTER + TAB + "JMP		inicioread" + ENTER
			+ "estado1:" + ENTER + ENTER + TAB + "CMP		al, 0Dh" + ENTER + TAB
			+ "je 		finread" + ENTER + TAB + "CMP		al, 08h" + ENTER + TAB
			+ "jne 	inicioread" + ENTER + "writeBS" + ENTER + TAB
			+ "MOV		[bp-1], 00h" + ENTER + TAB + "JMP		inicioread" + ENTER
			+ "estado2:" + ENTER + ENTER + TAB + "CMP		al, 0Dh" + ENTER + TAB
			+ "je 		inicioread" + ENTER + TAB + "CMP		al, 08h" + ENTER + TAB
			+ "jne		estado2a" + ENTER + TAB + "writeBS" + ENTER + TAB
			+ "MOV		[bp-1], 00h" + ENTER + TAB + "MOV		[bp-2], 00h" + ENTER
			+ TAB + "JMP		inicioread" + ENTER + "estado2a:" + ENTER + ENTER
			+ TAB + "dig 	'1'" + ENTER + TAB + "JMP		inicioread" + ENTER
			+ "estado3:" + ENTER + ENTER + TAB + "CMP		al, 0Dh" + ENTER + TAB
			+ "je 		finread" + ENTER + TAB + "CMP		al, 08h" + ENTER + TAB
			+ "jne 	estado3a" + ENTER + TAB + "writeBS" + ENTER + TAB
			+ "MOV		ax, si" + ENTER + TAB + "MOV		dx, 0" + ENTER + TAB
			+ "MOV		bx, 000Ah; AX = AX / 10" + ENTER + TAB + "div 	bx" + ENTER
			+ TAB + "MOV		si, ax" + ENTER + TAB + "CMP		si, 0" + ENTER + TAB
			+ "jne 	inicioread" + ENTER + TAB + "CMP		[bp-2], 00h" + ENTER
			+ TAB + "jne 	estado3bs1" + ENTER + TAB + "MOV		[bp-1], 00h"
			+ ENTER + TAB + "JMP		inicioread" + ENTER + "estado3bs1:" + ENTER
			+ ENTER + TAB + "MOV		[bp-1], 02h" + ENTER + TAB
			+ "JMP		inicioread" + ENTER + "estado3a:" + ENTER + ENTER + TAB
			+ "dig 	'0'" + ENTER + TAB + "JMP		inicioread" + ENTER + "finread:"
			+ ENTER + ENTER + TAB + "CMP		[bp-2], 00h" + ENTER + TAB
			+ "je 		finread2" + ENTER + TAB + "neg 	si" + ENTER + "finread2:"
			+ ENTER + ENTER + TAB + "MOV		[bp+6], si" + ENTER + TAB + "POP		si"
			+ ENTER + TAB + "POP		dx" + ENTER + TAB + "POP		cx" + ENTER + TAB
			+ "POP		bx" + ENTER + TAB + "POP		ax" + ENTER + TAB + "MOV		sp, bp"
			+ ENTER + TAB + "POP		bp" + ENTER + TAB + "RET 2" + ENTER
			+ "readln ENDP" + ENTER + ENTER + "; ***Fin rutina readln***"
			+ ENTER;
	public static final String VARIABLES_SIN_VALOR = " dw ";
	public static final String finprocedimiento = ENTER + TAB + "int 21h";

}