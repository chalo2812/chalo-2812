package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;


public class DivisionInt extends AbstractElement {

	private int tempResultDivInt; //propio para producto de enteros, se usa para guardar el resultado de un producto temporal.
	
	public DivisionInt (){
		this.label = "DIVISION_ENTERA";
	}
	
	public int getTemp() {
		return tempResultDivInt;
	}
	
	public void setTemp(int temp) {
		this.tempResultDivInt = temp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DivisionInt))
			return false;
		DivisionInt other = (DivisionInt) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public void compile (StringBuilder sb) {
		/* Divide números con signo. Calcula el cociente y el resto de dividir AX / operando (tamaño byte).
		 * El cociente lo guarda en AL o AX según el caso. El resto en AH o DX. 
		 * Si divides por cero salta una interrupción 0.*/
		AbstractElement dividendo = this.getChildren().get(0);
		AbstractElement divisor = this.getChildren().get(1);
		
		if (ErrorManager.debugEnabled) sb.append("; [DivInt]-secondTermElement" + ENTER);
		divisor.compile(sb);
		//Hasta acá el valor del Segundo termino quedo en AX

		sb.append(TAB + "push bx" + ENTER);		//backupeo BX
		//Uso BX de temporal
		sb.append(TAB + "mov bx, ax" + ENTER);

		if (ErrorManager.debugEnabled) sb.append("; [DivInt]-firstTermElement" + ENTER);
		dividendo.compile(sb);
		//Hasta acá el valor del Primer termino quedo en AX

		sb.append(TAB + "mov dx, 0" + ENTER);
		sb.append(TAB + "idiv bx" + ENTER);		//ax=ax/bx
		sb.append(TAB + "pop bx" + ENTER);		//restauro BX
		//El valor de retorno de la suma queda en AX
	}
}
