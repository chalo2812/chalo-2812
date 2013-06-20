package tp.procesadores.analizador.lexico.tokens.visitor;

public interface Visitable {
	
	/**Interfaz para decir que el objeto que la implemente es visitable por el TokenVisitor.
	 * 
	 * @param visitor 	objeto de tipo TokensVisitor que podra visitar al visitable
	 * @return 
	 */
	public String accept(TokensVisitor visitor);
	
}
