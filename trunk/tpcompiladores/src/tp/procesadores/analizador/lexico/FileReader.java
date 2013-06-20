package tp.procesadores.analizador.lexico;

import java.io.*;

/**
 * Clase usada para leer el archivo caracter a caracter y tener un caracter de lookahead
 */
public class FileReader {

	private File file;
	public static PushbackInputStream input;
	FileInputStream fin;
	public static final int EOF = -1; 

	/**
	 * Este objeto puede leer un file caracter a caracter y hacer un peek(), tiene un caracter 
	 * de lookahead 
	 * 
	 * @param f 	El Archivo a tratar
	 */
	public FileReader(File f) {
		try {
			file = f;
			fin = new FileInputStream(file);
			input = new PushbackInputStream(fin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lee el buffer donde se encuentra cargado el archivo, moviendo el puntero en una posicion 
	 * y devolviendo un caracter en lowerCase
	 * 
	 * @return 	devuelve un int con el caracter leido en minuscula
	 */
	public int read() {
		int data;
		try {
			if ((data = input.read()) != EOF){
				return Character.toLowerCase(data); 
			}else{
				return -1; //EOF 
			} 
		} catch (IOException e) {
			e.printStackTrace();
			return -1; 
		}
	}
	/**
	 * Se utiliza para leer cadenas, no modifica las Mayusculas o minusculas
	 * 
	 * @return devuelve un int con el caracter leido  
	 */
	public int readInsensitive() {
		int data;
		try {
			if ((data = input.read()) != EOF){
				return data; 
			}else{
				return -1; //EOF 
			} 
		} catch (IOException e) {
			e.printStackTrace();
			return -1; 
		}
	}


	/**
	 * Espia el siguiente caracter del buffer donde se encuentra cargado el archivo 
	 * 
	 * @return devuelve un int con el siguiente caracter que 
	 */
	public int peek() {
		int data, r;
		try {
			if ( (data = input.read()) != EOF ) { 
				r = data; 
				input.unread(data);
				return Character.toLowerCase(r);
			}else{
				return -1; //EOF 
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
