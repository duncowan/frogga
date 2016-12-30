package gui;

import java.util.ArrayList;

import render.Renderer;

import level.Level;
import main.Colour;
import main.GameWindow;

import entities.Entity;
import entities.EntityFrog;

public class GuiHud extends Renderer
{
	//Setup constant varables
	private final int TEXT_SCALE = 1;
	private final int FROG_SCALE = 3;
	private final int TIME_SCALE = 2;
	private final int MARGIN_X = 10;
	private final int MARGIN_Y = 8;
	
	//Setup global variables
	private ArrayList<int[]> frogs = new ArrayList<int[]>();
	private Level level;
	private int playerColour;
	private int tick; 
	private int time;
	
	//Constructor
	public GuiHud(Level level)
	{	
		this.level = level;
		//Sets the time equal to the value of time in the player's setting file.
		this.time = level.game.profiles.getProfileSettingValue("time");
		//Sets playerColour equal to the frog colour in game
		for(Entity e : level.getEntities())
		{
			if(e instanceof EntityFrog)
			{
				playerColour = ((EntityFrog) e).getColour();
			}
		}
		updateLives();
	}

	//Used to add/remove the number of frog images in the top left corner of the screen
	public void updateLives()
	{
		//Remove everything from the frogs array
		frogs.clear();
		//Add a frog image to the frogs array for every life the player has
		//(e.g. if the player has 1 life, then one frog image will be displayed in the top 
		//left corner of the screen)
		for(int i = 0; i < level.getLives(); i++)
		{
			//Sets the frog's body colour to the player colour
			int[] colouredFrog = replaceColour(spriteSheet.getIcon(0, 0), Colour.LIGHTGREY, playerColour);
			//Scales the frog
			int[] scaledColouredFrog = scaleImg(colouredFrog, Resource.ICON_SIZE, Resource.ICON_SIZE, FROG_SCALE);
			frogs.add(scaledColouredFrog);
		}
	}
	
	//Called on every tick
	public void update()
	{
		//Used to update the timer
		tick++;
		//Every 60 ticks (1 second), decrease the time by one and set the time in the player's settings
		//file to the current time
		if(tick % 60 == 0)
		{
			time--;
			level.setTime(time);
			level.game.profiles.setProfileSetting("time", time);
			//If the time reaches 0, then display the game over GUI
			if(time <= 0)
			{
				level.game.displayScreen(new GuiGameOver());
			}
		}
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Adds the "LIVES" text to the screen
		this.drawString(pixels, "LIVES", TEXT_SCALE, MARGIN_X, MARGIN_Y);
		//Adds the "TIME" text to the screen
		this.drawString(pixels, "TIME", TEXT_SCALE, GameWindow.WIDTH - (MARGIN_X + ((TEXT_SCALE * Resource.ICON_SIZE) * 4)), MARGIN_Y);
		//Adds the current time to the screen
		this.drawString(pixels, String.valueOf(time), TIME_SCALE, GameWindow.WIDTH - ((String.valueOf(time).length() * (TIME_SCALE * Resource.ICON_SIZE)) + MARGIN_X), MARGIN_Y + 14);
		//Draws the frog images to the screen
		for(int i = 0; i < frogs.size(); i++)
		{
			this.drawImg(pixels, frogs.get(i), (i * 30) + MARGIN_X, MARGIN_Y + 14, (Resource.ICON_SIZE * FROG_SCALE), (Resource.ICON_SIZE * FROG_SCALE));
		}
	}
}
