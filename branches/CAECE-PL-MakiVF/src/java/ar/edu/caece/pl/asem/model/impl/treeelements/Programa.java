package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.codegenerator.PreDefinedFunctions;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class Programa extends AbstractElement {

	public Programa (){
		this.label = "PROGRAMA";
		this.envName = SymbolTable.GLOBAL;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Programa))		//La raiz se distinguira por su clase RootElement
			return false;
		Programa other = (Programa) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public void compile(StringBuilder sb) {
		sb.append("ORG 100h \n");
		sb.append("JMP main \n");
		
		PreDefinedFunctions.complete(sb);
		
		for (AbstractElement child : this.getChildren()) {	//DECLARACIONES y CODIGO
			if (ErrorManager.debugEnabled) sb.append("; Child Init - " + child.label + " - " + child.getEnvName() + ENTER);
			child.compile(sb);
			if (ErrorManager.debugEnabled) sb.append("; Child End - " + child.label + " - " + child.getEnvName() + ENTER);
		}
	}

}
