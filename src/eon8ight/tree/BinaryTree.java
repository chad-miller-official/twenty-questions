package eon8ight.tree;

import java.io.Serializable;
import java.util.ArrayList;

public class BinaryTree<E> implements Serializable, Cloneable
{
	private static final long serialVersionUID = 5775027136738890250L;
	private TreeNode<E> root;
	
	public BinaryTree(TreeNode<E> root)
	{
		this.root = root;
	}
	
	public TreeNode<E> getRoot()
	{
		return root;
	}

	public int numNodes()
	{
		return numNodesHelper(root);
	}

	private int numNodesHelper(TreeNode<E> root)
	{
		return (root == null) ? 0 : (numNodesHelper(root.getLeft()) + numNodesHelper(root.getRight()) + 1);
	}

	public int numLeaves()
	{
		return numLeavesHelper(root);
	}

	private int numLeavesHelper(TreeNode<E> root)
	{
		if(root.getLeft() == null && root.getRight() == null)
			return 1;
		else
		{
			int temp = 0;
			
			if(root.getLeft() != null)
				temp += numLeavesHelper(root.getLeft());
				
			if(root.getRight() != null)
				temp += numLeavesHelper(root.getRight());
			
			return temp;
		}
	}

	public int height()
	{
		return heightHelper(root);
	}

	private int heightHelper(TreeNode<E> root)
	{
		if(root == null)
			return 0;
		else
		{
			int left = heightHelper(root.getLeft()) + 1;
			int right = heightHelper(root.getRight()) + 1;
			
			return (left > right) ? left : right;
		}
	}

	public int width()
	{
		return widthHelper(root);
	}

	private int widthHelper(TreeNode<E> root)
	{
		if(root == null)
			return 0;
		
		int leftWidth = widthHelper(root.getLeft());
		int rightWidth = widthHelper(root.getRight());
		int width = heightHelper(root.getLeft()) + heightHelper(root.getRight()) + 1;
		
		return Math.max(Math.max(leftWidth, rightWidth), width);
	}

	public boolean isDescendant(E ancestor, E descendant)
	{
		return (isDescendantHelper(isDescendantHelper(root, ancestor), descendant) != null);
	}
	
	private TreeNode<E> isDescendantHelper(TreeNode<E> root, E toFind)
	{
		if(root != null && root.getValue() == toFind)
			return root;
		else
		{
			TreeNode<E> one = null;
			TreeNode<E> two = null;
			
			if(root.getLeft() != null)
				one = isDescendantHelper(root.getRight(), toFind);
			
			if(root.getRight() != null)
				two = isDescendantHelper(root.getLeft(), toFind);
			
			if(one != null)
				return one;
			else if(two != null)
				return two;
			else
				return null;
		}
	}

	public boolean isFull()
	{
		return (root == null) ? true : isFullHelper(root);
	}

	private boolean isFullHelper(TreeNode<E> root)
	{
		if(root.getLeft() == null && root.getRight() == null)
			return true;
		else if(root.getLeft() != null && root.getRight() != null)
			return (isFullHelper(root.getLeft()) && isFullHelper(root.getRight()));
		else
			return false;
	}

	public void clear()
	{
		root = null;
	}
	
	public TreeNode<E> getParent(TreeNode<E> node)
	{
		return (node.equals(root)) ? null : getParentHelper(root, node);
	}
	
	private TreeNode<E> getParentHelper(TreeNode<E> node, TreeNode<E> targetNode)
	{
		if(node == null)
			return null;
		else
		{
			if((node.getLeft() != null && node.getLeft().equals(targetNode)) || (node.getRight() != null && node.getRight().equals(targetNode)))
				return node;
			else
			{
				TreeNode<E> leftBranch = getParentHelper(node.getLeft(), targetNode);
				TreeNode<E> rightBranch = getParentHelper(node.getRight(), targetNode);
				
				if(leftBranch != null && rightBranch == null)
					return leftBranch;
				else if(leftBranch == null && rightBranch != null)
					return rightBranch;
				else
					return null;
			}
		}
	}

	public String preOrder()
	{
		String s = preOrderHelper(root);
		
		for(int i = 0; i < s.length() - 2; i++)
			if(s.charAt(i) == ',' && Character.isWhitespace(s.charAt(i + 1)) && !Character.isLetterOrDigit(s.charAt(i + 2)))
			{
				s = s.substring(0, i) + s.substring(i + 2);
				
				if(i >= 2)
					i -= 2;
			}
		
		s = s.substring(0, s.length() - 2);
		return "[" + s + "]";
	}

	private String preOrderHelper(TreeNode<E> root)
	{
		return (root == null) ? "" : (root.getValue().toString() + ", " + preOrderHelper(root.getLeft()) + ", " + preOrderHelper(root.getRight()) + ", ");
	}

	public String postOrder()
	{
		String s = postOrderHelper(root);
		
		for(int i = 0; i < s.length() - 2; i++)
			if(s.charAt(i) == ',' && Character.isWhitespace(s.charAt(i + 1)) && !Character.isLetterOrDigit(s.charAt(i + 2)))
			{
				s = s.substring(0, i) + s.substring(i + 2);
				
				if(i >= 2)
					i -= 2;
			}
		
		s = s.substring(2, s.length() - 2);
		return "[" + s + "]";
	}

	private String postOrderHelper(TreeNode<E> root)
	{
		return (root == null) ? "" : (postOrderHelper(root.getLeft()) + ", " + postOrderHelper(root.getRight()) + ", " + root.getValue().toString() + ", ");
	}

	public String levelOrderTraversal()
	{
		if(root != null)
		{
			ArrayList<TreeNode<E>> overall = new ArrayList<TreeNode<E>>();
			ArrayList<TreeNode<E>> tempOne = new ArrayList<TreeNode<E>>();
			ArrayList<TreeNode<E>> tempTwo = new ArrayList<TreeNode<E>>();
			tempOne.add(root);
			
			while(tempOne.size() > 0)
			{
				for(TreeNode<E> node : tempOne)
				{
					if(node.getLeft() != null)
						tempTwo.add(node.getLeft());
					
					if(node.getRight() != null)
						tempTwo.add(node.getRight());
				}
				
				overall.addAll(tempOne);
				tempOne = tempTwo;
				tempTwo = new ArrayList<TreeNode<E>>();
			}
			
			return overall.toString();
		}
		else
			return "[]";
	}

	@Override
	public String toString()
	{
		String s = toStringHelper(root);
		
		for(int i = 0; i < s.length() - 2; i++)
			if(s.charAt(i) == ',' && Character.isWhitespace(s.charAt(i + 1)) && !Character.isLetterOrDigit(s.charAt(i + 2)))
			{
				s = s.substring(0, i) + s.substring(i + 2);
				
				if(i >= 2)
					i -= 2;
			}
		
		s = s.substring(2, s.length() - 2);
		return "[" + s + "]";
	}

	private String toStringHelper(TreeNode<E> root)
	{
		//in-order traversal
		return (root == null) ? "" : (toStringHelper(root.getLeft()) + ", " + root.getValue().toString() + ", " + toStringHelper(root.getRight()) + ", ");
	}
	
	@Override
	public Object clone()
	{
		return new BinaryTree<E>(root);
	}
}