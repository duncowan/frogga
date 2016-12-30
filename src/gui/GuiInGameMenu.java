package gui;

import main.Colour;
import main.GameWindow;
import main.Music;

public class GuiInGameMenu extends GuiScreen
{
	//Setup constant variables
	private int VOLUME_MODIFIER = 10;
	private int BUTTONS_X = (GameWindow.WIDTH / 2) - 200;
	private int BUTTONS_Y = 250;
	private int BUTTONS_WIDTH = 400;
	private int BUTTONS_HEIGHT = 40;
	private int SPACING_BETWEEN_BUTTONS = 50;
	
	//Stores all the options for the menu
	private String[] options = {"Resume", "Options", "Exit To Menu"};

	//Called when the GUI first loads
	protected void init() 
	{
		//Decrease the music volume by 10
		if(game.settings.getValue("volume") > 30)
			Music.setVolume(Integer.valueOf(game.settings.getValue("volume")) - VOLUME_MODIFIER);
		
		//Add a new button for every item in the options array
		for(int i = 0; i < options.length; i++)
		{
			this.buttons.add(new GuiButton(i, options[i], BUTTONS_X, (i * SPACING_BETWEEN_BUTTONS) + BUTTONS_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT));
		}
		//Save any changes to the player's settings file
		game.profiles.saveChanges();
	}

	//Called when a button is clicked
	protected void buttonClicked(GuiButton button) 
	{
		//If the "Resume" button is clicked...
		if(button.id == 0)
		{
			//Display no GUI and set the volume back to its normal value
			game.displayScreen(null);
			Music.setVolume(Integer.valueOf(game.settings.getValue("volume")));
		}
		
		//If the "Options" button is clicked, the display the options GUI
		if(button.id == 1)
		{
			game.displayScreen(new GuiOptions());
		}
		
		//If the "Exit To Menu" button is clicked...
		if(button.id == 2)
		{
			//Display the main menu GUI, exit the current level, set the volume back normal value and play the main menu music
			game.displayScreen(new GuiMainMenu());
			game.loadLevel(null);
			Music.setVolume(Integer.valueOf(game.settings.getValue("volume")));
			Music.play("intro.wav");
		}
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Set the background to be a striped rectangle
		this.drawStripedRect(pixels, 0, 0, GameWindow.WIDTH, GameWindow.WIDTH, Colour.BLACK);
		//Adds the "GAME MENU" text to the screen
		this.drawCenteredStringWithShadow(pixels, "GAME MENU", 3, GameWindow.WIDTH / 2, 30);
		//Draws all the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
