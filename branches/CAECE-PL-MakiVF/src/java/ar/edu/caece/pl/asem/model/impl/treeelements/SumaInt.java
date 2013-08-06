package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class SumaInt extends AbstractElement {

	private int tempResultSumaInt; // propio para producto de enteros, se usa
									// para guardar el resultado de un producto
									// temporal.

	public SumaInt() {
		this.label = "SUMA_ENTERO";
	}

	public int getTemp() {
		return tempResultSumaInt;
	}

	public void setTemp(int tempResultSumaInt) {
		this.tempResultSumaInt = tempResultSumaInt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SumaInt))
			return false;
		SumaInt other = (SumaInt) obj;
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

		if (ErrorManager.debugEnabled) sb.append("; [SumaInt]-firstTermElement" + ENTER);
		firstTermElement.compile(sb);
		//Hasta acá el valor del Primer termino quedo en AX
		sb.append(TAB + "push bx" + ENTER);		//backupeo BX
		//Uso BX de temporal
		sb.append(TAB + "mov bx, ax" + ENTER);

		if (ErrorManager.debugEnabled) sb.append("; [SumaInt]-secondTermElement" + ENTER);
		secondTermElement.compile(sb);
		//Hasta acá el valor del Segundo termino quedo en AX
		sb.append("add ax, bx");
		sb.append(TAB + "pop bx" + ENTER);		//restauro BX
		//El valor de retorno de la suma queda en AX
	}
}
