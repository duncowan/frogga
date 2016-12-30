package main;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener
{
	private Point mousePos;
	public boolean mouseClicked = false;
	private MouseEvent mouseEvent;
	public boolean keyPressed = false;
	private KeyEvent keyEvent;
	
	//Constructor
	public InputHandler(Canvas window)
	{
		window.addKeyListener(this);
		window.addMouseListener(this);
		window.addMouseMotionListener(this);
	}

	//Called whenever the mous moves
	public void mouseMoved(MouseEvent e) 
	{
		mousePos = e.getPoint();
	}
	
	//Gets the mouse's location
	public Point getMousePos()
	{
		return this.mousePos;
	}
	
	//Called when a mouse button is pressed
	public void mousePressed(MouseEvent e) 
	{
		mouseClicked = true;
		mouseEvent = e;
	}

	//Called when a mouse button is released
	public void mouseReleased(MouseEvent e) 
	{
		mouseClicked = false;
		mouseEvent = e;
	}
    
	//Used to get the mouse event, which can be used to get the mouses location, what button was clicked and more
	public MouseEvent getMouseEvent()
	{
		return this.mouseEvent;
	}

	//Called when a key on the keyboard is pressed
	public void keyPressed(KeyEvent e) 
	{
		keyPressed = true;
		keyEvent = e;
	}

	//Called when a key on the keyboard is released
	public void keyReleased(KeyEvent e) 
	{
		keyPressed = false;
		keyEvent = e;
	}
	
	//Used to get the key event, which can be used to get the key that was pressed
	public KeyEvent getKeyEvent()
	{
		return this.keyEvent;
	}
	
	//Not used at this point
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
