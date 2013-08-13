package tp.procesadores.compilador.generadorcodigo.constantes;

public class PreDefinedFunctions {
		
	public final static StringBuilder sb = new StringBuilder(); 
	static {
		sb.append("; ***Comienzo rutina writeCRLF*** " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Imprime por pantalla un caracter <Constants.ENTER> (<CR><LF>) " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Parametros: - " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("writeCRLF proc near " + Constants.ENTER);
		sb.append(Constants.TAB + "push ax " + Constants.ENTER);
		    
		sb.append(Constants.TAB + "mov al, 0Dh " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append(Constants.TAB + "mov al, 0Ah " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append(Constants.TAB + "pop ax " + Constants.ENTER);
		sb.append(Constants.TAB + "ret " + Constants.ENTER);
		sb.append("writeCRLF endp " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; ***Fin rutina writeCRLF*** " + Constants.ENTER);
		sb.append("; ***Comienzo rutina writeSTR*** " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Imprime por pantalla un string sin <Constants.ENTER> al final " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador) " + Constants.ENTER);
		sb.append("; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador) " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("writeSTR proc near " + Constants.ENTER);
		sb.append(Constants.TAB + "push bp " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bp, sp " + Constants.ENTER);
		sb.append(Constants.TAB + "push ax " + Constants.ENTER);
		sb.append(Constants.TAB + "push bx " + Constants.ENTER);
		sb.append(Constants.TAB + "push cx " + Constants.ENTER);
		sb.append(Constants.TAB + "push si " + Constants.ENTER);
		sb.append(Constants.TAB + "mov si, [bp+6]  ; a SI se le asigna el primer parametro (direccion) " + Constants.ENTER);
		sb.append(Constants.TAB + "mov cx, [bp+4]  ; a CX se le asigna el segundo parametro (cantidad de caracteres) " + Constants.ENTER);
		sb.append(Constants.TAB + "xor bx, bx " + Constants.ENTER);	    
		sb.append("loop: " + Constants.ENTER);    
		sb.append(Constants.TAB + "mov al, [si] " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append(Constants.TAB + "inc bx " + Constants.ENTER);
		sb.append(Constants.TAB + "inc si " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp bx, cx " + Constants.ENTER);
		sb.append(Constants.TAB + "jne loop " + Constants.ENTER);
		sb.append(Constants.TAB + "pop si " + Constants.ENTER);
		sb.append(Constants.TAB + "pop cx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop bx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop ax " + Constants.ENTER);
		sb.append(Constants.TAB + "pop bp " + Constants.ENTER);
		sb.append(Constants.TAB + "ret 4 " + Constants.ENTER);
		sb.append("writeSTR endp " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; ***Fin rutina writeSTR*** " + Constants.ENTER);
	
	
	
		sb.append("; ***Comienzo rutina writeNUM*** " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Imprime por pantalla un numero (word con signo) sin <Constants.ENTER> al final " + Constants.ENTER);
		sb.append("; (usa rutina writeSTR) " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) " + Constants.ENTER);
		sb.append("; Parametro entrada 2: numero a imprimir (word/via push del llamador) " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("writeNUM proc near " + Constants.ENTER);
		sb.append(Constants.TAB + "push bp " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bp, sp " + Constants.ENTER);
		sb.append(Constants.TAB + "sub sp, 1        ; \"variable local\" [bp-1] = signo (00h=positivo, 01h=negativo) " + Constants.ENTER);
		sb.append(Constants.TAB + "sub sp, 6        ; \"variable local\" [bp-7] = espacio para imprimir (db 6 dup(?)) " + Constants.ENTER);
	
		sb.append(Constants.TAB + "push ax " + Constants.ENTER);
		sb.append(Constants.TAB + "push bx " + Constants.ENTER);
		sb.append(Constants.TAB + "push cx " + Constants.ENTER);
		sb.append(Constants.TAB + "push dx " + Constants.ENTER);
		sb.append(Constants.TAB + "push si " + Constants.ENTER);
	
		sb.append(Constants.TAB + "mov [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ax, [bp+4] " + Constants.ENTER);
	
		sb.append(Constants.TAB + "cmp [bp+6], 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "je comenzar      ; Si no es aritmetica con signo, comienza " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp ax, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "jge comenzar     ; Si no es negativo, comienza " + Constants.ENTER);
		sb.append(Constants.TAB + "neg ax           ; Realiza ax = -ax " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 01h " + Constants.ENTER);
	
		sb.append("comenzar: " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bx, 10 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov cx, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov si, bp " + Constants.ENTER);
		sb.append(Constants.TAB + "sub si, 8 " + Constants.ENTER);
	
		sb.append("proxdiv: " + Constants.ENTER);
		sb.append(Constants.TAB + "dec si " + Constants.ENTER);
		sb.append(Constants.TAB + "xor dx, dx " + Constants.ENTER);    
		sb.append(Constants.TAB + "div bx " + Constants.ENTER);
		sb.append(Constants.TAB + "add dl, 48 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [si], dl " + Constants.ENTER);
		sb.append(Constants.TAB + "inc cx " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp ax, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "jnz proxdiv " + Constants.ENTER);
		    
		sb.append(Constants.TAB + "cmp [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "jz mostrar " + Constants.ENTER);
		    
		sb.append(Constants.TAB + "dec si " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [si], '-' " + Constants.ENTER);
		sb.append(Constants.TAB + "inc cx " + Constants.ENTER);
	
		sb.append("mostrar: " + Constants.ENTER);    
		sb.append(Constants.TAB + "push si " + Constants.ENTER);
		sb.append(Constants.TAB + "push cx " + Constants.ENTER);
		sb.append(Constants.TAB + "call writeSTR " + Constants.ENTER);
	
		sb.append(Constants.TAB + "pop si " + Constants.ENTER);
		sb.append(Constants.TAB + "pop dx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop cx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop bx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop ax " + Constants.ENTER);
	
		sb.append(Constants.TAB + "mov sp, bp " + Constants.ENTER);   
		sb.append(Constants.TAB + "pop bp " + Constants.ENTER);
		sb.append(Constants.TAB + "ret 4 " + Constants.ENTER);
		sb.append("writeNUM endp " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; ***Fin rutina writeNUM*** " + Constants.ENTER);
	
	
	
		sb.append("; ***Comienzo rutina readln*** " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Obtiene por teclado un numero (word con o sin signo) " + Constants.ENTER);
		sb.append("; (usa rutina writeSTR, notar que posee dos macros) " + Constants.ENTER);
		sb.append("; Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Constants.ENTER> (cuando corresponden) " + Constants.ENTER);
		sb.append("; No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) " + Constants.ENTER);
		sb.append("; Parametro salida: numero obtenido (word/via pop del llamador) " + Constants.ENTER);
		sb.append("; (el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi) " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("dig macro digbase " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, digbase " + Constants.ENTER);
		sb.append(Constants.TAB + "jl inicioread " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, '9' " + Constants.ENTER);
		sb.append(Constants.TAB + "jg inicioread " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append(Constants.TAB + "mov [bp-1], 03h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov cl, al " + Constants.ENTER);
		sb.append(Constants.TAB + "sub cl, 48 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ax, si " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bx, 000Ah " + Constants.ENTER);
		sb.append(Constants.TAB + "mul bx           ; AX = AX * 10 " + Constants.ENTER);
		sb.append(Constants.TAB + "add ax, cx       ; AX = AX + CX (digito) " + Constants.ENTER);
		sb.append(Constants.TAB + "mov si, ax " + Constants.ENTER);
		sb.append("endm " + Constants.ENTER);
		sb.append("writeBS macro " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov al, ' ' " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov al, 08h " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append("endm " + Constants.ENTER);
		sb.append("readln proc near " + Constants.ENTER);
		sb.append(Constants.TAB + "push bp " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bp, sp " + Constants.ENTER);
		sb.append(Constants.TAB + "sub sp, 1        ; [bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos) " + Constants.ENTER);
		sb.append(Constants.TAB + "sub sp, 1        ; [bp-2] = signo (00h=positivo, 01h=negativo) " + Constants.ENTER);
	
		sb.append(Constants.TAB + "push ax " + Constants.ENTER);
		sb.append(Constants.TAB + "push bx " + Constants.ENTER);
		sb.append(Constants.TAB + "push cx " + Constants.ENTER);
		sb.append(Constants.TAB + "push dx " + Constants.ENTER);
		sb.append(Constants.TAB + "push si " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-2], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov si, 0000h    ; valor " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bx, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov cx, 0 " + Constants.ENTER);
	
		sb.append("inicioread: " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "int 16h " + Constants.ENTER);
		    
		sb.append(Constants.TAB + "cmp [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "je estado0 " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp [bp-1], 01h " + Constants.ENTER);
		sb.append(Constants.TAB + "je estado1 " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp [bp-1], 02h " + Constants.ENTER);
		sb.append(Constants.TAB + "je estado2 " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp [bp-1], 03h " + Constants.ENTER);
		sb.append(Constants.TAB + "je estado3 " + Constants.ENTER);
		    
		sb.append("estado0: " + Constants.ENTER);    
		sb.append(Constants.TAB + "cmp al, 0Dh " + Constants.ENTER);
		sb.append(Constants.TAB + "je inicioread " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, '0' " + Constants.ENTER);
		sb.append(Constants.TAB + "jne estado0a " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 01h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
		sb.append("estado0a: " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, '-' " + Constants.ENTER);
		sb.append(Constants.TAB + "jne estado0b " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp [bp+4], 0000h " + Constants.ENTER);
		sb.append(Constants.TAB + "je inicioread " + Constants.ENTER);    
		sb.append(Constants.TAB + "mov [bp-1], 02h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-2], 01h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh " + Constants.ENTER);
		sb.append(Constants.TAB + "int 10h " + Constants.ENTER);     
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
		sb.append("estado0b: " + Constants.ENTER);
		sb.append(Constants.TAB + "dig '1' " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
	
		sb.append("estado1: " + Constants.ENTER);    
		sb.append(Constants.TAB + "cmp al, 0Dh " + Constants.ENTER);
		sb.append(Constants.TAB + "je finread " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, 08h " + Constants.ENTER);
		sb.append(Constants.TAB + "jne inicioread " + Constants.ENTER);
		sb.append(Constants.TAB + "writeBS " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
	
		sb.append("estado2: " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, 0Dh " + Constants.ENTER);
		sb.append(Constants.TAB + "je inicioread " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, 08h " + Constants.ENTER);
		sb.append(Constants.TAB + "jne estado2a " + Constants.ENTER);
		sb.append(Constants.TAB + "writeBS " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-2], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
		sb.append("estado2a: " + Constants.ENTER);    
		sb.append(Constants.TAB + "dig '1' " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
	
		sb.append("estado3: " + Constants.ENTER);    
		sb.append(Constants.TAB + "cmp al, 0Dh " + Constants.ENTER);
		sb.append(Constants.TAB + "je finread " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp al, 08h " + Constants.ENTER);
		sb.append(Constants.TAB + "jne estado3a " + Constants.ENTER);
		sb.append(Constants.TAB + "writeBS " + Constants.ENTER);
		sb.append(Constants.TAB + "mov ax, si " + Constants.ENTER);
		sb.append(Constants.TAB + "mov dx, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov bx, 000Ah " + Constants.ENTER);
		sb.append(Constants.TAB + "div bx           ; AX = AX / 10 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov si, ax " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp si, 0 " + Constants.ENTER);
		sb.append(Constants.TAB + "jne inicioread " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp [bp-2], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "jne estado3bs1 " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
		sb.append("estado3bs1: " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp-1], 02h " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
		sb.append("estado3a: " + Constants.ENTER);    
		sb.append(Constants.TAB + "dig '0' " + Constants.ENTER);
		sb.append(Constants.TAB + "jmp inicioread " + Constants.ENTER);
	
		sb.append("finread: " + Constants.ENTER);
		sb.append(Constants.TAB + "cmp [bp-2], 00h " + Constants.ENTER);
		sb.append(Constants.TAB + "je finread2 " + Constants.ENTER);
		sb.append(Constants.TAB + "neg si " + Constants.ENTER);
		sb.append("finread2: " + Constants.ENTER);
		sb.append(Constants.TAB + "mov [bp+6], si " + Constants.ENTER);
		sb.append(Constants.TAB + "pop si " + Constants.ENTER);
		sb.append(Constants.TAB + "pop dx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop cx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop bx " + Constants.ENTER);
		sb.append(Constants.TAB + "pop ax " + Constants.ENTER);
		sb.append(Constants.TAB + "mov sp, bp " + Constants.ENTER); 
		sb.append(Constants.TAB + "pop bp " + Constants.ENTER);    
		sb.append(Constants.TAB + "ret 2 " + Constants.ENTER);
		sb.append("readln endp " + Constants.ENTER);
		sb.append("; " + Constants.ENTER);
		sb.append("; ***Fin rutina readln*** " + Constants.ENTER);
	}
	
}
