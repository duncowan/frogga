package gui;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import render.Renderer;
import main.GameWindow;
import main.InputHandler;
import main.Sound;

public class GuiScreen extends Renderer
{
	//Setup global varables
	protected ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	protected ArrayList<GuiTextbox> textboxes = new ArrayList<GuiTextbox>();
	protected GameWindow game;
	protected InputHandler input;
	
	private boolean pausesGame = true;
	
	//Constructor (not used)
	public GuiScreen()
	{
	}
	
	//This gets called when a GUI first loads.
	//Classes that extend this class will override this
	protected void init(){}
	
	//This gets called on every tick
	//Classes that extend this class will override this
	public void update(){}
	
	//Sets whether of not a GUI should pause the game
	//This is used by subclasses (default is ture)
	protected void shouldPauseGame(boolean pausesGame)
	{
		this.pausesGame = pausesGame;
	}
	
	//Gets whether of not a GUI should pause the game
	public boolean pausesGame()
	{
		return this.pausesGame;
	}
	
	//Called whenever the GUI changes
	public void initialize(GameWindow game)
	{
		this.game = game;
		this.input = game.input;
		this.buttons.clear();
		this.textboxes.clear();
		this.init();
	}
	
	//Used to add the graphics to the screen
	//This gets overridden by subclasses
	public void draw(int[] pixels)
	{
		for(GuiButton b : buttons)
		{
			b.draw(pixels);
		}
		
		for(GuiTextbox t : textboxes)
		{
			t.draw(pixels);
		}
	}
	
	//Called on every tick
	public void mouseMoved(int x, int y)
	{
		//If a button is enabled and the mouse cursor enters it... 
		for(GuiButton b : buttons)
		{
			if(b.isEnabled())
			{
				if(b.contains(x, y))
				{
					//call the mouseEntered method from the button
					b.mouseEntered();
				}
				else
				{
					//If the mouse leaves the button, call the mouseExited method from the button
					b.mouseExited();
				}
			}
		}
	}
	
	//Called when a mouse button has been pressed and then released
	private void mouseClicked(MouseEvent mouseEvent) 
	{
		//If a button gets clicked on, call the buttonClicked method play the click sound and set the button's focus to true
		for(GuiButton b : buttons)
		{
			if(b.isEnabled())
			{
				if(b.contains(mouseEvent.getX(), mouseEvent.getY()))
				{
					this.buttonClicked(b);
					Sound.CLICK.play();
					b.setFocused(true);
				}
				else
				{
					b.setFocused(false);
				}
			}
		}
		//If a textbox gets clicked on, set its focus to ture
		for(GuiTextbox t : textboxes)
		{
			if(t.contains(mouseEvent.getX(), mouseEvent.getY()))
			{
				t.setFocused(true);
			}
			else
			{
				t.setFocused(false);
			}
		}
	}
	
	//Called when a key has been pressed an then released
	protected void keyTyped(KeyEvent keyEvent) 
	{
		//Call the textboxKeyTyped method from the textbox
		for(GuiTextbox t : textboxes)
		{
			t.textboxKeyTyped(keyEvent);
		}
	}
	
	//Called when a button is clicked
	//Gets overridden by subclasses
	protected void buttonClicked(GuiButton button) {}
	
	boolean mouseDown = false;
	boolean keyDown = false;
	//Called on every tick
	public void handleInput() 
	{
		try 
		{
			mouseMoved(input.getMousePos().x, input.getMousePos().y);
			
			if(input.mouseClicked)
			{
				if(!mouseDown)
				{
					mouseDown = true;
				}
			}
			else
			{
				if(mouseDown)
				{
					mouseClicked(input.getMouseEvent());
					mouseDown = false;
				}
			}
			
			if(input.keyPressed)
			{
				keyDown = true;
			}
			else
			{
				if(keyDown)
				{
					keyTyped(input.getKeyEvent());
					keyDown = false;
				}
			}
		}
		catch(Exception e){}
	}
}
