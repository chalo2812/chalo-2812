package tp.procesadores.analizador.lexico;

/**
 *	Este Objeto guarda las coordenadas de donde se encuentra el cursor en el archivo  
 *	para poder ubicar los lexemas
 */
public class Coordenadas {
	private int x, y;
	
	/**
	 * Este objeto mantiene la referencia a donde se encuentra parado el cursor en el archivo
	 * 
	 * @param ejeX columna donde se encuentra el cursor
	 * @param ejeY fila donde se encuentra el cursor
	 */
	public Coordenadas(int ejeX, int ejeY){ 
		x = ejeX;
		y = ejeY; 
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

