package gui;

import javax.swing.JOptionPane;

public class Program 
{
	public static void main(String[] args) 
	{
		MainWindow m;
		try 
		{
			m = new MainWindow();
			m.main(args);
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println(e.getStackTrace());
		}
	}
}
