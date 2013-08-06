package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.temp.TemporalGenerator;


public class ShowLn extends AbstractElement {

	public ShowLn() {
		this.label = "SHOWLN";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ShowLn))
			return false;
		ShowLn other = (ShowLn) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public void compile (StringBuilder sb) {

		if (this.getChildren().size() > 0) {
			for (AbstractElement child : this.getChildren()) {
				if (child instanceof Cadena) {
					String name = "tmp" + "Cadena";

					name = TemporalGenerator.getInstance().insertTemporal(name,
							((Cadena) child).getValue());
					sb.append(TAB + "PUSH OFFSET " + name + ENTER);
					sb.append(TAB + "PUSH " + (((Cadena) child).getValue().length() - 2) + ENTER);
					sb.append(TAB + "CALL writeSTR" + ENTER);
					//(inieto) Revisado
				} else {
					child.compile(sb);
					if(child instanceof Arreglo){
						sb.append(TAB + "mov bx, ax" + ENTER);
						sb.append(TAB + "mov ax, 2" + ENTER);
						sb.append(TAB + "mul bx" + ENTER);
						sb.append(TAB + "mov bx, ax" + ENTER);
						sb.append(TAB + "mov di, offset " + child.getEnvName() + "_" + child.getName() + ENTER);
						sb.append(TAB + "add di, bx" + ENTER);
						sb.append(TAB + "mov ax, [di]" + ENTER);
					}
					
					sb.append("\t mov " + TemporalGenerator.getInstance()
							.getActualTemporal() + ",ax \n");
					
					sb.append(TAB + "PUSH 0001h" + ENTER);
					sb.append(TAB + "PUSH "
							+ TemporalGenerator.getInstance()
									.getActualTemporal() + ENTER);
					TemporalGenerator.getInstance().releaseTemporal();
					sb.append(TAB + "CALL writeNUM" + ENTER);
				}
			}
			// Si no hay una coma despues de la cadena o variable a mostrar
			if (this.getChildren().size()>0) {
				sb.append(TAB + "CALL writeCRLF" + ENTER);
			}
		}
	}
	
}
