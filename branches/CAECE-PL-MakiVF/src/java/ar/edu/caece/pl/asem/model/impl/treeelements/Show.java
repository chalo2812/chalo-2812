package ar.edu.caece.pl.asem.model.impl.treeelements;

import ar.edu.caece.pl.asem.temp.TemporalGenerator;


public class Show extends AbstractElement {

	public Show() {
		this.label = "SHOW";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Show))
			return false;
		Show other = (Show) obj;
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

					name = TemporalGenerator.getInstance().insertTemporal(name, ((Cadena) child).getValue());
					sb.append(TAB + "PUSH OFFSET " + name + ENTER);	
					//La keyword OFFSET indica al CPU de pushear la dirección y no el contenido 
					sb.append(TAB + "PUSH "+ (((Cadena) child).getValue().length() - 2) + ENTER);
					sb.append(TAB + "CALL writeSTR" + ENTER);
				} else {
					child.compile(sb);
					if (child instanceof Arreglo) {
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
					sb.append(TAB + "PUSH " + TemporalGenerator.getInstance().getActualTemporal() + ENTER);
					TemporalGenerator.getInstance().releaseTemporal();
					sb.append(TAB + "CALL writeNUM" + ENTER);
				}
			}
		}
	}
}
