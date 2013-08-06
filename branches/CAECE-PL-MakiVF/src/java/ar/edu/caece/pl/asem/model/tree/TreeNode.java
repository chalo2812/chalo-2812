/*
Copyright 2010 Visin Suresh Paliath
Distributed under the BSD license
http://vivin.net/2010/01/30/generic-n-ary-tree-in-java/
 */

package ar.edu.caece.pl.asem.model.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rits.cloning.Cloner;

public class TreeNode {

	private TreeData data;
	private List<TreeNode> children;
	private TreeNode parent;

	public TreeNode() {
		super();
		children = new ArrayList<TreeNode>();
	}

	public TreeNode(TreeData data) {
		this();
		setData(data);
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public List<TreeNode> getChildren() {
		return this.children;
	}

	public int getNumberOfChildren() {
		return getChildren().size();
	}

	public boolean hasChildren() {
		return (getNumberOfChildren() > 0);
	}

	public void setChildren(List<TreeNode> children) {
		for (TreeNode child : children) {
			child.parent = this;
		}

		this.children = children;
	}

	public void addChild(TreeNode child) {
		child.parent = this;
		children.add(child);
	}

	public void addChildAt(int index, TreeNode child)
			throws IndexOutOfBoundsException {
		child.parent = this;
		children.add(index, child);
	}

	public void removeChildren() {
		this.children = new ArrayList<TreeNode>();
	}

	public void removeChildAt(int index) throws IndexOutOfBoundsException {
		children.remove(index);
	}

	public TreeNode getChildAt(int index) throws IndexOutOfBoundsException {
		return children.get(index);
	}

	public TreeData getData() {
		return this.data;
	}

	public void setData(TreeData data) {
		Cloner cloner = new Cloner();			//TODO inieto revisar si no hay forma de sacar el jar invasor
		this.data = cloner.deepClone(data);
		this.data.setNode(this);
	}

	public String toString() {
		if (getData() != null) {
			return getData().toString();
		} else {
			return "";
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TreeNode other = (TreeNode) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	public String toStringVerbose() {
		String stringRepresentation = getData().toString() + ":[";

		for (TreeNode node : getChildren()) {
			stringRepresentation += node.getData().toString() + ", ";
		}

		// Pattern.DOTALL causes ^ and $ to match. Otherwise it won't. It's
		// retarded.
		Pattern pattern = Pattern.compile(", $", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(stringRepresentation);

		stringRepresentation = matcher.replaceFirst("");
		stringRepresentation += "]";

		return stringRepresentation;
	}
}
