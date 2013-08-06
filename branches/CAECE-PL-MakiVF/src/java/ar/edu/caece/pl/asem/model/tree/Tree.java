/*
 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
 http://vivin.net/2010/01/30/generic-n-ary-tree-in-java/
 */

package ar.edu.caece.pl.asem.model.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tree<T> {

	public static final int PRE_ORDER = -1;
	public static final int POST_ORDER = 1;

	private TreeNode root;

	public Tree() {
		super();
	}

	public TreeNode getRoot() {
		return this.root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public int getNumberOfNodes() {
		int numberOfNodes = 0;

		if (root != null) {
			numberOfNodes = auxiliaryGetNumberOfNodes(root) + 1; // 1 for the
																	// root!
		}

		return numberOfNodes;
	}

	private int auxiliaryGetNumberOfNodes(TreeNode node) {
		int numberOfNodes = node.getNumberOfChildren();

		for (TreeNode child : node.getChildren()) {
			numberOfNodes += auxiliaryGetNumberOfNodes(child);
		}

		return numberOfNodes;
	}

	public boolean exists(T dataToFind) {
		return (find(dataToFind) != null);
	}

	public TreeNode find(T dataToFind) {
		TreeNode returnNode = null;

		if (root != null) {
			returnNode = auxiliaryFind(root, dataToFind);
		}

		return returnNode;
	}

	private TreeNode auxiliaryFind(TreeNode currentNode, T dataToFind) {
		TreeNode returnNode = null;
		int i = 0;

		if (currentNode.getData().equals(dataToFind)) {
			returnNode = currentNode;
		}

		else if (currentNode.hasChildren()) {
			i = 0;
			while (returnNode == null && i < currentNode.getNumberOfChildren()) {
				returnNode = auxiliaryFind(currentNode.getChildAt(i),
						dataToFind);
				i++;
			}
		}

		return returnNode;
	}

	public boolean isEmpty() {
		return (root == null);
	}

	public List<TreeNode> build(int traversalOrder) {
		List<TreeNode> returnList = null;

		if (root != null) {
			returnList = build(root, traversalOrder);
		}

		return returnList;
	}

	public List<TreeNode> build(TreeNode node, int traversalOrder) {
		List<TreeNode> traversalResult = new ArrayList<TreeNode>();

		if (traversalOrder == PRE_ORDER) {
			buildPreOrder(node, traversalResult);
		}

		else if (traversalOrder == POST_ORDER) {
			buildPostOrder(node, traversalResult);
		}

		return traversalResult;
	}

	private void buildPreOrder(TreeNode node,
			List<TreeNode> traversalResult) {
		traversalResult.add(node);

		for (TreeNode child : node.getChildren()) {
			buildPreOrder(child, traversalResult);
		}
	}

	private void buildPostOrder(TreeNode node,
			List<TreeNode> traversalResult) {
		for (TreeNode child : node.getChildren()) {
			buildPostOrder(child, traversalResult);
		}

		traversalResult.add(node);
	}

	public Map<TreeNode, Integer> buildWithDepth(int traversalOrder) {
		Map<TreeNode, Integer> returnMap = null;

		if (root != null) {
			returnMap = buildWithDepth(root, traversalOrder);
		}

		return returnMap;
	}

	public Map<TreeNode, Integer> buildWithDepth(TreeNode node,
			int traversalOrder) {
		Map<TreeNode, Integer> traversalResult = new LinkedHashMap<TreeNode, Integer>();

		if (traversalOrder == PRE_ORDER) {
			buildPreOrderWithDepth(node, traversalResult, 0);
		}

		else if (traversalOrder == POST_ORDER) {
			buildPostOrderWithDepth(node, traversalResult, 0);
		}

		return traversalResult;
	}

	private void buildPreOrderWithDepth(TreeNode node,
			Map<TreeNode, Integer> traversalResult, int depth) {
		traversalResult.put(node, depth);

		for (TreeNode child : node.getChildren()) {
			buildPreOrderWithDepth(child, traversalResult, depth + 1);
		}
	}

	private void buildPostOrderWithDepth(TreeNode node,
			Map<TreeNode, Integer> traversalResult, int depth) {
		for (TreeNode child : node.getChildren()) {
			buildPostOrderWithDepth(child, traversalResult, depth + 1);
		}

		traversalResult.put(node, depth);
	}

	/*
	 * public String toString() {
	 * 
	 * // We're going to assume a pre-order traversal by default
	 * 
	 * 
	 * String stringRepresentation = "";
	 * 
	 * if(root != null) { stringRepresentation = build(PRE_ORDER).toString();
	 * 
	 * }
	 * 
	 * return stringRepresentation; }
	 */

	public String toString() {

		StringBuilder sb = new StringBuilder();
		if (root != null) {
			sb.append(root.toString());

			printChildren(sb, root, 1);
			return sb.toString();
		} else {
			return "";
		}

	}

	private void printChildren(StringBuilder sb, TreeNode node, int tab) {
		for (TreeNode child : node.getChildren()) {
			sb.append("\n");
			for (int i = 0; i < tab; i++) {
				sb.append("  ");
			}
			sb.append(child.toString());
			this.printChildren(sb, child, tab + 1);
		}
	}

	//TODO inieto: revisar, Abstract Element no tiene que ser conocido por el Tree, que es genérico
//	public void fillData(TreeNode node) {
//		List<AbstractElement> children = new ArrayList<AbstractElement>();
//		if (node.getNumberOfChildren() != 0) {
//			for (TreeNode child : node.getChildren()) {
//				children.add((AbstractElement) child.getData());
//				this.fillData(child);
//			}
//			((AbstractElement) node.getData()).setChildren(children);
//		}
//	}

	public String toStringWithDepth() {
		/*
		 * We're going to assume a pre-order traversal by default
		 */

		String stringRepresentation = "";

		if (root != null) {
			stringRepresentation = buildWithDepth(PRE_ORDER).toString();
		}

		return stringRepresentation;
	}

	@Override
	public boolean equals(Object obj) {

		// Validar que obj sea un arbol
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Tree<?> other = (Tree<?>) obj;

		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;

		TreeNode yo = this.root;
		TreeNode otro = other.getRoot();

		// El equals de cada nodo compara su elemento dato sin el hijo.
		return yo.equals(otro) && compareChildren(yo, otro);
	}

	private boolean compareChildren(TreeNode yo, TreeNode otro) {

		boolean result = true;

		// Si tenemos distinta cantidad de hijos devuelve false
		if (yo.getChildren().size() != otro.getChildren().size())
			return false;

		for (int i = 0; i < yo.getChildren().size() && result; i++) {

			TreeNode miHijo = yo.getChildAt(i);
			TreeNode elHijoDelOtro = otro.getChildAt(i);

			result &= miHijo.equals(elHijoDelOtro) && // comparo elemtno dato de
														// ese nivel y llamo
														// recursivamente
					compareChildren(miHijo, elHijoDelOtro); // comparar Nietos
		}
		return result;
	}

}
