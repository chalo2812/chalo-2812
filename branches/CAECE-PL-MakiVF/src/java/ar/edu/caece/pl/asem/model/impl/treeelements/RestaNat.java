package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;


public class RestaNat extends AbstractElement {

	private int tempResultRestaNat; //propio para producto de enteros, se usa para guardar el resultado de un producto temporal.
	
	public RestaNat (){
		this.label = "RESTA_NATURAL";
	}
	
	public int getTemp() {
		return tempResultRestaNat;
	}
	
	public void setTemp(int tempResultRestaNat) {
		this.tempResultRestaNat = tempResultRestaNat;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RestaNat))
			return false;
		RestaNat other = (RestaNat) obj;
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

		if (ErrorManager.debugEnabled) sb.append("; [RestaNat]-secondTermElement" + ENTER);
		secondTermElement.compile(sb);
		//Hasta acá el valor del Segundo termino quedo en AX

		sb.append(TAB + "push bx" + ENTER);		//backupeo BX
		//Uso BX de temporal
		sb.append(TAB + "mov bx, ax" + ENTER);

		if (ErrorManager.debugEnabled) sb.append("; [RestaNat]-firstTermElement" + ENTER);
		firstTermElement.compile(sb);
		//Hasta acá el valor del Primer termino quedo en AX

		sb.append("sub ax, bx");	//ax=ax-bx
		sb.append(TAB + "pop bx" + ENTER);		//restauro BX
		//El valor de retorno de la suma queda en AX
	}
}
