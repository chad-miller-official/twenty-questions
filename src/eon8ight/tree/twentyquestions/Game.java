package eon8ight.tree.twentyquestions;

import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import eon8ight.tree.*;

public class Game implements Runnable
{
	public static final String YES = "YES";
	public static final String NO = "NO";
	private boolean running;
	private String fileName;
	private BinaryTree<String> tree;
	private TreeNode<String> currentNode;
	
	public Game()
	{
		running = true;
		
		System.out.println("================");
		System.out.println("= 20 QUESTIONS =");
		System.out.println("================");
		
		while(fileName == null || fileName.isEmpty())
		{
			fileName = JOptionPane.showInputDialog("Enter filename.");
			
			try
			{
				tree = GameIO.loadFile(fileName);
			}
			catch(FileNotFoundException e)
			{
				fileName = null;
				System.err.println("Could not find res/" + fileName + ".dat.");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		currentNode = tree.getRoot();
	}
	
	@Override
	public void run()
	{
		Scanner answer = new Scanner(System.in);
		
		while(running)
		{
			System.out.println(currentNode);
			String response = answer.next();
			
			if(response.toUpperCase().equals(YES))
			{
				if(currentNode.isLeaf())
				{
					System.out.println("I guessed correctly! I win!");
					playAgain();
				}
				else
				{
					if(currentNode.getLeft() == null)
					{
						System.out.println("I guessed incorrectly! You win!");
						replaceCurrentNode();
						playAgain();
					}
					else
						currentNode = currentNode.getLeft();
				}
			}
			else if(response.toUpperCase().equals(NO))
			{
				if(currentNode.isLeaf())
				{
					System.out.println("I guessed incorrectly! You win!");
					replaceCurrentNode();
					playAgain();
				}
				else
				{
					if(currentNode.getRight() == null)
					{
						System.out.println("I guessed incorrectly! You win!");
						playAgain();
					}
					else
						currentNode = currentNode.getRight();
				}
			}
		}
		
		answer.close();
		System.out.println("===========");
		System.out.println("= GOODBYE =");
		System.out.println("===========");
		System.exit(0);
	}
	
	private void playAgain()
	{
		int cont = JOptionPane.showConfirmDialog(null, "Play again? (Loads same 20Q file.)", "Play again?", JOptionPane.YES_NO_OPTION);
		running = (cont == JOptionPane.YES_OPTION);
		currentNode = tree.getRoot();
	}
	
	@SuppressWarnings("unchecked")
	private void replaceCurrentNode()
	{
		String newQuestion = JOptionPane.showInputDialog("Enter a new question.");
		String newLeft = JOptionPane.showInputDialog("Enter a thing that would be a \"yes\" answer to that question.");
		TreeNode<String> newRight = (TreeNode<String>)currentNode.clone();
		
		if(!newQuestion.endsWith("?"))
			newQuestion += "?";
		
		if(!newLeft.endsWith("?"))
			newLeft += "?";
		
		currentNode.setValue(newQuestion);
		currentNode.setRight(newRight);
		currentNode.setLeft(new TreeNode<String>(newLeft));
		
		try
		{
			GameIO.saveFile(tree, fileName);
		}
		catch(Exception e)
		{
			System.err.println("Could not save file.");
		}
	}
	
	public static void main(String[] args)
	{
		new Thread(new Game()).start();
	}
}