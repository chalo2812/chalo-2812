package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asin.manager.impl.ErrorManager;


public class ProductoInt extends AbstractElement {

	private int tempResultProdInt; // propio para producto de enteros, se usa
									// para guardar el resultado de un producto
									// temporal.

	public ProductoInt() {
		this.label = "PRODUCTO_ENTERO";
	}

	public int getTemp() {
		return tempResultProdInt;
	}

	public void setTemp(int tempResultProdInt) {
		this.tempResultProdInt = tempResultProdInt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductoInt))
			return false;
		ProductoInt other = (ProductoInt) obj;
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
		
		if (ErrorManager.debugEnabled) sb.append("; [ProdInt]-secondTermElement" + ENTER);
		multiplicador.compile(sb);
		//Hasta acá el valor del Segundo termino quedo en AX

		sb.append(TAB + "push bx" + ENTER);		//backupeo BX
		//Uso BX de temporal
		sb.append(TAB + "mov bx, ax" + ENTER);

		if (ErrorManager.debugEnabled) sb.append("; [ProdInt]-firstTermElement" + ENTER);
		multiplicando.compile(sb);
		//Hasta acá el valor del Primer termino quedo en AX

		sb.append(TAB + "mul bx" + ENTER);	//ax=ax*bx
		sb.append(TAB + "pop bx" + ENTER);		//restauro BX
		//El valor de retorno de la suma queda en AX
	}
}
