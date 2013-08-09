package tp.procesadores.analizador.lexico;

import java.io.File;
import tp.procesadores.analizador.lexico.tokens.*;

/**
 * 	Analizador Lexico, devuelve los tokens del archivo que se le pasa en su constructor 
 *
 */
public class LexicAnalyzer {
	
	public File file; 
	private FileReader fr; 
	private Coordenadas coord = new Coordenadas(1,1);
	LexicHelper helper = new LexicHelper(); 
	
	/** 
	 * El Analizador Lexico se encarga de devolver los tokens del archivo que recive como parametro 
	 * 
	 * @param f	Archivo con el codigo a ser compilado 
	 */
	public LexicAnalyzer(File f){
		file =f;
		fr = new FileReader(file);
	} 
	
	/**   
	 * Esta funcion devuelve el siguiente token del archivo 
	 */
	public Token getToken(){
		Caracter caracter = new Caracter();
		Token t = null; 
		
		caracter.setSimbolo(fr.read());
		tratarIgnorables(caracter,  coord,  helper,  fr);
		
		switch (caracter.getTipo()){ 
			case 1:
				//Tratar Numero
				t = helper.tratarNumero(coord, fr, caracter);
				break;
			case 2: 
				//Tratar Cadena 
				t = helper.tratarCadena(coord, fr, caracter);
				break; 
			case 3: 
				//Tratar Operador
				t = helper.tratarOperador(coord, fr, caracter); 
				break; 
			case 4:  
				//Tratar EOF 
				t = new Eof(coord.getY(),coord.getX());
				break; 
			case 5: 
				//Tratar Palabra
				t = helper.tratarPalabra(coord, fr, caracter);
				break;
			default: 
				//Tratar error por caracter invalido detectado
				t = helper.tratarError(coord, fr, caracter);
				break;
		}
		return t;
	} 
	

	//Remuevo espacios, comentarios, tabs y enters del principio del lexema
	private void tratarIgnorables(Caracter caracter, Coordenadas coord, LexicHelper helper, FileReader fr){
		while(caracter.esIgnorable()){ 
			if (caracter.getSimbolo() == '\n')
			{
            	coord.setY(coord.getY() + 1);
            	coord.setX(1);
            	caracter.setSimbolo(fr.read());
			}else
			{ 
				if (caracter.esPpioComentario())
				{
						helper.tratarComentario(coord, fr, caracter);
				}else
				{ 
						if(caracter.getSimbolo()=='\t') //Tratar TAB
						{    
							coord.setX(coord.getX() + (4 - ((coord.getX()-1 )%4)));
							caracter.setSimbolo(fr.read());
						}else 
						{	
							coord.setX(coord.getX()+1);
							caracter.setSimbolo(fr.read());
						}
				}
			}
		}

		
	}
}
