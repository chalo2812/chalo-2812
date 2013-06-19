 SEG Segment
 ASSUME CS:SEG,SS:SEG,DS:SEG,ES:SEG
 ORG 0100h

aritmetico	dw ?
PUSH	aritmetico

JMP	PRINCIPAL


PRINCIPAL:

testvar	dw ?
MOV	AX, testvar
MOV	BX, 0
CMP	BX, 0
JNE	 EVITARINTERRUPCION
int	00h
EVITARINTERRUPCION:
IDIV	BX
MOV	aritmetico, AX
MOV	DX, aritmetico
MOV	testvar, DX


JMP	FINAL




; ***Comienzo rutina writeSTR***
; Imprime por pantalla un string sin <Enter> al final
; Parametros:
; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador)
; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador)
writeSTR PROC NEAR

PUSH	bp
MOV	bp, sp
PUSH	ax
PUSH	bx
PUSH	cx
PUSH	si
; a SI se le asigna el primer parametro (direccion)
MOV	si, [bp+6]
; a CX se le asigna el segundo parametro (cantidad de caracteres)
MOV	cx, [bp+4]
xor bx,bx
loop:

MOV	al, [si]
MOV	ah, 0Eh
int 10h
inc bx
inc si
CMP	bx, cx
jne loop
POP	si
POP	cx
POP	bx
POP	ax
POP	bp
 RET 4
writeSTR ENDP

; ***Fin rutina writeSTR***

; ***Comienzo rutina writeCRLF***
; Imprime por pantalla un caracter <Enter> (<CR><LF>)
; Parametros: -
writeCRLF PROC NEAR

PUSH	AX
MOV	al, 0Dh
MOV	ah, 0Eh
int 10h
MOV	al, 0Ah
MOV	ah, 0Eh
int 10h
POP	AX
 RET
writeCRLF ENDP

; ***Fin rutina writeCRLF***

; ***Comienzo rutina writeNUM***
; Imprime por pantalla un numero (word con signo) sin <Enter> al final
; Parametros:
; Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)
; Parametro entrada 2: numero a imprimir (word/via push del llamador)
writeNUM PROC NEAR

PUSH	bp
MOV	bp, sp
; variable local/ [bp-1] = signo (00h=positivo, 01h=negativo)
sub sp,1
; variable local/ [bp-7] = espacio para imprimir (db 6 dup(?))
sub sp,6
PUSH	ax
PUSH	bx
PUSH	cx
PUSH	dx
PUSH	si
MOV	[bp-1], 00h
MOV	ax, [bp+4]
CMP	[bp+6], 0
; Si no es aritmetica con signo, comienza
je comenzar
CMP	ax, 0
; Si no es negativo, comienza
jge comenzar
; Realiza ax = -ax
neg ax
MOV	[bp-1], 01h
comenzar:

MOV	bx, 10
MOV	cx, 0
MOV	si, bp
sub si,8
proxdiv:

dec si
xor dx,dx
div bx
add dl,48
MOV	[si], dl
inc cx
CMP	ax, 0
jnz proxdiv
CMP	[bp-1], 00h
jz mostrar
dec si
MOV	[si], '-'
inc cx
mostrar:

PUSH	si
PUSH	cx
CALL	writeSTR
POP	si
POP	dx
POP	cx
POP	bx
POP	ax
MOV	sp, bp
POP	bp
 RET 4
writeNUM ENDP

; ***Fin rutina writeNUM***

; ***Comienzo rutina readln***
; Obtiene por teclado un numero (word con o sin signo)
; (usa rutina writeSTR, notar que posee dos macros)
; Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden)
; No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa
; Parametros:; Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)
; Parametro salida: numero obtenido (word/via pop del llamador)
; (el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi)
dig macro digbase
CMP	al, digbase
jl inicioread
CMP	al, '9'
jg inicioread
MOV	ah, 0Eh
int 10h
MOV	[bp-1], 03h
MOV	cl, al
sub cl,48
MOV	ax, si
MOV	bx, 000Ah
; AX = AX * 10
mul bx
; AX = AX + CX (digito)
add ax,cx
MOV	si, ax
endm
writeBS macro
MOV	ah, 0Eh
int 10h
MOV	al, ' '
int 10h
MOV	ah, 08h
int 10h
endm
readln PROC NEAR

PUSH	bp
MOV	bp, sp
; [bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos)
sub sp,1
; [bp-2] = signo (00h=positivo, 01h=negativo)
sub sp,1
PUSH	ax
PUSH	bx
PUSH	cx
PUSH	dx
PUSH	si
MOV	[bp-1], 00h
MOV	[bp-2], 00h
; valor
MOV	si, 0000h
MOV	bx, 0
MOV	cx, 0
inicioread:

MOV	ah, 0
int 16h
CMP	[bp-1], 00h
je estado0
CMP	[bp-1], 01h
je estado1
CMP	[bp-1], 02h
je estado2
CMP	[bp-1], 03h
je estado3
estado0:

CMP	al, 0Dh
je inicioread
CMP	al, '0'
jne estado0a
MOV	[bp-1], 01h
MOV	ah, 0Eh
int 10h
JMP	inicioread
estado0a:

CMP	al, '-'
jne estado0b
CMP	[bp+4], 0000h
je inicioread
MOV	[bp-1], 02h
MOV	[bp-2], 01h
MOV	ah, 0Eh
int 10h
JMP	inicioread
estado0b:

dig '1'
JMP	inicioread
estado1:

CMP	al, 0Dh
je finread
CMP	al, 08h
jne inicioread
writeBS
MOV	[bp-1], 00h
JMP	inicioread
estado2:

CMP	al, 0Dh
je inicioread
CMP	al, 08h
jne estado2a
writeBS
MOV	[bp-1], 00h
MOV	[bp-2], 00h
JMP	inicioread
estado2a:

dig '1'
JMP	inicioread
estado3:

CMP	al, 0Dh
je finread
CMP	al, 08h
jne estado3a
writeBS
MOV	ax, si
MOV	dx, 0
MOV	bx, 000Ah
; AX = AX / 10
div bx
MOV	si, ax
CMP	si, 0
jne inicioread
CMP	[bp-2], 00h
jne estado3bs1
MOV	[bp-1], 00h
JMP	inicioread
estado3bs1:

MOV	[bp-1], 02h
JMP	inicioread
estado3a:

dig '0'
JMP	inicioread
finread:

CMP	[bp-2], 00h
je finread2
neg si
finread2:

MOV	[bp+6], si
POP	si
POP	dx
POP	cx
POP	bx
POP	ax
MOV	sp, bp
POP	bp
 RET 2
readln ENDP

; ***Fin rutina readln***
AENTERO PROC NEAR

MOV	AX, BX
 RET
AENTERO ENDP

ANATURAL PROC NEAR

MOV	AX,BX
CMP	AX,AX
JS	NEGATIVO
RET
NEGATIVO:
MOV	BX,-1
IMUL	BX
MOV	AX, BX
 RET
ANATURAL ENDP

FINAL:
POP	aritmetico
   MOV	AH, 4ch
   INT	21h
END	PRINCIPAL


