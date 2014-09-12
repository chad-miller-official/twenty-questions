package eon8ight.tree;

import java.io.Serializable;

public final class TreeNode<E> implements Serializable, Cloneable
{
	private static final long serialVersionUID = 4405070037735298622L;
	private E value;
	private TreeNode<E> left;
	private TreeNode<E> right;

	public TreeNode(E value, TreeNode<E> left, TreeNode<E> right)
	{ 
		this.value = value; 
		this.left = left; 
		this.right = right;
	}
	
	public TreeNode(E value)
	{
		this.value = value;
		left = right = null;
	}
	
	public E getValue()
	{
		return value;
	}
	
	public TreeNode<E> getLeft()
	{
		return left;
	}
	
	public TreeNode<E> getRight()
	{
		return right;
	}
	
	public void setValue(E value)
	{
		this.value = value;
	}
	
	public void setLeft(TreeNode<E> left)
	{
		this.left = left;
	}
	
	public void setRight(TreeNode<E> right)
	{
		this.right = right;
	}
	
	public boolean isLeaf()
	{
		return (left == null && right == null);
	}
	
	@Override
	public String toString()
	{
		return value.toString();
	}
	
	@Override
	public Object clone()
	{
		return new TreeNode<E>(value, left, right);
	}
} 