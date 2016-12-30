package gui;

import main.Colour;
import main.GameWindow;
import main.Music;

public class GuiGameOver extends GuiScreen
{
	//Setup constant variables
	private final int TEXT_SCALE = 6;
	
	private final int BUTTON_WIDTH = 200;
	private final int BUTTON_HEIGHT = 40;
	private final int SPACE_BETWEEN_TEXT_AND_BUTTONS = 50;
	private final int SPACE_BETWEEN_BUTTONS = 10;
	
	//Called when the GUI first loads
	protected void init() 
	{
		//Exits the level
		game.loadLevel(null);
		//Plays the game over music
		Music.play("gameover.wav");
		//Adds the "Continue" and "Exit" buttons to the GUI
		buttons.add(new GuiButton(0, "Continue", (GameWindow.WIDTH / 2) - (BUTTON_WIDTH + SPACE_BETWEEN_BUTTONS), (GameWindow.HEIGHT / 2) + SPACE_BETWEEN_TEXT_AND_BUTTONS, BUTTON_WIDTH, BUTTON_HEIGHT));
		buttons.add(new GuiButton(1, "Exit", (GameWindow.WIDTH / 2) + SPACE_BETWEEN_BUTTONS, (GameWindow.HEIGHT / 2) + SPACE_BETWEEN_TEXT_AND_BUTTONS, BUTTON_WIDTH, BUTTON_HEIGHT));
	}
	
	//Called when a button is clicked
	protected void buttonClicked(GuiButton button) 
	{
		//If the "Continue" button is clicked...
		if(button.id == 0)
		{
			//Reset the players settings and load the first level
			game.profiles.setDefaultSettings();
			game.displayScreen(new GuiLevelTransition(1));
		}
		
		//If the "Exit" button is clicked...
		if(button.id == 1)
		{
			//Reset the players settings, save the changes, play the main menu music and display the main menu
			game.profiles.setDefaultSettings();
			game.profiles.saveChanges();
			Music.play("intro.wav");
			game.displayScreen(new GuiMainMenu());
		}
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Adds the "GAME OVER" text to the screen
		this.drawCenteredString(pixels, "GAME OVER", TEXT_SCALE, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) - (SPACE_BETWEEN_TEXT_AND_BUTTONS + (TEXT_SCALE * Resource.ICON_SIZE)), Colour.RED);
		//Draws all the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
