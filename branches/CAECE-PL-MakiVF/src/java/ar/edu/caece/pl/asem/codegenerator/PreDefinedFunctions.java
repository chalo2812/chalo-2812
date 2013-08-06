package ar.edu.caece.pl.asem.codegenerator;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class PreDefinedFunctions {
	
	private static final String TAB = "\t";
	private static final String ENTER = "\n";
	
	
	public final static StringBuilder sb = new StringBuilder(); 
	static {
		sb.append("; ***Comienzo rutina writeCRLF*** " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Imprime por pantalla un caracter <Enter> (<CR><LF>) " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Parametros: - " + ENTER);
		sb.append("; " + ENTER);
		sb.append("writeCRLF proc near " + ENTER);
		sb.append(TAB + "push ax " + ENTER);
		    
		sb.append(TAB + "mov al, 0Dh " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append(TAB + "mov al, 0Ah " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append(TAB + "pop ax " + ENTER);
		sb.append(TAB + "ret " + ENTER);
		sb.append("writeCRLF endp " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; ***Fin rutina writeCRLF*** " + ENTER);
		sb.append("; ***Comienzo rutina writeSTR*** " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Imprime por pantalla un string sin <Enter> al final " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador) " + ENTER);
		sb.append("; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador) " + ENTER);
		sb.append("; " + ENTER);
		sb.append("writeSTR proc near " + ENTER);
		sb.append(TAB + "push bp " + ENTER);
		sb.append(TAB + "mov bp, sp " + ENTER);
		sb.append(TAB + "push ax " + ENTER);
		sb.append(TAB + "push bx " + ENTER);
		sb.append(TAB + "push cx " + ENTER);
		sb.append(TAB + "push si " + ENTER);
		sb.append(TAB + "mov si, [bp+6]  ; a SI se le asigna el primer parametro (direccion) " + ENTER);
		sb.append(TAB + "mov cx, [bp+4]  ; a CX se le asigna el segundo parametro (cantidad de caracteres) " + ENTER);
		sb.append(TAB + "xor bx, bx " + ENTER);	    
		sb.append("loop: " + ENTER);    
		sb.append(TAB + "mov al, [si] " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append(TAB + "inc bx " + ENTER);
		sb.append(TAB + "inc si " + ENTER);
		sb.append(TAB + "cmp bx, cx " + ENTER);
		sb.append(TAB + "jne loop " + ENTER);
		sb.append(TAB + "pop si " + ENTER);
		sb.append(TAB + "pop cx " + ENTER);
		sb.append(TAB + "pop bx " + ENTER);
		sb.append(TAB + "pop ax " + ENTER);
		sb.append(TAB + "pop bp " + ENTER);
		sb.append(TAB + "ret 4 " + ENTER);
		sb.append("writeSTR endp " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; ***Fin rutina writeSTR*** " + ENTER);
	
	
	
		sb.append("; ***Comienzo rutina writeNUM*** " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Imprime por pantalla un numero (word con signo) sin <Enter> al final " + ENTER);
		sb.append("; (usa rutina writeSTR) " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) " + ENTER);
		sb.append("; Parametro entrada 2: numero a imprimir (word/via push del llamador) " + ENTER);
		sb.append("; " + ENTER);
		sb.append("writeNUM proc near " + ENTER);
		sb.append(TAB + "push bp " + ENTER);
		sb.append(TAB + "mov bp, sp " + ENTER);
		sb.append(TAB + "sub sp, 1        ; \"variable local\" [bp-1] = signo (00h=positivo, 01h=negativo) " + ENTER);
		sb.append(TAB + "sub sp, 6        ; \"variable local\" [bp-7] = espacio para imprimir (db 6 dup(?)) " + ENTER);
	
		sb.append(TAB + "push ax " + ENTER);
		sb.append(TAB + "push bx " + ENTER);
		sb.append(TAB + "push cx " + ENTER);
		sb.append(TAB + "push dx " + ENTER);
		sb.append(TAB + "push si " + ENTER);
	
		sb.append(TAB + "mov [bp-1], 00h " + ENTER);
		sb.append(TAB + "mov ax, [bp+4] " + ENTER);
	
		sb.append(TAB + "cmp [bp+6], 0 " + ENTER);
		sb.append(TAB + "je comenzar      ; Si no es aritmetica con signo, comienza " + ENTER);
		sb.append(TAB + "cmp ax, 0 " + ENTER);
		sb.append(TAB + "jge comenzar     ; Si no es negativo, comienza " + ENTER);
		sb.append(TAB + "neg ax           ; Realiza ax = -ax " + ENTER);
		sb.append(TAB + "mov [bp-1], 01h " + ENTER);
	
		sb.append("comenzar: " + ENTER);
		sb.append(TAB + "mov bx, 10 " + ENTER);
		sb.append(TAB + "mov cx, 0 " + ENTER);
		sb.append(TAB + "mov si, bp " + ENTER);
		sb.append(TAB + "sub si, 8 " + ENTER);
	
		sb.append("proxdiv: " + ENTER);
		sb.append(TAB + "dec si " + ENTER);
		sb.append(TAB + "xor dx, dx " + ENTER);    
		sb.append(TAB + "div bx " + ENTER);
		sb.append(TAB + "add dl, 48 " + ENTER);
		sb.append(TAB + "mov [si], dl " + ENTER);
		sb.append(TAB + "inc cx " + ENTER);
		sb.append(TAB + "cmp ax, 0 " + ENTER);
		sb.append(TAB + "jnz proxdiv " + ENTER);
		    
		sb.append(TAB + "cmp [bp-1], 00h " + ENTER);
		sb.append(TAB + "jz mostrar " + ENTER);
		    
		sb.append(TAB + "dec si " + ENTER);
		sb.append(TAB + "mov [si], '-' " + ENTER);
		sb.append(TAB + "inc cx " + ENTER);
	
		sb.append("mostrar: " + ENTER);    
		sb.append(TAB + "push si " + ENTER);
		sb.append(TAB + "push cx " + ENTER);
		sb.append(TAB + "call writeSTR " + ENTER);
	
		sb.append(TAB + "pop si " + ENTER);
		sb.append(TAB + "pop dx " + ENTER);
		sb.append(TAB + "pop cx " + ENTER);
		sb.append(TAB + "pop bx " + ENTER);
		sb.append(TAB + "pop ax " + ENTER);
	
		sb.append(TAB + "mov sp, bp " + ENTER);   
		sb.append(TAB + "pop bp " + ENTER);
		sb.append(TAB + "ret 4 " + ENTER);
		sb.append("writeNUM endp " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; ***Fin rutina writeNUM*** " + ENTER);
	
	
	
		sb.append("; ***Comienzo rutina readln*** " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Obtiene por teclado un numero (word con o sin signo) " + ENTER);
		sb.append("; (usa rutina writeSTR, notar que posee dos macros) " + ENTER);
		sb.append("; Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden) " + ENTER);
		sb.append("; No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) " + ENTER);
		sb.append("; Parametro salida: numero obtenido (word/via pop del llamador) " + ENTER);
		sb.append("; (el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi) " + ENTER);
		sb.append("; " + ENTER);
		sb.append("dig macro digbase " + ENTER);
		sb.append(TAB + "cmp al, digbase " + ENTER);
		sb.append(TAB + "jl inicioread " + ENTER);
		sb.append(TAB + "cmp al, '9' " + ENTER);
		sb.append(TAB + "jg inicioread " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append(TAB + "mov [bp-1], 03h " + ENTER);
		sb.append(TAB + "mov cl, al " + ENTER);
		sb.append(TAB + "sub cl, 48 " + ENTER);
		sb.append(TAB + "mov ax, si " + ENTER);
		sb.append(TAB + "mov bx, 000Ah " + ENTER);
		sb.append(TAB + "mul bx           ; AX = AX * 10 " + ENTER);
		sb.append(TAB + "add ax, cx       ; AX = AX + CX (digito) " + ENTER);
		sb.append(TAB + "mov si, ax " + ENTER);
		sb.append("endm " + ENTER);
		sb.append("writeBS macro " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);
		sb.append(TAB + "mov al, ' ' " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);
		sb.append(TAB + "mov al, 08h " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append("endm " + ENTER);
		sb.append("readln proc near " + ENTER);
		sb.append(TAB + "push bp " + ENTER);
		sb.append(TAB + "mov bp, sp " + ENTER);
		sb.append(TAB + "sub sp, 1        ; [bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos) " + ENTER);
		sb.append(TAB + "sub sp, 1        ; [bp-2] = signo (00h=positivo, 01h=negativo) " + ENTER);
	
		sb.append(TAB + "push ax " + ENTER);
		sb.append(TAB + "push bx " + ENTER);
		sb.append(TAB + "push cx " + ENTER);
		sb.append(TAB + "push dx " + ENTER);
		sb.append(TAB + "push si " + ENTER);
		sb.append(TAB + "mov [bp-1], 00h " + ENTER);
		sb.append(TAB + "mov [bp-2], 00h " + ENTER);
		sb.append(TAB + "mov si, 0000h    ; valor " + ENTER);
		sb.append(TAB + "mov bx, 0 " + ENTER);
		sb.append(TAB + "mov cx, 0 " + ENTER);
	
		sb.append("inicioread: " + ENTER);
		sb.append(TAB + "mov ah, 0 " + ENTER);
		sb.append(TAB + "int 16h " + ENTER);
		    
		sb.append(TAB + "cmp [bp-1], 00h " + ENTER);
		sb.append(TAB + "je estado0 " + ENTER);
		sb.append(TAB + "cmp [bp-1], 01h " + ENTER);
		sb.append(TAB + "je estado1 " + ENTER);
		sb.append(TAB + "cmp [bp-1], 02h " + ENTER);
		sb.append(TAB + "je estado2 " + ENTER);
		sb.append(TAB + "cmp [bp-1], 03h " + ENTER);
		sb.append(TAB + "je estado3 " + ENTER);
		    
		sb.append("estado0: " + ENTER);    
		sb.append(TAB + "cmp al, 0Dh " + ENTER);
		sb.append(TAB + "je inicioread " + ENTER);
		sb.append(TAB + "cmp al, '0' " + ENTER);
		sb.append(TAB + "jne estado0a " + ENTER);
		sb.append(TAB + "mov [bp-1], 01h " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append(TAB + "jmp inicioread " + ENTER);
		sb.append("estado0a: " + ENTER);
		sb.append(TAB + "cmp al, '-' " + ENTER);
		sb.append(TAB + "jne estado0b " + ENTER);
		sb.append(TAB + "cmp [bp+4], 0000h " + ENTER);
		sb.append(TAB + "je inicioread " + ENTER);    
		sb.append(TAB + "mov [bp-1], 02h " + ENTER);
		sb.append(TAB + "mov [bp-2], 01h " + ENTER);
		sb.append(TAB + "mov ah, 0Eh " + ENTER);
		sb.append(TAB + "int 10h " + ENTER);     
		sb.append(TAB + "jmp inicioread " + ENTER);
		sb.append("estado0b: " + ENTER);
		sb.append(TAB + "dig '1' " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
	
		sb.append("estado1: " + ENTER);    
		sb.append(TAB + "cmp al, 0Dh " + ENTER);
		sb.append(TAB + "je finread " + ENTER);
		sb.append(TAB + "cmp al, 08h " + ENTER);
		sb.append(TAB + "jne inicioread " + ENTER);
		sb.append(TAB + "writeBS " + ENTER);
		sb.append(TAB + "mov [bp-1], 00h " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
	
		sb.append("estado2: " + ENTER);
		sb.append(TAB + "cmp al, 0Dh " + ENTER);
		sb.append(TAB + "je inicioread " + ENTER);
		sb.append(TAB + "cmp al, 08h " + ENTER);
		sb.append(TAB + "jne estado2a " + ENTER);
		sb.append(TAB + "writeBS " + ENTER);
		sb.append(TAB + "mov [bp-1], 00h " + ENTER);
		sb.append(TAB + "mov [bp-2], 00h " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
		sb.append("estado2a: " + ENTER);    
		sb.append(TAB + "dig '1' " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
	
		sb.append("estado3: " + ENTER);    
		sb.append(TAB + "cmp al, 0Dh " + ENTER);
		sb.append(TAB + "je finread " + ENTER);
		sb.append(TAB + "cmp al, 08h " + ENTER);
		sb.append(TAB + "jne estado3a " + ENTER);
		sb.append(TAB + "writeBS " + ENTER);
		sb.append(TAB + "mov ax, si " + ENTER);
		sb.append(TAB + "mov dx, 0 " + ENTER);
		sb.append(TAB + "mov bx, 000Ah " + ENTER);
		sb.append(TAB + "div bx           ; AX = AX / 10 " + ENTER);
		sb.append(TAB + "mov si, ax " + ENTER);
		sb.append(TAB + "cmp si, 0 " + ENTER);
		sb.append(TAB + "jne inicioread " + ENTER);
		sb.append(TAB + "cmp [bp-2], 00h " + ENTER);
		sb.append(TAB + "jne estado3bs1 " + ENTER);
		sb.append(TAB + "mov [bp-1], 00h " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
		sb.append("estado3bs1: " + ENTER);
		sb.append(TAB + "mov [bp-1], 02h " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
		sb.append("estado3a: " + ENTER);    
		sb.append(TAB + "dig '0' " + ENTER);
		sb.append(TAB + "jmp inicioread " + ENTER);
	
		sb.append("finread: " + ENTER);
		sb.append(TAB + "cmp [bp-2], 00h " + ENTER);
		sb.append(TAB + "je finread2 " + ENTER);
		sb.append(TAB + "neg si " + ENTER);
		sb.append("finread2: " + ENTER);
		sb.append(TAB + "mov [bp+6], si " + ENTER);
		sb.append(TAB + "pop si " + ENTER);
		sb.append(TAB + "pop dx " + ENTER);
		sb.append(TAB + "pop cx " + ENTER);
		sb.append(TAB + "pop bx " + ENTER);
		sb.append(TAB + "pop ax " + ENTER);
		sb.append(TAB + "mov sp, bp " + ENTER); 
		sb.append(TAB + "pop bp " + ENTER);    
		sb.append(TAB + "ret 2 " + ENTER);
		sb.append("readln endp " + ENTER);
		sb.append("; " + ENTER);
		sb.append("; ***Fin rutina readln*** " + ENTER);
	}
	
	public static void complete(StringBuilder code) {
		if(ErrorManager.debugEnabled) code.append("; PreDefined - Init \n\n");
		code.append(sb);
		if(ErrorManager.debugEnabled) code.append("; PreDefined - Finish \n\n");
	}
}
