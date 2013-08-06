package ar.edu.caece.pl.asem.model.impl.treeelements;

import java.util.ArrayList;
import java.util.List;

import ar.edu.caece.pl.asem.model.tree.TreeData;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.impl.ErrorManager;

/**
 * AbstractElement, es la clase genérica de la que extienden todos los elementos del árbol
 * Define la interfaz compile() que los específicos deberán sobrescribir
 * Implementa TreeData para poder conocer al Nodo que lo contiene (para poder navegar)
 * Implementa Clonable para clonarse al setearse en el árbol? (ver TreeNode.setData(TreeData data))
 * @author inieto
 *
 */
public abstract class AbstractElement implements TreeData, Cloneable {
	
	protected static final String TAB = "\t";
	protected static final String ENTER = "\n";
	protected static final int REGISTER_SIZE = 2; //2 bytes/reg en el 8086
	
	private TreeNode myNode;
	protected String label;
	protected String place;
	protected String envName;
	protected String name;

	public abstract boolean equals(Object obj);

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return this.getLabel();
	}

	public List<AbstractElement> getChildren() {
		List<AbstractElement> children = new ArrayList<AbstractElement>();
		for(TreeNode childNode : this.myNode.getChildren()) {
			children.add((AbstractElement)childNode.getData());
		}
		return children;
	}

	public AbstractElement getParent() {
		return (AbstractElement)this.myNode.getParent().getData();
	}

	public void compile(StringBuilder sb) {
		// Acumulo el código ASM de todos los childs
		for (AbstractElement child : this.getChildren()) {
			if (ErrorManager.debugEnabled) sb.append("; Child Init - " + child.label + " - " + child.getEnvName() + ENTER);
			child.compile(sb);
			if (ErrorManager.debugEnabled) sb.append("; Child End - " + child.label + " - " + child.getEnvName() + ENTER);
			
			
		}
	}

	public String getPlace() {
		return "";
	}

	public String getEnvName() {
		return this.envName != null? this.envName : this.getParent().getEnvName();
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void setNode(TreeNode tn) {
		this.myNode = tn;
	}
}
