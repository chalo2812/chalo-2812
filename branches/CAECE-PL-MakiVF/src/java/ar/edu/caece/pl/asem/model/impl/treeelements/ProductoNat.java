package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;


public class ProductoNat extends AbstractElement {

	private int tempResultProdNat; //propio para producto de enteros, se usa para guardar el resultado de un producto temporal.
	
	public ProductoNat (){
		this.label = "PRODUCTO_NATURAL";
	}
	
	public int getTemp() {
		return tempResultProdNat;
	}
	
	public void setTemp(int tempResultProdNat) {
		this.tempResultProdNat = tempResultProdNat;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductoNat))
			return false;
		ProductoNat other = (ProductoNat) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public void compile (StringBuilder sb) {
		AbstractElement multiplicando = this.getChildren().get(0);
		AbstractElement multiplicador = this.getChildren().get(1);
		
		if (ErrorManager.debugEnabled) sb.append("; [ProdNat]-secondTermElement" + ENTER);
		multiplicador.compile(sb);
		//Hasta acá el valor del Segundo termino quedo en AX

		sb.append(TAB + "push bx" + ENTER);		//backupeo BX
		//Uso BX de temporal
		sb.append(TAB + "mov bx, ax" + ENTER);

		if (ErrorManager.debugEnabled) sb.append("; [ProdNat]-firstTermElement" + ENTER);
		multiplicando.compile(sb);
		//Hasta acá el valor del Primer termino quedo en AX

		sb.append(TAB + "mul bx" + ENTER);	//ax=ax*bx
		sb.append(TAB + "pop bx" + ENTER);		//restauro BX
		//El valor de retorno de la suma queda en AX
	}
}
