package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.model.impl.Ambiente;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;


public class Declaraciones extends AbstractElement {

	public Declaraciones (){
		this.label = "DECLARACIONES";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Declaraciones))
			return false;
		Declaraciones other = (Declaraciones) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	@Override
	public void compile(StringBuilder sb) {
		if(ErrorManager.debugEnabled) sb.append("; Declaraciones - Init \n\n");
		
		Ambiente env = SymbolTable.getInstance().getAmbiente(this.getEnvName());
		
		if(SymbolTable.GLOBAL.equals(this.getEnvName())) {
			//Si el ambiente es global, las variables necesitan un puntero absoluto en memoria
			for (Arreglo a : env.getArreglos().values()) {
				sb.append(a.getEnvName() + "_" + a.getName() + " dw ");
				int size = a.getLength();
				for (int i = 0; i < size; i++) {
					if (i < size - 1) {
						sb.append("0, "); // Arreglos inicializados en 0
					} else {
						sb.append("0\n");
					}
				}
			}
			
			for(Variable v : env.getVariables().values()) {
				sb.append(v.getEnvName() + "_" + v.getName() + " dw 0\n"); //var dw value? o 0?
			}
			
			for(Constante c : env.getConstantes().values()) {
				sb.append(c.getEnvName() + "_" + c.getName() + " dw " + c.getValue() + "\n"); //var dw value
			}
			
		} else {
		//Si el ambiente es local, las variables necesitan un puntero relativo al BP, que a su vez es parte del SP
		
		/* Cuando se define la función se guarda el BP en SP (SP-2) y luego se asigna el nuevo SP al BP. Queda:
		 * bp+8 	- 2º function parameter
		 * bp+4 	- 1º function parameter
		 * bp+2 	- old IP (the function's "return address")
		 * bp-0 	- old BP (previous function's base pointer)
		 * bp-2 	- 1º local variable
		 * bp-4 	- 2º local variable
		 * bp-8 	- 3º local variable
		 */
			int offset = 0;
			for (Arreglo a : env.getArreglos().values()) {
				a.setPlace("[bp-"+offset+"]");
				sb.append(TAB).append(";sub sp, ").append(a.getLength() * REGISTER_SIZE);
				sb.append(" ;"+ a.getLabel() +" "+a.getName()+ENTER);
				offset += a.getLength() * REGISTER_SIZE;
			}
			
			for(Variable v : env.getVariables().values()) {
				v.setPlace("[bp-"+offset+"]");
				sb.append(TAB).append(";sub sp, ").append(REGISTER_SIZE);
				sb.append(" ;"+ v.getLabel() +" "+v.getName()+ENTER);
				offset += REGISTER_SIZE;
			}
			
			/*No se guardan constantes en el SP, se usa el valor directo*/
			
			if(offset > 0) {				
				sb.append(TAB).append("sub sp, ").append(offset).append(" ;TOTAL"+ENTER);
			}
		}
		
		if(ErrorManager.debugEnabled) sb.append("; Declaraciones - Finish \n\n");
	}
}
