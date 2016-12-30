package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameWindowListener implements WindowListener
{
	//Setup global variables
	private GameWindow game;
	
	//Constructor
	public GameWindowListener(GameWindow game)
	{
		this.game = game;
	}

	//Called when the window is closing
	public void windowClosing(WindowEvent e) 
	{
		game.exit();
	}
	
	//Not needed at this point
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
