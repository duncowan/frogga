package gui;

import main.Colour;
import main.GameWindow;

public class GuiMainMenu extends GuiScreen 
{
	//Setup constant variables
	private int BUTTONS_X = (GameWindow.WIDTH / 2) - 200;
	private int BUTTONS_Y = 250;
	private int BUTTONS_WIDTH = 400;
	private int BUTTONS_HEIGHT = 40;
	private int SPACING_BETWEEN_BUTTONS = 50;
	
	//Stores all the options for the menu
	private String[] options = {"Play", "Options", "Quit"};
	
	protected void init()
	{
		//If the player just exited a level, then reset the game tick.
		//This is used to try and sync the pulsing of the "FROGGA" text to the main menu music
		if(game.isInGame())
			game.tick = -7;
		
		//Loads all the player profiles
		this.game.profiles.loadProfiles();
		
		//For every item in the options array, add a new button
		for(int i = 0; i < options.length; i++)
		{
			this.buttons.add(new GuiButton(i, options[i], BUTTONS_X, (i * SPACING_BETWEEN_BUTTONS) + BUTTONS_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT));
		}
	}
	
	//Called when a button is clicked
	protected void buttonClicked(GuiButton button)
	{
		//If the "Play" button is clicked...
		if(button.id == 0)
		{
			//display the player setup GUI if there are no profiles or...
			if(this.game.profiles.getProfileNames().size() < 1)
			{
				this.game.displayScreen(new GuiPlayerSetup());
			}
			//display the profile select GUI is there is at least 1 profile
			else
			{
				this.game.displayScreen(new GuiSelectProfile());
			}
		}
		
		//If the "Options" button is clicked, display the options GUI
		if(button.id == 1)
		{
			this.game.displayScreen(new GuiOptions());
		}
		
		//If the "Quit" button is clicked, call the exit method from the GameWindow class
		if(button.id == 2)
		{
			game.exit();
		}
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels)
	{
		//Adds the game build version text to the screen
		this.drawString(pixels, "BETA 1.0", 1, 10, 10);
		//Adds the "FROGGA" text to the screen and...           v Sets its scale to 9 for 31 ticks then sets it to 10 for 31 ticks
		this.drawCenteredStringWithShadow(pixels, "FROGA", (game.tick / 31 % 2 == 0 ? 9 : 10), GameWindow.WIDTH / 2, 150, Colour.GREEN);
		//Draws the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
