package gui;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.Colour;
import main.GameWindow;
import main.Music;

public class GuiCredits extends GuiScreen
{
	//This how long the credits take to scroll to the end
	private final int DELAY = 4300;
	
	//Loads the credits text file
	private InputStream creditsFile = Resource.class.getResourceAsStream("/resources/credits.txt");
	//Stores each line of text from the credits file
	private ArrayList<String> creditLines = new ArrayList<String>();
	//Stores the text size for each line of text from the credits file
	private ArrayList<Integer> lineScale = new ArrayList<Integer>();
	private int tick;
	
	public GuiCredits()
	{
        try 
        {
        	//Create a new buffered reader and put the contents of the credits text file in it
        	BufferedReader buffCreditsFile = new BufferedReader(new InputStreamReader(creditsFile));
    		
        	String line;
        	//Loops through all the lines of text in the credits file
			while ((line = buffCreditsFile.readLine()) != null) 
			{
				//If the current line is not a blank line (# in text file = blank line)...
				if(!line.contains("#"))
				{
					//Get the first character of the current line, convert it to an integer then store it in 
					//'lineScale' array. On every line the first character is a number. This number is used
					//to set the scale for that line.
					lineScale.add(Integer.valueOf(line.substring(0, 1)));
					//Get the characters after the first character and store them in the 'creditLines' array
					creditLines.add(line.substring(1));
				}
				//If the current line is blank (i.e. has a # in it)...
				else
				{
					//Set the scale of that line to 8 and its text to nothing
					lineScale.add(8);
					creditLines.add("");
				}
			}
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		}
	}

	//Called when the GUI first loads
	protected void init()
	{
		//Play the credits music
		Music.play("credits.wav");
	}
	
	//Called when a key is typed
	protected void keyTyped(KeyEvent keyEvent) 
	{
		//If the escape key is pressed, then play the main menu music and display the main menu GUI
		if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			Music.play("intro.wav");
			game.displayScreen(new GuiMainMenu());
		}
	}

	//Called on every tick
	public void update() 
	{
		//Used to scroll the text up
		tick++;
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//After the credits have finished scrolling, show the string "Captain Camper Games"
		if(tick > DELAY)
		{
			this.drawCenteredString(pixels, "Captain Camper", 5,(GameWindow.WIDTH / 2), (GameWindow.HEIGHT / 2), Colour.GREEN);
			this.drawCenteredString(pixels, "Games", 3,(GameWindow.WIDTH / 2), (GameWindow.HEIGHT / 2) + 40, Colour.GREEN);
		}
		else
		{
			//Adds all the lines of text from the credits file to the screen and slowly scrolls them up
			for(int i = 0; i < creditLines.size(); i++)
			{
				//If the current line of text is off screen, then don't draw it to the screen
				//This helps with performance 
				if((GameWindow.HEIGHT + (i * 40) - tick) < GameWindow.HEIGHT) 
				this.drawCenteredStringWithShadow(pixels, creditLines.get(i), lineScale.get(i), (GameWindow.WIDTH / 2), GameWindow.HEIGHT + (i * 40) - tick);
			}
		}
	}
}
