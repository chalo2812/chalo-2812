package ar.edu.caece.pl.asem.model.impl.treeelements;

import java.util.List;

public class Parametros extends AbstractElement {

	public Parametros (){
		this.label = "PARAMETROS";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Parametros))
			return false;
		Parametros other = (Parametros) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public void compile(StringBuilder sb) {
		/* Cuando se define la función se guarda el BP en SP (SP-2) y luego se asigna el nuevo SP al BP. Queda:
		 * bp+8 	- 2º function parameter
		 * bp+4 	- 1º function parameter
		 * bp+2 	- old IP (the function's "return address")   --> call/ret
		 * bp-0 	- old BP (previous function's base pointer)  --> push bp/pop bp
		 */
		int offset = 2 * REGISTER_SIZE;		//Dejamos espacio para el old BP y old IP
		List<AbstractElement> params = this.getChildren();
		for(AbstractElement p : params) {
			p.setPlace("[bp+"+offset+"]");	//BP (+) porque están antes en el SP, arrancando en el bp+4 
			offset += REGISTER_SIZE;
		}
	}
	
}
