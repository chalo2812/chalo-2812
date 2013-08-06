package ar.edu.caece.pl.asem.model;

import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.impl.BloqueVarParam;
import ar.edu.caece.pl.asin.model.impl.Condicion;
import ar.edu.caece.pl.asin.model.impl.Expresion;
import ar.edu.caece.pl.asin.model.impl.Factor;
import ar.edu.caece.pl.asin.model.impl.Header;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;
import ar.edu.caece.pl.asin.model.impl.Sentencia;
import ar.edu.caece.pl.asin.model.impl.ShowElem;
import ar.edu.caece.pl.asin.model.impl.Termino;
import ar.edu.caece.pl.asin.model.impl.Tipo;
import ar.edu.caece.pl.asin.model.impl.TipoC;
import ar.edu.caece.pl.asin.model.impl.Var;

public abstract class AbstractVisitor implements IVisitor {
	
	@Override
	public void visit(IAnalizadorSintactico nt) {  //nt = NoTerminal
		
		//this.visit(nt);		//Aqui, la magia del polimorfismo no anda :(
		
		if(nt.getClass() == AnalizadorSintactico.class) { //AnalizadorSintactico es <Programa>
			this.visit((AnalizadorSintactico)nt);
		} else if(nt.getClass() == BloqueVarParam.class) {
			this.visit((BloqueVarParam)nt);
		} else if(nt.getClass() == Condicion.class) {
			this.visit((Condicion)nt);
		} else if(nt.getClass() == Expresion.class) {
			this.visit((Expresion)nt);
		} else if(nt.getClass() == Factor.class) {
			this.visit((Factor)nt);
		} else if(nt.getClass() == Header.class) {
			this.visit((Header)nt);
		} else if(nt.getClass() == ProcsFuncs.class) {
			this.visit((ProcsFuncs)nt);
		} else if(nt.getClass() == Sentencia.class) {
			this.visit((Sentencia)nt);
		} else if(nt.getClass() == ShowElem.class) {
			this.visit((ShowElem)nt);
		} else if(nt.getClass() == Termino.class) {
			this.visit((Termino)nt);
		} else if(nt.getClass() == Tipo.class) {
			this.visit((Tipo)nt);
		} else if(nt.getClass() == TipoC.class) {
			this.visit((TipoC)nt);
		}
	}

	//A continuación los métodos por default, se heredarán y se sobreescribirán cuando sea oportuno
	protected void visit(AnalizadorSintactico nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(BloqueVarParam nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Condicion nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Expresion nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Factor nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Header nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(ProcsFuncs nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Sentencia nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(ShowElem nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Termino nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(Tipo nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit(TipoC nt) {
		throw new IllegalArgumentException("No Implementado");
	}
	protected void visit (Var nt) {
		throw new IllegalArgumentException("No Implementado");
	}
}
