ORG 100h 
JMP main 
; ***Comienzo rutina writeCRLF*** 
; 
; Imprime por pantalla un caracter <Enter> (<CR><LF>) 
; 
; Parametros: - 
; 
writeCRLF proc near 
	push ax 
	mov al, 0Dh 
	mov ah, 0Eh 
	int 10h 
	mov al, 0Ah 
	mov ah, 0Eh 
	int 10h 
	pop ax 
	ret 
writeCRLF endp 
; 
; ***Fin rutina writeCRLF*** 
; ***Comienzo rutina writeSTR*** 
; 
; Imprime por pantalla un string sin <Enter> al final 
; 
; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador) 
; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador) 
; 
writeSTR proc near 
	push bp 
	mov bp, sp 
	push ax 
	push bx 
	push cx 
	push si 
	mov si, [bp+6]  ; a SI se le asigna el primer parametro (direccion) 
	mov cx, [bp+4]  ; a CX se le asigna el segundo parametro (cantidad de caracteres) 
	xor bx, bx 
loop: 
	mov al, [si] 
	mov ah, 0Eh 
	int 10h 
	inc bx 
	inc si 
	cmp bx, cx 
	jne loop 
	pop si 
	pop cx 
	pop bx 
	pop ax 
	pop bp 
	ret 4 
writeSTR endp 
; 
; ***Fin rutina writeSTR*** 
; ***Comienzo rutina writeNUM*** 
; 
; Imprime por pantalla un numero (word con signo) sin <Enter> al final 
; (usa rutina writeSTR) 
; 
; Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) 
; Parametro entrada 2: numero a imprimir (word/via push del llamador) 
; 
writeNUM proc near 
	push bp 
	mov bp, sp 
	sub sp, 1        ; "variable local" [bp-1] = signo (00h=positivo, 01h=negativo) 
	sub sp, 6        ; "variable local" [bp-7] = espacio para imprimir (db 6 dup(?)) 
	push ax 
	push bx 
	push cx 
	push dx 
	push si 
	mov [bp-1], 00h 
	mov ax, [bp+4] 
	cmp [bp+6], 0 
	je comenzar      ; Si no es aritmetica con signo, comienza 
	cmp ax, 0 
	jge comenzar     ; Si no es negativo, comienza 
	neg ax           ; Realiza ax = -ax 
	mov [bp-1], 01h 
comenzar: 
	mov bx, 10 
	mov cx, 0 
	mov si, bp 
	sub si, 8 
proxdiv: 
	dec si 
	xor dx, dx 
	div bx 
	add dl, 48 
	mov [si], dl 
	inc cx 
	cmp ax, 0 
	jnz proxdiv 
	cmp [bp-1], 00h 
	jz mostrar 
	dec si 
	mov [si], '-' 
	inc cx 
mostrar: 
	push si 
	push cx 
	call writeSTR 
	pop si 
	pop dx 
	pop cx 
	pop bx 
	pop ax 
	mov sp, bp 
	pop bp 
	ret 4 
writeNUM endp 
; 
; ***Fin rutina writeNUM*** 
; ***Comienzo rutina readln*** 
; 
; Obtiene por teclado un numero (word con o sin signo) 
; (usa rutina writeSTR, notar que posee dos macros) 
; Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden) 
; No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa 
; 
; Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador) 
; Parametro salida: numero obtenido (word/via pop del llamador) 
; (el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi) 
; 
dig macro digbase 
	cmp al, digbase 
	jl inicioread 
	cmp al, '9' 
	jg inicioread 
	mov ah, 0Eh 
	int 10h 
	mov [bp-1], 03h 
	mov cl, al 
	sub cl, 48 
	mov ax, si 
	mov bx, 000Ah 
	mul bx           ; AX = AX * 10 
	add ax, cx       ; AX = AX + CX (digito) 
	mov si, ax 
endm 
writeBS macro 
	mov ah, 0Eh 
	int 10h 
	mov al, ' ' 
	int 10h 
	mov al, 08h 
	int 10h 
endm 
readln proc near 
	push bp 
	mov bp, sp 
	sub sp, 1        ; [bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos) 
	sub sp, 1        ; [bp-2] = signo (00h=positivo, 01h=negativo) 
	push ax 
	push bx 
	push cx 
	push dx 
	push si 
	mov [bp-1], 00h 
	mov [bp-2], 00h 
	mov si, 0000h    ; valor 
	mov bx, 0 
	mov cx, 0 
inicioread: 
	mov ah, 0 
	int 16h 
	cmp [bp-1], 00h 
	je estado0 
	cmp [bp-1], 01h 
	je estado1 
	cmp [bp-1], 02h 
	je estado2 
	cmp [bp-1], 03h 
	je estado3 
estado0: 
	cmp al, 0Dh 
	je inicioread 
	cmp al, '0' 
	jne estado0a 
	mov [bp-1], 01h 
	mov ah, 0Eh 
	int 10h 
	jmp inicioread 
estado0a: 
	cmp al, '-' 
	jne estado0b 
	cmp [bp+4], 0000h 
	je inicioread 
	mov [bp-1], 02h 
	mov [bp-2], 01h 
	mov ah, 0Eh 
	int 10h 
	jmp inicioread 
estado0b: 
	dig '1' 
	jmp inicioread 
estado1: 
	cmp al, 0Dh 
	je finread 
	cmp al, 08h 
	jne inicioread 
	writeBS 
	mov [bp-1], 00h 
	jmp inicioread 
estado2: 
	cmp al, 0Dh 
	je inicioread 
	cmp al, 08h 
	jne estado2a 
	writeBS 
	mov [bp-1], 00h 
	mov [bp-2], 00h 
	jmp inicioread 
estado2a: 
	dig '1' 
	jmp inicioread 
estado3: 
	cmp al, 0Dh 
	je finread 
	cmp al, 08h 
	jne estado3a 
	writeBS 
	mov ax, si 
	mov dx, 0 
	mov bx, 000Ah 
	div bx           ; AX = AX / 10 
	mov si, ax 
	cmp si, 0 
	jne inicioread 
	cmp [bp-2], 00h 
	jne estado3bs1 
	mov [bp-1], 00h 
	jmp inicioread 
estado3bs1: 
	mov [bp-1], 02h 
	jmp inicioread 
estado3a: 
	dig '0' 
	jmp inicioread 
finread: 
	cmp [bp-2], 00h 
	je finread2 
	neg si 
finread2: 
	mov [bp+6], si 
	pop si 
	pop dx 
	pop cx 
	pop bx 
	pop ax 
	mov sp, bp 
	pop bp 
	ret 2 
readln endp 
; 
; ***Fin rutina readln*** 
hanoi proc near
	push bp
	mov bp, sp
	;sub sp, 2 ;VARIABLE actual
	;sub sp, 2 ;VARIABLE ant1
	;sub sp, 2 ;VARIABLE ant2
	;sub sp, 2 ;VARIABLE i
	sub sp, 8 ;TOTAL
	mov ax, null
	mov cx, ax
	mov ax, 1
	mov bx, ax
	mov ax, cx
	cmp ax, bx
jle lbl_0
	mov ax, 1
	push bx
	mov bx, ax
	mov ax, null
sub ax, bx	pop bx
	push tmp_-1
	push [bp+8]
	push [bp+4]
	push [bp+6]
	CALL hanoi
	 add sp, 8
	PUSH OFFSET tmpCadenap0
	PUSH 3
	CALL writeSTR
	mov ax, null
	 mov tmp_-1,ax 
	PUSH 0001h
	PUSH tmp_-1
	CALL writeNUM
	PUSH OFFSET tmpCadenap1
	PUSH 3
	CALL writeSTR
	mov ax, null
	 mov tmp_-1,ax 
	PUSH 0001h
	PUSH tmp_-1
	CALL writeNUM
	CALL writeCRLF
	mov ax, 1
	push bx
	mov bx, ax
	mov ax, null
sub ax, bx	pop bx
	push tmp_-1
	push [bp+4]
	push [bp+6]
	push [bp+8]
	CALL hanoi
	 add sp, 8
	jmp lbl_0
lbl_0:
	add sp, 8
	pop bp
	add sp, 8
	ret 
hanoi endp
main proc near
	;sub sp, 2 ;VARIABLE n
	sub sp, 2 ;TOTAL
	PUSH OFFSET tmpCadenap2
	PUSH 72
	CALL writeSTR
	CALL writeCRLF
	PUSH OFFSET tmpCadenap3
	PUSH 15
	CALL writeSTR
	CALL writeCRLF
	PUSH 0000h
	PUSH 0001h
	CALL readln
	POP Ax
	MOV main_n, AX
	PUSH OFFSET tmpCadenap4
	PUSH 1
	CALL writeSTR
	CALL writeCRLF
	mov ax, null
	push tmp_-1
	push 1
	push 2
	push 3
	CALL hanoi
	 add sp, 8
	int 21h
main endp

