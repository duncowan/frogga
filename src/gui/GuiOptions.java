package gui;

import main.Colour;
import main.GameWindow;
import main.Sound;
import main.Music;

public class GuiOptions extends GuiScreen 
{
	//Setup constant variables
	private final int NORMAL_BUTTONS_WIDTH = 400;
	private final int NORMAL_BUTTONS_HEIGHT = 40;
	private final int VOLUME_BUTTONS_WIDTH = 40;
	private final int VOLUME_BUTTONS_HEIGHT = 40;
	private final int SPACING_BETWEEN_VOLUME_BUTTONS = 150;
	
	private final int DIFFICULTY_BUTTON_Y = 175;
	private final int VOLUME_BUTTONS_Y = 250;
	private final int CONTROLS_BUTTON_Y = 325;
	private final int DONE_BUTTON_Y = 500;
	
	//Setup global variables
	private GuiButton btnDifficulty;
	
	private int volume = 0;
	private int selectedDifficulty = 0;
	private String[] difficulties = {"EASY", "NORMAL", "HARD", "IMPOSSIBLE"};
	private int tick = 0;
	
	//Called when the GUI first loads
	protected void init()
	{
		//If the difficulty in game settings is more than the maximum possible difficulty, then set
		//selectedDifficulty equal to the maximum difficulty.
		//If the difficulty in game settings is less than the maximum possible difficulty, then set
		//selectedDifficulty equal to the game settings difficulty.
		selectedDifficulty = (this.game.settings.getValue("difficulty") > difficulties.length ? difficulties.length : this.game.settings.getValue("difficulty"));
		//Set the volume equal to the game settings volume
		volume = this.game.settings.getValue("volume");
		
		//Add the difficulty selector button
		btnDifficulty = new GuiButton(0, "Difficulty: " + difficulties[selectedDifficulty], (GameWindow.WIDTH / 2) - 200, DIFFICULTY_BUTTON_Y, NORMAL_BUTTONS_WIDTH, NORMAL_BUTTONS_HEIGHT);
		this.buttons.add(btnDifficulty);
		
		//Add the volume control buttons
		this.buttons.add(new GuiButton(1, "+", (GameWindow.WIDTH / 2) + (SPACING_BETWEEN_VOLUME_BUTTONS - VOLUME_BUTTONS_WIDTH), VOLUME_BUTTONS_Y, VOLUME_BUTTONS_WIDTH, VOLUME_BUTTONS_HEIGHT));
		this.buttons.add(new GuiButton(2, "-", (GameWindow.WIDTH / 2) - SPACING_BETWEEN_VOLUME_BUTTONS, VOLUME_BUTTONS_Y, VOLUME_BUTTONS_WIDTH, VOLUME_BUTTONS_HEIGHT));
		
		//Add the "Controls" button
		this.buttons.add(new GuiButton(3, "Controls", (GameWindow.WIDTH / 2) - (NORMAL_BUTTONS_WIDTH / 2), CONTROLS_BUTTON_Y, NORMAL_BUTTONS_WIDTH, NORMAL_BUTTONS_HEIGHT));
		
		//Add the "Done" button
		this.buttons.add(new GuiButton(4, "Done", (GameWindow.WIDTH / 2) - (NORMAL_BUTTONS_WIDTH / 2), DONE_BUTTON_Y, NORMAL_BUTTONS_WIDTH, NORMAL_BUTTONS_HEIGHT));
	}
	
	//Called when a button is clicked
	protected void buttonClicked(GuiButton button)
	{
		//If the difficulty selector button was clicked...
		if(button.id == 0)
		{
			//Increase the difficulty by 1
			selectedDifficulty++;
			//If the selected difficulty is more than the maximum difficulty, then set difficulty to 0
			if(selectedDifficulty == difficulties.length)
			{
				selectedDifficulty = 0;
			}
			//Change the difficulty selector button's text to the selected difficulty
			button.setText("Difficulty: " + difficulties[selectedDifficulty]);
			//Set the new difficulty in game settings
			this.game.settings.setValue("difficulty", selectedDifficulty);
		}
		
		//If the volume increase button was clicked...
		if(button.id == 1)
		{
			//if the current volume is less than 100, then add 5 to it
			if(volume < 100)
			{
				volume += 5;
			}
			//Set the new volume in game settings
			this.game.settings.setValue("volume", volume);
			//Change the volume for the sounds and music
			Sound.setVolume(volume);
			Music.setVolume(volume);
		}
		
		//If the volume decrease button was clicked...
		if(button.id == 2)
		{
			//If the current volume is more than 15, the subtract 5 from it
			if(volume > 20)
			{
				volume -= 5;
			}
			//Set the new volume in game settings
			this.game.settings.setValue("volume", volume);
			//Change the volume for the sounds and music
			Sound.setVolume(volume);
			Music.setVolume(volume);
		}
		
		//If the "Controls" button was clicked...
		if(button.id == 3)
		{
			//Display the controls GUI and save all changes to the game settings file
			this.game.displayScreen(new GuiControls());
			this.game.settings.saveChanges();
		}
		
		//If the "Done" button was clicked...
		if(button.id == 4)
		{
			//Display the main menu GUI if the player is not in a level
			if(!this.game.isInGame())
			{
				this.game.displayScreen(new GuiMainMenu());
			}
			//Display the in game menu GUI if the player is in a level
			else
			{
				this.game.displayScreen(new GuiInGameMenu());
			}
			//Save all changes to the game settings file
			this.game.settings.saveChanges();
		}
	}
	
	//Called on every tick
	public void update()
	{
		//If the selected difficulty is set to "Impossible"...
		if(selectedDifficulty == 3)
		{
			tick++;
			//Change the difficulty selector button's text colour to cyan for 10 ticks then to red for 10 ticks
			if(tick / 10 % 2 == 0)
			{
				btnDifficulty.setTextColour(Colour.CYAN);
			}
			else
			{
				btnDifficulty.setTextColour(Colour.RED);
			}
		}
		//For every other difficulty...
		else
		{
			//Set the difficulty selector button's text colour to white
			btnDifficulty.setTextColour(Colour.WHITE);
		}
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels)
	{
		//If the player is in a level, then set the background to a striped rectangle
		if(this.game.isInGame())
		{
			this.drawStripedRect(pixels, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, Colour.BLACK);
		}
		//Adds the "OPTIONS" text to the screen
		this.drawCenteredStringWithShadow(pixels, "OPTIONS", 3, GameWindow.WIDTH / 2, 30);
		//If volume is less than 25, add the "Volume: MUTE" text to the screen
		if(volume < 25)
		{
			this.drawCenteredString(pixels, "Volume: MUTE", 2, GameWindow.WIDTH / 2, VOLUME_BUTTONS_Y + 20);
		}
		//If the volume is more than 20, add the text "Volume: " and the volume value to the screen 
		else
		{
			this.drawCenteredString(pixels, "Volume: " + volume + "%", 2, GameWindow.WIDTH / 2, VOLUME_BUTTONS_Y + 20);
		}
		//Draws all the buttons and textboses to the screen
		super.draw(pixels);
	}
}
