package eon8ight.tree.twentyquestions;

import java.awt.event.*;

import javax.swing.JFrame;

public class TreeEditorRunner implements KeyListener, Runnable
{
	public static JFrame frame = new JFrame("20Q Tree Editor");
	public TreeEditor editor = new TreeEditor();
	
	@Override
	public void run()
	{
		editor.addKeyListener(this);
		frame.add(editor);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add an anonymous listener to handle file-saving upon closing
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				try
				{
					GameIO.saveFile(editor.tree, editor.treeFileName);
					System.out.println("\nSaved tree.");
					System.exit(0);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_DELETE)
			editor.deleteCurrentNode();
		
		e.consume();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		e.consume();
	}
	
	public static void main(String[] args)
	{
		new Thread(new TreeEditorRunner()).start();
	}
}