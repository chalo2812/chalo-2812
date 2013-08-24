package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

	
	/**
	 * Interfaz del Nodo para el composite del Arbol 
	 *
	 */
	public interface InterfazNodo extends VisitableNode{
		public void add(InterfazNodo nodo);
		public void remove(InterfazNodo nodo);
		public TablaDeSimbolos acceptTSVisitor(
				TablaSimbolosVisitor tablaSimbolosVisitor);
		public Codigo generarCodigo(Codigo codigoAux, TempManager tempManager,
				LabelManager labelManager);
		public String accept(NodeVisitor identVisitor);
		
		//Para verificar al generar codigo
		public boolean esFuncion(InterfazNodo posicion);
		public boolean esProcedimiento(InterfazNodo posicion);
		public boolean esGlobales(InterfazNodo posicion);
	}
 