/*
 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
 http://vivin.net/2010/01/30/generic-n-ary-tree-in-java/
*/

package ar.edu.caece.pl.asem.model.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;


public class TreeTest {

    @Test
    public void TestRootIsNullOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();
        assertNull(tree.getRoot());
    }

    @Test
    public void TestNumberOfNodesIsZeroOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();
        assertEquals(tree.getNumberOfNodes(), 0);
    }

    @Test
    public void TestIsEmptyIsTrueOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void TestExistsIsFalseOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();
        String dataToFind = "";

        assertFalse(tree.exists(dataToFind));
    }

    @Test
    public void TestFindReturnsNullOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();
        String dataToFind = "";

        assertNull(tree.find(dataToFind));
    }

    @Test
    public void TestPreOrderBuildReturnsNullListOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();

        assertNull(tree.build(Tree.PRE_ORDER));
    }

    @Test
    public void TestPostOrderBuildReturnsNullListOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();

        assertNull(tree.build(Tree.POST_ORDER));
    }

    @Test
    public void TestPreOrderBuildWithDepthReturnsNullMapOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();

        assertNull(tree.buildWithDepth(Tree.PRE_ORDER));
    }

    @Test
    public void TestPostOrderBuildWithDepthReturnsNullMapOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();

        assertNull(tree.buildWithDepth(Tree.POST_ORDER));
    }

    @Test
    public void TestToStringReturnsEmptyStringOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();

        assertEquals(tree.toString(), "");
    }

    @Test
    public void TestToStringWithDepthReturnsEmptyStringOnNewTreeCreation() {
        Tree<String> tree = new Tree<String>();

        assertEquals(tree.toStringWithDepth(), "");
    }

    @Test
    public void TestSetRootGetRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertNotNull(tree.getRoot());
    }

    @Test
    public void TestNumberOfNodesIsOneWithNonNullRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertEquals(tree.getNumberOfNodes(), 1);
    }

    @Test
    public void TestEmptyIsFalseWithNonNullRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertFalse(tree.isEmpty());
    }

    @Test
    public void TestPreOrderBuildListSizeIsOneWithNonNullRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertEquals(tree.build(Tree.PRE_ORDER).size(), 1);
    }

    @Test
    public void TestPostOrderBuildListSizeIsOneWithNonNullRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertEquals(tree.build(Tree.POST_ORDER).size(), 1);
    }

    @Test
    public void TestPreOrderBuildWithDepthSizeIsOneWithNonNullRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertEquals(tree.buildWithDepth(Tree.PRE_ORDER).size(), 1);
    }

    @Test
    public void TestPostOrderBuildWithDepthSizeIsOneWithNonNullRoot() {
        Tree<String> tree = new Tree<String>();
        TreeNode root = new TreeNode(new TreeDataImpl("root"));
        tree.setRoot(root);

        assertEquals(tree.buildWithDepth(Tree.POST_ORDER).size(), 1);
    }

    /*
      Tree looks like:
          A
         / \
        B  C
            \
             D

      For the following tests

     */
    @Test
    public void TestNumberOfNodes() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        assertEquals(tree.getNumberOfNodes(), 4);
        
        System.out.print(tree.toString());
    }

    @Test
    public void TestExistsReturnsTrue() {
        Tree<String> tree = new Tree<String>();
        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        String dataToFindD = "D";

        assertTrue(tree.exists(dataToFindD));
    }

    @Test
    public void TestFindReturnsNonNull() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        String dataToFindD = "D";

        assertNotNull(tree.find(dataToFindD));
    }

    @Test
    public void TestExistsReturnsFalse() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        String dataToFindE = "E";

        assertFalse(tree.exists(dataToFindE));
    }

    @Test
    public void TestFindReturnsNull() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        String dataToFindE = "E";

        assertNull(tree.find(dataToFindE));
    }

    // Pre-order traversal will give us A B C D
    @Test
    public void TestPreOrderBuild() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        List<TreeNode> preOrderList = new ArrayList<TreeNode>();
        preOrderList.add(new TreeNode(new TreeDataImpl("A")));
        preOrderList.add(new TreeNode(new TreeDataImpl("B")));
        preOrderList.add(new TreeNode(new TreeDataImpl("C")));
        preOrderList.add(new TreeNode(new TreeDataImpl("D")));

        // Instead of checking equalities on the lists themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.build(Tree.PRE_ORDER).toString(), preOrderList.toString());
    }

    //Post-order traversal will give us B D C A
    @Test
    public void TestPostOrderBuild() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        List<TreeNode> postOrderList = new ArrayList<TreeNode>();
        postOrderList.add(new TreeNode(new TreeDataImpl("B")));
        postOrderList.add(new TreeNode(new TreeDataImpl("D")));
        postOrderList.add(new TreeNode(new TreeDataImpl("C")));
        postOrderList.add(new TreeNode(new TreeDataImpl("A")));

        // Instead of checking equalities on the lists themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.build(Tree.POST_ORDER).toString(), postOrderList.toString());
     }

    //Pre-order traversal with depth will give us A:0, B:1, C:1, D:2
    @Test
    public void TestPreOrderBuildWithDepth() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        Map<TreeNode, Integer> preOrderMapWithDepth = new LinkedHashMap<TreeNode, Integer>();
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("A")), 0);
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("B")), 1);
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("C")), 1);
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("D")), 2);

        // Instead of checking equalities on the maps themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.buildWithDepth(Tree.PRE_ORDER).toString(), preOrderMapWithDepth.toString());
     }

     //Post-order traversal with depth will give us B:1, D:2, C:1, A:0
    @Test
    public void TestPostOrderBuildWithDepth() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        Map<TreeNode, Integer> postOrderMapWithDepth = new LinkedHashMap<TreeNode, Integer>();
        postOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("B")), 1);
        postOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("D")), 2);
        postOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("C")), 1);
        postOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("A")), 0);

        // Instead of checking equalities on the maps themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.buildWithDepth(Tree.POST_ORDER).toString(), postOrderMapWithDepth.toString());
    }

    //toString and toStringWithDepth both use pre-order traversal
    @Test
    public void TestToString() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        String expected = "A\n  B\n  C\n    D";
        assertEquals(expected,tree.toString());
    }

    @Test
    public void TestToStringWithDepth() {
        Tree<String> tree = new Tree<String>();

        TreeNode rootA = new TreeNode(new TreeDataImpl("A"));
        TreeNode childB = new TreeNode(new TreeDataImpl("B"));
        TreeNode childC = new TreeNode(new TreeDataImpl("C"));
        TreeNode childD = new TreeNode(new TreeDataImpl("D"));

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        Map<TreeNode, Integer> preOrderMapWithDepth = new LinkedHashMap<TreeNode, Integer>();
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("A")), 0);
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("B")), 1);
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("C")), 1);
        preOrderMapWithDepth.put(new TreeNode(new TreeDataImpl("D")), 2);

        assertEquals(tree.toStringWithDepth(), preOrderMapWithDepth.toString());
       
        //System.out.print(tree.toStringWithDepth());
    }

	class TreeDataImpl implements TreeData {
		private TreeNode myNode;
		private String data;
		
		public TreeDataImpl(String data) {
			this.data = data;
		}
		
		public List<TreeDataImpl> getChildren() {
			List<TreeDataImpl> children = new ArrayList<TreeDataImpl>();
			for(TreeNode childNode : this.myNode.getChildren()) {
				children.add((TreeDataImpl)childNode.getData());
			}
			return children;
		}

		public AbstractElement getParent() {
			return (AbstractElement)this.myNode.getParent().getData();
		}

		@Override
		public void setNode(TreeNode tn) {
			this.myNode = tn;
		}
		
		@Override
		public String toString() {
			return this.data;
		}
		
	}
}
