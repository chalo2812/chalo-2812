package ar.edu.caece.pl.asem.model;

import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;

public interface IVisitor {

	public void visit(IAnalizadorSintactico nt);
	
}
