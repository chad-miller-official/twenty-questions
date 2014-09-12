package eon8ight.tree.twentyquestions;

import java.io.*;
import eon8ight.tree.*;

public final class GameIO
{
	public static final String PREFIX = "res/";
	public static final String SUFFIX = ".dat";
	public static final String HEADER = "20QUESTIONS";
	
	public static void saveFile(BinaryTree<String> tree, String fileName) throws FileNotFoundException, IOException
	{
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(PREFIX + fileName + SUFFIX));
		output.writeObject(HEADER);
		output.writeObject(tree);
		output.close();
	}
	
	@SuppressWarnings("unchecked")
	public static BinaryTree<String> loadFile(String fileName) throws ClassNotFoundException, FileNotFoundException, IOException
	{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(PREFIX + fileName + SUFFIX));
		Object obj = input.readObject();
		
		if(obj instanceof String)
		{
			if(!((String)obj).contains(HEADER))
			{
				input.close();
				throw new IOException("Could not load " + fileName + ".\n");
			}
			else
			{
				BinaryTree<String> tree = (BinaryTree<String>) input.readObject();
				input.close();
				return tree;
			}
		}
		else
		{
			input.close();
			throw new IOException("Could not load " + fileName + ".\n");
		}
	}
}