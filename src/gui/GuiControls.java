package gui;


import java.awt.event.KeyEvent;

import main.Colour;
import main.GameWindow;

public class GuiControls extends GuiScreen 
{
	//Setup constant variables
	private final int KEY_SETTER_BUTTONS_X = 350;
	private final int KEY_SETTER_BUTTONS_Y = 160;
	private final int KEY_SETTER_BUTTONS_HEIGHT = 40;
	private final int KEY_SETTER_BUTTONS_WIDTH = 200;
	private final int SPACING_BETWEEN_BUTTONS = 60;
	
	private final int DONE_BUTTON_X = ((GameWindow.WIDTH / 2) - 200);
	private final int DONE_BUTTON_Y = 500;
	private final int DONE_BUTTON_WIDTH = 400;
	private final int DONE_BUTTON_HEIGHT = 40;
	
	//Setup global variables
	private String[] actions = {"Up", "Down", "Left", "Right"};
	private GuiButton selectedButton = null;
	
	//Called when the GUI first loads
	protected void init()
	{	
		//Create a button for every item in the 'actions' array.
		for(int i = 0; i < actions.length; i++)
		{
			//Gets the current key for the selected action from the settings file
			int currentKey = this.game.settings.getValue("key" + actions[i]);
			this.buttons.add(new GuiButton(i, KeyEvent.getKeyText(currentKey).toUpperCase(), KEY_SETTER_BUTTONS_X, KEY_SETTER_BUTTONS_Y + (i * SPACING_BETWEEN_BUTTONS), KEY_SETTER_BUTTONS_WIDTH, KEY_SETTER_BUTTONS_HEIGHT));
		}
		
		//Create a new button with the text "Done"
		this.buttons.add(new GuiButton(4, "Done", DONE_BUTTON_X, DONE_BUTTON_Y, DONE_BUTTON_WIDTH, DONE_BUTTON_HEIGHT));
	}
	
	String buttonText = "";
	//Called when a button gets clicked
	protected void buttonClicked(GuiButton button)
	{
		//Resets the previously selected button's text and text colour when another button is clicked
		//and sets the selectedButton to null
		if(button != selectedButton && selectedButton != null)
		{
			selectedButton.setText(buttonText);
			selectedButton.setTextColour(Colour.WHITE);
			selectedButton = null;
		}
		
		//Changes the text and text colour of the clicked button (if the button isn't the "Done" button)
		//and sets the selectedButton to the button that was clicked
		if(button.id != 4 && selectedButton != button)
		{
			selectedButton = button;
			buttonText = button.getText();
			button.setText(">" + buttonText + "<");
			button.setTextColour(Colour.YELLOW);
		}
		
		//Displays the options GUI and saves the key bindings when the "Done" button is clicked
		if(button.id == 4)
		{
			this.game.displayScreen(new GuiOptions());
			this.game.settings.saveChanges();
		}
	}
	
	//Called when a key is typed
	protected void keyTyped(KeyEvent keyEvent) 
	{
		//If a button (thats not the "Done" button) was clicked...
		if(selectedButton != null)
		{
			//Change the value of the key used to do the selected action
			//(e.g. if the "Up" button is clicked and the "W" key was pressed, then the key to move 
			//up would be set to "W")
			this.game.settings.setValue("key" + actions[selectedButton.id], keyEvent.getKeyCode());
			//Change the text of the selectedButton to the text of the pressed key
			selectedButton.setText(KeyEvent.getKeyText(keyEvent.getKeyCode()).toUpperCase());
			selectedButton.setTextColour(Colour.WHITE);
			selectedButton = null;
		}
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels)
	{
		//If the player is playing a level, then make the background a striped rectangle
		if(this.game.isInGame())
		{
			this.drawStripedRect(pixels, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, Colour.BLACK);
		}
		//Draw the string "CONTROLS" at the top center of the screen
		this.drawCenteredStringWithShadow(pixels, "CONTROLS", 3, GameWindow.WIDTH / 2, 30);
		//Draw a string next to each button describing what action they set
		//(e.g. next to the button that sets the "Up" key, draw the string "Up")
		for(int i = 0; i < actions.length; i++)
		{
			this.drawString(pixels, actions[i], 2, KEY_SETTER_BUTTONS_X - 100, (KEY_SETTER_BUTTONS_Y + 10) + (i * SPACING_BETWEEN_BUTTONS));
		}
		//Draws all the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
