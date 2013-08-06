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
import java.util.List;

import org.junit.Test;

import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;

public class TreeNodeTest {
    @Test
    public void TestNodeDataIsNullOnNewNodeCreation() {
        TreeNode node = new TreeNode();
        assertNull(node.getData());
    }

    @Test
    public void TestNodeHasNonNullChildrenListOnNewNodeCreation() {
        TreeNode node = new TreeNode();
        assertNotNull(node.getChildren());
    }

    @Test
    public void TestNodeHasZeroChildrenOnNewNodeCreation() {
        TreeNode node = new TreeNode();
        assertEquals(node.getNumberOfChildren(), 0);
    }

    @Test
    public void TestNodeHasChildrenReturnsFalseOnNewNodeCreation() {
        TreeNode node = new TreeNode();
        assertFalse(node.hasChildren());
    }

    @Test
    public void TestNodeDataIsNonNullWithParameterizedConstructor() {
        TreeNode node = new TreeNode(new TreeDataImpl("I haz data"));
        assertNotNull(node.getData());
    }

    @Test
    public void TestNodeSetAndGetData() {
        TreeNode node = new TreeNode();
        String data = "data";
        node.setData(new TreeDataImpl(data));
        assertEquals(node.getData(), data);
    }

    @Test
    public void TestNodeSetAndGetChildren() {
        TreeNode node = new TreeNode();
        TreeNode child = new TreeNode();

        List<TreeNode> children = new ArrayList<TreeNode>();
        children.add(child);

        node.setChildren(children);
        assertEquals(node.getChildren(), children);
    }

    @Test
    public void TestNodeSetAndGetChildrenHasCorrectParent() {
        TreeNode node = new TreeNode();
        TreeNode child = new TreeNode();

        List<TreeNode> children = new ArrayList<TreeNode>();
        children.add(child);

        node.setChildren(children);
        assertEquals(node.getChildren(), children);

        for(TreeNode childNode : children) {
            assertEquals(node, childNode.getParent());
        }
    }

    @Test
    public void TestNodeRemoveChildren() {
        TreeNode node = new TreeNode();
        TreeNode child = new TreeNode();

        List<TreeNode> children = new ArrayList<TreeNode>();
        children.add(child);

        node.setChildren(children);
        node.removeChildren();
        assertEquals(node.getChildren().size(), 0);
    }

    @Test
    public void TestNodeAddChildHasCorrectParent() {
        TreeNode node = new TreeNode();
        TreeNode child = new TreeNode();

        node.addChild(child);
        assertEquals(node, child.getParent());
    }

    @Test
    public void TestNodeAddChildHasOneChild() {
        TreeNode node = new TreeNode();
        TreeNode child = new TreeNode();

        node.addChild(child);
        assertEquals(node.getNumberOfChildren(), 1);
    }

    @Test
    public void TestNodeAddChildHasChildrenIsTrue() {
        TreeNode node = new TreeNode();
        TreeNode child = new TreeNode();

        node.addChild(child);
        assertTrue(node.hasChildren());
    }

    @Test
    public void TestNodeAddAndGetChildAt() {
        TreeNode node = new TreeNode(new TreeDataImpl("root"));
        TreeNode child1 = new TreeNode(new TreeDataImpl("child1"));
        TreeNode child2 = new TreeNode(new TreeDataImpl("child2"));

        node.addChild(child1);
        node.addChildAt(1, child2);

        assertEquals(node.getChildAt(1).getData(), child2.getData());
    }

    @Test
    public void TestNodeAddAndRemoveChildAt() {
        TreeNode node = new TreeNode(new TreeDataImpl("root"));
        TreeNode child1 = new TreeNode(new TreeDataImpl("child1"));
        TreeNode child2 = new TreeNode(new TreeDataImpl("child2"));

        node.addChild(child1);
        node.addChildAt(1, child2);

        node.removeChildAt(0);

        assertEquals(node.getNumberOfChildren(), 1);
    }

    @Test
    public void TestNodeToString() {
        TreeNode node = new TreeNode();
        node.setData(new TreeDataImpl("data"));
        assertEquals(node.toString(), "data");
    }

    @Test
    public void TestNodeToStringVerboseNoChildren() {
        TreeNode node = new TreeNode();
        node.setData(new TreeDataImpl("data"));
        assertEquals(node.toStringVerbose(), "data:[]");
    }

    @Test
    public void TestNodeToStringVerboseOneChild() {
        TreeNode node = new TreeNode();
        node.setData(new TreeDataImpl("data"));

        TreeNode child = new TreeNode();
        child.setData(new TreeDataImpl("child"));

        node.addChild(child);
        assertEquals(node.toStringVerbose(), "data:[child]");
    }

    @Test
    public void TestNodeToStringVerboseMoreThanOneChild() {
        TreeNode node = new TreeNode();
        node.setData(new TreeDataImpl("data"));

        TreeNode child1 = new TreeNode();
        child1.setData(new TreeDataImpl("child1"));

        TreeNode child2 = new TreeNode();
        child2.setData(new TreeDataImpl("child2"));

        node.addChild(child1);
        node.addChild(child2);

        assertEquals(node.toStringVerbose(), "data:[child1, child2]");
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
