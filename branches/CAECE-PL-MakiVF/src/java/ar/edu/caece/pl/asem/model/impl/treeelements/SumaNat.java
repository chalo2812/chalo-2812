package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;


public class SumaNat extends AbstractElement {

	private int tempResultSumaNat; //propio para producto de enteros, se usa para guardar el resultado de un producto temporal.
	
	public SumaNat (){
		this.label = "SUMA_NATURAL";
	}
	
	public int getTemp() {
		return tempResultSumaNat;
	}
	
	public void setTemp(int tempResultSumaNat) {
		this.tempResultSumaNat = tempResultSumaNat;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SumaNat))
			return false;
		SumaNat other = (SumaNat) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {
		AbstractElement firstTermElement = this.getChildren().get(0);
		AbstractElement secondTermElement = this.getChildren().get(1);

		if (ErrorManager.debugEnabled) sb.append("; [SumaNat]-firstTermElement" + ENTER);
		firstTermElement.compile(sb);
		//Hasta acá el valor del Primer termino quedo en AX
		sb.append(TAB + "push bx" + ENTER);		//backupeo BX
		//Uso BX de temporal
		sb.append(TAB + "mov bx, ax" + ENTER);

		if (ErrorManager.debugEnabled) sb.append("; [SumaNat]-secondTermElement" + ENTER);
		secondTermElement.compile(sb);
		//Hasta acá el valor del Segundo termino quedo en AX
		sb.append("add ax, bx");
		sb.append(TAB + "pop bx" + ENTER);		//restauro BX
		//El valor de retorno de la suma queda en AX
	}
}
