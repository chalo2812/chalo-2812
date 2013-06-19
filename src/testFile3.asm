ORG 0100h

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