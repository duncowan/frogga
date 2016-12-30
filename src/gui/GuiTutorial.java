package gui;


import java.awt.event.KeyEvent;
import java.util.ArrayList;

import main.GameWindow;

public class GuiTutorial extends GuiScreen
{
	//Setup global variables
	ArrayList<GuiButton> tutBubbles = new ArrayList<GuiButton>();
	private int displayedTip = 0;
	
	//Called when GUI first loads
	public void init()
	{
		//Gets the movement keys
		String upKey = KeyEvent.getKeyText(game.settings.getValue("keyUp")).toUpperCase();
		String downKey = KeyEvent.getKeyText(game.settings.getValue("keyDown")).toUpperCase();
		String leftKey = KeyEvent.getKeyText(game.settings.getValue("keyLeft")).toUpperCase();
		String rightKey = KeyEvent.getKeyText(game.settings.getValue("keyRight")).toUpperCase();
		
		//Adds all the tutorial bubbles to the tutBubbles array
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "  Welcome to FROGA    (click to proceed)", (GameWindow.WIDTH / 2) - 150, 250));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "Click these squares to progress through the tutorial.", (GameWindow.WIDTH / 2) - 150, 150));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "This is you.", (GameWindow.WIDTH / 2) - 220, GameWindow.HEIGHT - 36));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "You can move with the " + upKey + " " + downKey + " " + leftKey + " and " + rightKey + " keys. This can be changed in the options menu.", 400, GameWindow.HEIGHT - 200));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "Your goal is to get here...", (GameWindow.WIDTH / 2) - 150, 60));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "Before time runs out", GameWindow.WIDTH - 390, 12));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "If you get hit by a vehicle...", (GameWindow.WIDTH / 2) - 150, 300));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "You will lose a life", 80, 12));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "If you lose all your lives or run out of time...", (GameWindow.WIDTH / 2) - 150, 300));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "You will get a GAME OVER D:", (GameWindow.WIDTH / 2) - 150, 200));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "Once you go through all the exits...", (GameWindow.WIDTH / 2) - 150, 60));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "You will progress to the next level :D", (GameWindow.WIDTH / 2) - 150, 300));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "Once you complete level 10...", (GameWindow.WIDTH / 2) - 150, 200));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "The game will be concluded and you can go out and do more useful things with your time :D", (GameWindow.WIDTH / 2) - 150, 100));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "But for now...", (GameWindow.WIDTH / 2) - 100, 100));
		tutBubbles.add(new GuiTutBubble(tutBubbles.size(), "Have fun with                 level 1 :D", (GameWindow.WIDTH / 2) - 150, 200));
		//Hide all tutorial bubbles but the first one
		for(int i = 1; i < tutBubbles.size(); i++)
		{
			tutBubbles.get(i).setVisible(false);
		}
		//Adds all the tutorial bubbles to the screen
		buttons.addAll(tutBubbles);
		//Adds the "Skip" button to the screen
		buttons.add(new GuiTutBubble(tutBubbles.size(), "Skip >>", GameWindow.WIDTH - 130, GameWindow.HEIGHT - 36));
	}

	//Called when a button is clicked
	protected void buttonClicked(GuiButton button) 
	{	
		//When a tutorial bubble is clicked, hide it and show the next tutorial bubble
		displayedTip++;
		for(int i = 0; i < tutBubbles.size(); i++)
		{
			tutBubbles.get(i).setVisible(i == displayedTip);
		}
		
		//If the last tutorial bubble is clicked or the "Skip" button is clicked, load the first level
		if(displayedTip >= tutBubbles.size() || button.id == tutBubbles.size())
		{
			game.displayScreen(new GuiLevelTransition(1));
		}
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Draws all the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
