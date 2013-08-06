package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.label.LabelGenerator;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class Si extends AbstractElement {	
	
	public Si (){
		this.label = "IF";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Si))
			return false;
		Si other = (Si) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {
		if (ErrorManager.debugEnabled) sb.append("; [Si]-Init" + ENTER);

		String startLabel = LabelGenerator.getInstance().generateLabel();
		//Condicion
		this.getChildren().get(0).compile(sb);
		//Codigo de adentro del <THEN>
		this.getChildren().get(1).compile(sb);
		
		if (this.getChildren().size() > 2) {
			sb.append(TAB + "jmp " + LabelGenerator.getInstance().getActualLabel() + ENTER);
			sb.append(startLabel + ":" + ENTER);
			this.getChildren().get(2).compile(sb);
		} else {
			sb.append(TAB + "jmp " + startLabel + ENTER);
			sb.append(startLabel + ":" + ENTER);	
		}
		
		if (ErrorManager.debugEnabled) sb.append("; [Si]-End" + ENTER);
	}
	
}
