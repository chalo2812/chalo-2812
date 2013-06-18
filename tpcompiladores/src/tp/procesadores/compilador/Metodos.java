package tp.procesadores.compilador;

import tp.procesadores.compilador.constante.Constants;

public class Metodos {

	public String mostrarPantalla() {
		StringBuffer sb = new StringBuffer();
		sb.append(Constants.EncabezadoEscribirCaracter);
		sb.append("writeCRLF proc near" + Constants.ENTER);
		sb.append(Constants.TAB + "	push ax "+ Constants.ENTER);
		sb.append(Constants.TAB + "	mov al, 0Dh "+ Constants.ENTER);
		sb.append(Constants.TAB + "	mov ah, 0Eh"+ Constants.ENTER);
		sb.append(Constants.TAB + " int 10h"+ Constants.ENTER);
		sb.append(Constants.TAB + "	mov al, 0Ah"+ Constants.ENTER);
		sb.append(Constants.TAB + "mov ah, 0Eh "+ Constants.ENTER);
		sb.append(Constants.TAB + "int 10h"+ Constants.ENTER);
		sb.append(Constants.TAB + "pop ax "+ Constants.ENTER);
		sb.append(Constants.TAB + "ret"+ Constants.ENTER);
		sb.append(Constants.TAB + "writeCRLF endp"+ Constants.ENTER);
		sb.append(Constants.FinEscribirCaracter);
		return sb.toString();
	}
}
