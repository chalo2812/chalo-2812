package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.temp.TemporalGenerator;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

public class Asignacion extends AbstractElement {

	public Asignacion() {
		this.label = "ASIGNACION";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Asignacion))
			return false;
		Asignacion other = (Asignacion) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile(StringBuilder sb) {

		if (ErrorManager.debugEnabled) sb.append("; [Asignacion]-Init" + ENTER);
		
		AbstractElement leftTerm = this.getChildren().get(0);
		AbstractElement rightTerm = this.getChildren().get(1);
		
		/** La idea es hacer mov [left], [right]. Con Left la posición y right la posición o el valor de la constante*/
		if (!(leftTerm instanceof Arreglo)) {			
			if (rightTerm instanceof NumeroInt || 
				rightTerm instanceof NumeroNat ||
				rightTerm instanceof Variable  ||
				rightTerm instanceof Constante) {
				sb.append(TAB + "mov "+ leftTerm.getPlace() + ", ");
				rightTerm.compile(sb);
				sb.append(ENTER);
			} else if (rightTerm instanceof Arreglo) {
				//TODO
			} else {
				sb.append(TAB + "mov "+ leftTerm.getPlace() + ", ax" + ENTER);
			}
		}
		
		if (leftTerm instanceof Arreglo) {
			if (ErrorManager.debugEnabled) sb.append("; [Asignacion]-Arreglo" + ENTER);
			
			// <EXPRESSION>
			leftTerm.compile(sb);
			sb.append(TAB + "mov di, offset " + leftTerm.getPlace() + ENTER);
			sb.append(TAB + "mov bx, ax" + ENTER);
			sb.append(TAB + "mov ax, 2" + ENTER);
			sb.append(TAB + "mul bx" + ENTER);
			sb.append(TAB + "add di, ax" + ENTER);

			if (rightTerm instanceof Arreglo) {
				rightTerm.compile(sb);
				sb.append(TAB + " mov bx, ax" + ENTER);
				sb.append(TAB + " mov ax, 2" + ENTER);
				sb.append(TAB + " mul bx" + ENTER);
				sb.append(TAB + " mov bx, ax" + ENTER);
				sb.append(TAB + " mov di, offset " + rightTerm.getEnvName() + "_" + rightTerm.getName() + ENTER);
				sb.append(TAB + " add di, bx" + ENTER);
				sb.append(TAB + " mov ax, [di]" + ENTER);
				
			} else {
				rightTerm.compile(sb);
			}
			sb.append(TAB + "mov bx, ax" + ENTER);
			sb.append(TAB + "mov [di], bx" + ENTER);
			TemporalGenerator.getInstance().releaseTemporal();
			
		} else {
			if (ErrorManager.debugEnabled) sb.append("; [Asignacion]-NoArreglo" + ENTER);
			
			rightTerm.compile(sb);
			
			if (rightTerm instanceof Arreglo) {
				sb.append(TAB + " mov bx, ax" + ENTER);
				sb.append(TAB + " mov ax, 2" + ENTER);
				sb.append(TAB + " mul bx" + ENTER);
				sb.append(TAB + " mov bx, ax" + ENTER);
				sb.append(TAB + " mov di, offset " + rightTerm.getEnvName() + "_" + rightTerm.getName() + ENTER);
				sb.append(TAB + " add di, bx" + ENTER);
				sb.append(TAB + " mov ax, [di]" + ENTER);
			}
			// <EXPRESSION>
			sb.append(TAB + "mov " + leftTerm.getPlace() + ", ax" + ENTER);
			TemporalGenerator.getInstance().releaseTemporal();
		}
		
		if (ErrorManager.debugEnabled) sb.append("; [Asignacion]-End" + ENTER);
	}
}
