package eon8ight.tree.twentyquestions;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import eon8ight.tree.*;

public class TreeEditor extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -1076249736085221978L;
	private final Dimension BUTTON_DIM = new Dimension(128, 32);
	public String treeFileName;
	private JButton leftButton, rightButton, parentButton;
	private JTextField currentNodeText;
	public BinaryTree<String> tree;
	private TreeNode<String> currentNode;
	
	public TreeEditor()
	{
		while(treeFileName == null || treeFileName.isEmpty())
			treeFileName = javax.swing.JOptionPane.showInputDialog("Enter filename.");
		
		try
		{
			System.out.println("Loading tree...");
			tree = GameIO.loadFile(treeFileName);
		}
		catch(FileNotFoundException e)
		{
			tree = new BinaryTree<String>(new TreeNode<String>("..."));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		currentNode = tree.getRoot();
		
		if(currentNode.getLeft() != null)
			leftButton = new JButton(currentNode.getLeft().toString());
		else
			leftButton = new JButton("...");
		
		leftButton.setPreferredSize(new Dimension(BUTTON_DIM));
		leftButton.setToolTipText("Left Child Node [YES]");
		leftButton.addActionListener(this);
		
		if(currentNode.getRight() != null)
			rightButton = new JButton(currentNode.getRight().toString());
		else
			rightButton = new JButton("...");
		
		rightButton.setPreferredSize(new Dimension(BUTTON_DIM));
		rightButton.setToolTipText("Right Child Node [NO]");
		rightButton.addActionListener(this);
		
		parentButton = new JButton("...");
		parentButton.setPreferredSize(new Dimension(BUTTON_DIM));
		parentButton.setToolTipText("Parent Node");
		parentButton.addActionListener(this);
		
		currentNodeText = new JTextField(currentNode.toString());
		currentNodeText.setPreferredSize(new Dimension(BUTTON_DIM));
		currentNodeText.setToolTipText("Current Node");
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 1;
		c.gridy = 0;
		add(parentButton, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(leftButton, c);
		
		c.gridx = 1;
		c.gridy = 1;
		add(currentNodeText, c);
		
		c.gridx = 2;
		c.gridy = 2;
		add(rightButton, c);
		
		System.out.println("\nPress [Delete] to delete the current node (must be a leaf).");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton source = (JButton)e.getSource();
		String s = currentNodeText.getText();
		
		if(!s.endsWith("?"))
			s += "?";
		
		if(source.equals(leftButton))
		{
			if(!s.isEmpty())
				currentNode.setValue(s);
			else
				currentNode.setValue("...");
			
			TreeNode<String> left = currentNode.getLeft();
			
			if(left == null)
			{
				left = new TreeNode<String>("...");
				currentNode.setLeft(left);
			}
			
			currentNode = left;
			
		}
		else if(source.equals(rightButton))
		{
			if(!s.isEmpty())
				currentNode.setValue(s);
			else
				currentNode.setValue("...");
			
			TreeNode<String> right = currentNode.getRight();
			
			if(right == null)
			{
				right = new TreeNode<String>("...");
				currentNode.setRight(right);
			}
			
			currentNode = right;
		}
		else if(source.equals(parentButton))
		{
			if(!s.isEmpty())
				currentNode.setValue(s);
			else
				currentNode.setValue("...");
			
			TreeNode<String> parent = tree.getParent(currentNode);
			
			if(parent == null)
				return;
			
			String parentS = currentNodeText.getText();
			
			if(!parentS.endsWith("?"))
				currentNode.setValue(currentNodeText.getText() + "?");
			else
				currentNode.setValue(currentNodeText.getText());
			
			currentNode = parent;
		}
		else
			return;
		
		if(currentNode.getLeft() != null)
			leftButton.setText(currentNode.getLeft().toString());
		else
			leftButton.setText("...");
		
		if(currentNode.getRight() != null)
			rightButton.setText(currentNode.getRight().toString());
		else
			rightButton.setText("...");
		
		if(tree.getParent(currentNode) != null)
			parentButton.setText(tree.getParent(currentNode).toString());
		else
			parentButton.setText("...");
		
		currentNodeText.setText(currentNode.toString());
		TreeEditorRunner.frame.pack();
		requestFocus();
	}
	
	@SuppressWarnings("unchecked")
	public void deleteCurrentNode()
	{
		if(currentNode.isLeaf() && !currentNode.equals(tree.getRoot()))
		{
			TreeNode<String> oldNode = (TreeNode<String>)currentNode.clone();
			TreeNode<String> parent = tree.getParent(currentNode);
			currentNode = parent;
			
			if(currentNode.getLeft().toString().equals(oldNode.toString()))
			{
				leftButton.setText("...");
				currentNode.setLeft(null);
			}
			else
				leftButton.setText(currentNode.getLeft().toString());
			
			if(currentNode.getRight().toString().equals(oldNode.toString()))
			{
				rightButton.setText("...");
				currentNode.setRight(null);
			}
			else
				rightButton.setText(currentNode.getRight().toString());
			
			if(tree.getParent(currentNode) != null)
				parentButton.setText(tree.getParent(currentNode).toString());
			else
				parentButton.setText("...");
			
			currentNodeText.setText(currentNode.toString());
			TreeEditorRunner.frame.pack();
			requestFocus();
		}
		else
			System.out.println("\nThe node you are trying to delete must be a leaf.");
	}
}