package gui;

import level.Level;
import main.Colour;
import main.GameWindow;

public class GuiSelectProfile extends GuiScreen
{	
	//Setup constant variables
	private final int PROFILES_HEIGHT = 90;
	private final int PROFILE_SPACING = 90;
	private final int OPTION_BUTTIONS_HIGHT = 480;
	private final int MAX_DISPLAYED_PROFILES = 4;
	
	//Setup global variables
	private int numOfProfiles;
	private int firstDisplayedProfileIndex = 0;
	
	private String selectedProfile;
	//Profile selection list components
	private GuiButton[] profiles = new GuiButton[MAX_DISPLAYED_PROFILES];
	private GuiButton btnUp, btnDown;
	//Options buttons
	private GuiButton btnLoad, btnCreate, btnDelete, btnBack;

	//Called when the GUI first loads
	protected void init() 
	{
		//Load all the profiles
		this.game.profiles.loadProfiles();
		//Sets the number of profiles equal to the number of profile loaded
		numOfProfiles = this.game.profiles.getProfileNames().size();
		
		//Adds 4 list items to the screen
		for(int i = 0; i < MAX_DISPLAYED_PROFILES; i++)
		{
			//If there are less than 4 loaded profiles, break out of the loop
			if(i >= numOfProfiles) break;
			profiles[i] = ((GuiButton) new GuiListItem(i, this.game.profiles.getProfileNames().get(i), (GameWindow.WIDTH / 2) - 250, (PROFILE_SPACING * i) + PROFILES_HEIGHT));
			this.buttons.add(profiles[i]);
		}
		
		//If there are more than 4 loaded profiles, then add up and down buttons to the screen
		if(numOfProfiles > MAX_DISPLAYED_PROFILES)
		{
			btnUp = new GuiButton(4, "^", (GameWindow.WIDTH / 2) + (250 + 20), PROFILES_HEIGHT, 40, 40);
			btnUp.setVisible(false);
			btnDown = new GuiButton(5, "v", (GameWindow.WIDTH / 2) + (250 + 20), (PROFILE_SPACING * MAX_DISPLAYED_PROFILES) - (40 + (PROFILE_SPACING - 80)) + PROFILES_HEIGHT, 40, 40);
			this.buttons.add(btnUp);
			this.buttons.add(btnDown);
		}
		
		//Adds the "Load" button to the screen
		btnLoad = new GuiButton(6, "Load", (GameWindow.WIDTH / 2) - 250, OPTION_BUTTIONS_HIGHT, (500 / 3) - 4, 40);
		btnLoad.setEnabled(false);
		//Adds the "Create" button to the screen
		btnCreate = new GuiButton(7, "Create", (GameWindow.WIDTH / 2) - ((500 / 3) / 2) + 2, OPTION_BUTTIONS_HIGHT, (500 / 3) - 4, 40);
		//Adds the "Delete" button to the screen
		btnDelete = new GuiButton(8, "Delete", (GameWindow.WIDTH / 2) + 254 - (500 / 3), OPTION_BUTTIONS_HIGHT, (500 / 3) - 4, 40);
		btnDelete.setEnabled(false);
		//Adds the "Back" button to the screen
		btnBack = new GuiButton(9, "Back", (GameWindow.WIDTH / 2) - (500 / 2), OPTION_BUTTIONS_HIGHT + 48, 500, 40);
		
		this.buttons.add(btnLoad);
		this.buttons.add(btnCreate);
		this.buttons.add(btnDelete);
		this.buttons.add(btnBack);
	}

	//Called when a button is clicked
	protected void buttonClicked(GuiButton button) 
	{
		//If the down button is clicked...
		if(button.id == 5)
		{
			//Move the list items up
			firstDisplayedProfileIndex++;
			//If the list can't go up any more, hide the down button
			if(firstDisplayedProfileIndex >= (numOfProfiles - MAX_DISPLAYED_PROFILES))
			{
				firstDisplayedProfileIndex = numOfProfiles - MAX_DISPLAYED_PROFILES;
				btnDown.setVisible(false);
			}
			btnUp.setVisible(true);
		}
		
		//If the up button is clicked...
		if(button.id == 4)
		{
			//Move the list items down
			firstDisplayedProfileIndex--;
			//If the list can't go down any more, hide the up button
			if(firstDisplayedProfileIndex <= 0)
			{
				firstDisplayedProfileIndex = 0;
				btnUp.setVisible(false);
			}
			btnDown.setVisible(true);
		}
		
		//Change the text of the list item to either the text of the list item below or the one above it
		//This gives the illusion that the list items are moving when its acutely only the text
		for(int i = 0; i < MAX_DISPLAYED_PROFILES; i++)
		{
			if(profiles[i] != null)
				profiles[i].setText(this.game.profiles.getProfileNames().get(firstDisplayedProfileIndex + i));
		}
		
		//If one of the list items was clicked
		if(button.id < MAX_DISPLAYED_PROFILES)
		{
			selectedProfile = button.getText();
		}
		
		//Enable the load and delete buttons when a profile is selected
		btnLoad.setEnabled(selectedProfile != "");
		btnDelete.setEnabled(selectedProfile != "");
		
		//If the "Load" button is clicked...
		if(button.id == 6)
		{
			//Set the selected profile to the profile that was clicked on
			this.game.profiles.setSelectedProfile(selectedProfile);
			int level = this.game.profiles.getProfileSettingValue("level");
			//If the selected profile is on a higher level than 0...
			if(level > 0)
			{
				//Load that level
				this.game.displayScreen(new GuiLevelTransition(level));
			}
			else
			{
				//If selected profile is on level 0, load the tutorial level
				this.game.loadLevel(new Level(0));
			}
		}
		
		//If button "Create" was clicked, display the player setup GUI
		if(button.id == 7)
		{
			this.game.displayScreen(new GuiPlayerSetup());
		}
		
		//If the "Delete" button was clicked, remove the selected profile and refresh the GUI
		if(button.id == 8)
		{
			this.game.profiles.deleteProfile(selectedProfile);
			this.game.displayScreen(new GuiSelectProfile());
		}
		
		//If the "Back" button was clicked, display the main menu GUI
		if(button.id == 9)
		{
			this.game.displayScreen(new GuiMainMenu());
		}
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Draws a very dark grey border around the profiles list
		this.drawRect(pixels, -4, 70, GameWindow.WIDTH + 8, GameWindow.HEIGHT - 210, 4, Colour.changeBrightness(Colour.DARKGREY, 0.3));
		//Adds the "PROFILES" text to the screen
		this.drawCenteredStringWithShadow(pixels, "PROFILES", 3, GameWindow.WIDTH / 2, 30);
		//Draws all the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
