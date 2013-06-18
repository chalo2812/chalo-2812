package tp.procesadores.compilador;

import tp.procesadores.compilador.constante.Constants;

public class Metodos {

	public String mostrarPantalla() {
		StringBuffer sb = new StringBuffer();
		sb.append(Constants.EncabezadoEscribirCaracter);
		sb.append("writeCRLF proc near");
		sb.append("	push ax ");
		sb.append("	mov al, 0Dh ");
		sb.append("			mov ah, 0Eh");
		sb.append("int 10h");
		sb.append("			mov al, 0Ah");
		sb.append("mov ah, 0Eh ");
		sb.append("int 10h");
		sb.append("pop ax ");
		sb.append("ret");
		sb.append("writeCRLF endp");
		sb.append(Constants.FinEscribirCaracter);
		return sb.toString();
	}
}
