package gui;

import level.Level;
import main.Colour;
import main.GameWindow;
import main.Music;

public class GuiLevelTransition extends GuiScreen
{
	//Delay before the level trys to load
	private final int DELAY = 100;
	
	//Setup global variables
	private int tick = 0;
	private int levelNumber = 0;
	private boolean loadLevel = false;
	
	//Constructor
	public GuiLevelTransition(int levelNumber)
	{
		this.levelNumber = levelNumber;
	}

	//Called when the GUI first loads
	protected void init() 
	{
		//Play the new level music and pre-load the level
		Music.play("nextlevel.wav");
		this.game.loadLevel(new Level(levelNumber));
	}

	public void update() 
	{
		tick++;
		
		//Gives the level a chance to load
		//If updates per second is 60 two times in a row, then display level
		if(tick > DELAY && tick % 60 == 0)
		{
			if(game.getUpdates() == 60)
			{
				if(loadLevel)
				{
					//Display no GUI and play a random level song
					game.displayScreen(null);
					Music.playRandom();
				}
				loadLevel = true;
			}
			else
			{
				loadLevel = false;
			}
		}
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Sets the background to a solid black rectangle
		this.drawFilledRect(pixels, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, Colour.BLACK);
		//Adds the "Level: " and level number text to the screen
		this.drawCenteredStringWithShadow(pixels, "Level " + levelNumber, 5, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) - 20);
		//Adds the "Loading..." text to the screen after 100 ticks
		if(tick > DELAY)
			this.drawCenteredStringWithShadow(pixels, "Loading...", 2, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) + 30);
	}
}
