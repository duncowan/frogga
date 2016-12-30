package gui;

import level.Level;
import main.Colour;
import main.GameWindow;

public class GuiPlayerSetup extends GuiScreen
{
	//Setup constant variables
	private final int COLOUR_SELECTOR_HEIGHT = 320;
	private final int TEXTBOX_HEIGHT = 130;
	private final int FROG_SCALE = 6;
	private final int FROG_SIZE = FROG_SCALE * Resource.ICON_SIZE;
	
	//Setup global variables
	private int[] frog = new int[FROG_SIZE * FROG_SCALE];
	private String[] colours = {"Red", "Green", "Blue"};
	private int selectedColour;
	private int[] colourValues = new int[colours.length];
	private int buttonId = 0;
	
	private GuiButton btnDone;
	private GuiTextbox txtName;
	
	//Constructor
	public GuiPlayerSetup()
	{
		//Creates the frog image
		frog = this.scaleImg(spriteSheet.getIcon(0, 0), Resource.ICON_SIZE, Resource.ICON_SIZE, FROG_SCALE);
	}
	
	//Called when the GUI first loads
	protected void init()
	{	
		//Adds the name textbox to the screen (only allows letters to be entered)
		txtName = new GuiTextbox(0, (GameWindow.WIDTH / 2) - 250, TEXTBOX_HEIGHT, 500, 40, "letters");
		this.textboxes.add(txtName);
		
		//Adds the colour controls to the screen
		for(int i = 0; i < colours.length; i++)
		{
			this.buttons.add(new GuiButton(buttonId, "-", (GameWindow.WIDTH / 2) - 150, (50 * i) + COLOUR_SELECTOR_HEIGHT, 40, 40));
			buttonId++;
			this.buttons.add(new GuiButton(buttonId, "+", (GameWindow.WIDTH / 2) + 110, (50 * i) + COLOUR_SELECTOR_HEIGHT, 40, 40));
			buttonId++;
		}
		
		//Adds the "Back" button to the screen
		this.buttons.add(new GuiButton(buttonId, "Back", (GameWindow.WIDTH / 2) - 250, 500, 245, 40));
		//Adds the "Done: button to the screen
		btnDone = new GuiButton(buttonId + 1, "Done", (GameWindow.WIDTH / 2) + 5, 500, 245, 40);
		btnDone.setEnabled(false);
		this.buttons.add(btnDone);
		
	}
	
	//Called when a button is clicked
	protected void buttonClicked(GuiButton button) 
	{
		//Keeps track of what colour the user is currently changing.
		//(0 = red, 1 = Green, 2 = blue)
		int colourToChange = -1;
		//False when the user is increasing a colour value and true when the user is decreasing a colour value
		boolean minusButtonClicked = false;
		//Detects what colour the user is changing and whether or not they are decreasing or increasing it
		for(int i = 0; i < buttonId; i++)
		{
			colourToChange = i / 2;
			if(button.id == i)
			{
				if(i % 2 == 0)
				{
					minusButtonClicked = true;
				}
				else if(i % 2 == 1)
				{
					minusButtonClicked = false;
				}
				break;
			}			
		}
		
		//If one of the colour control buttons were clicked...
		if(button.id < buttonId)
		{
			//If the minus button was clicked, then subtract 15 (if the value is more than 0)
			//from the colour the user is trying to change
			if(minusButtonClicked)
			{
				if(colourValues[colourToChange] > 0)
				colourValues[colourToChange] -= 15;
			}
			//If the plus button was clicked, then add 15 (if the value is less than 255)
			//to the colour the user is trying to change
			else
			{
				if(colourValues[colourToChange] < 255)
				colourValues[colourToChange] += 15;
			}
			//Set the selected colour equal to the hex value of the 3 colours
			selectedColour = Colour.rgbToHex(colourValues[0], colourValues[1], colourValues[2]);
		}
		
		//If the "Back" button was clicked, display the main menu GUI
		if(button.id == buttonId)
		{
			this.game.displayScreen(new GuiMainMenu());
		}
		
		//If the "Done" button was clicked...
		if(button.id == (buttonId + 1))
		{
			//Set the players frog body colour equal to the colour they have chosen
			this.game.profiles.setProfileSetting("colour", selectedColour);
			//Create a new profile with the name provided in the name textbox
			this.game.profiles.createProfile(txtName.getText());
			//Set the selected profile equal to the one that was just created
			this.game.profiles.setSelectedProfile(txtName.getText());
			//Load the tutorial level
			this.game.loadLevel(new Level(0));
		}
	}

	//Called on every tick
	public void update() 
	{
		//Causes the textbox's cursor to flash
		txtName.updateCursorCounter();
		//Enable the "Done" button if is at least 1 letter in the textbox
		btnDone.setEnabled(txtName.getText().length() > 0);
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels)
	{
		//Adds the "PLAYER SETUP" text to the screen
		this.drawCenteredStringWithShadow(pixels, "PLAYER SETUP", 3, GameWindow.WIDTH / 2, 30);
		//Adds the "Name" text to the screen
		this.drawCenteredString(pixels, "Name", 2, GameWindow.WIDTH / 2, TEXTBOX_HEIGHT - 20);
		//Adds the "Frog Body Colour" text to the screen
		this.drawCenteredString(pixels, "Frog Body Colour", 2, GameWindow.WIDTH / 2, 230);
		//Adds the frog to the screen
		this.drawImg(pixels, replaceColour(frog, Colour.LIGHTGREY, selectedColour), 376, 255, FROG_SIZE, FROG_SIZE);
		//Adds the colours (red green blue) and their values to the screen
		for(int i = 0; i < colours.length; i++)
		{
			this.drawCenteredString(pixels, colours[i] + ": " + colourValues[i], 2, GameWindow.WIDTH / 2, (i * 50) + (COLOUR_SELECTOR_HEIGHT + 16));
		}
		//Draws the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
