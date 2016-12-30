package entities.tile;

import java.awt.Point;
import java.util.ArrayList;

import level.Level;
import main.Sound;
import entities.Entity;
import entities.EntityFrog;
import entities.Hitbox;
import gui.Resource;

public class TileEntityExit extends Entity
{
	//Sets up global variables
	private Hitbox hitbox;
	private int width, height;
	private int arrowPos;
	private int tick;
	
	//Constructor
	public TileEntityExit(Level level, int x, int y, int scale) 
	{
		super(level, x, y, scale);
		//Sets the height and width of the exit tile (default: 50 x 50)
		this.width = (scale * Resource.ICON_SIZE) + Level.SPACING_BETWEEN_ROWS;
		this.height = (scale * Resource.ICON_SIZE) + Level.SPACING_BETWEEN_ROWS;
		//Creates a new hitbox with the same location and dimensions as the TileEntityExit
		this.hitbox = new Hitbox(x, y, width, height);
	}
	
	//Called on every tick
	public void update() 
	{
		//Calls the 'anim' method
		anim();
		//This code is used to find the EntityFrog entity in the Level's 'entities' array
		//Then checks to see if it collides with this (TileEntityExit).
		ArrayList<Entity> entityList = this.level.getEntities();
		for(Entity e : entityList)
		{
			if(e instanceof EntityFrog)
			{
				EntityFrog frog = (EntityFrog) e;
				
				if(this.intersectsWith(frog.hitbox()))
				{
					//When the frog collides with this, remove this from the level, play the "exit" sound
					//and respawn the frog at the bottom center of the screen.
					this.level.numOfExits--;
					level.game.profiles.setProfileSetting("exits", this.level.numOfExits);
					//If the frog has gone through all the exits, load the next level
					if(this.level.numOfExits <= 0)
					{
						this.level.loadNextLevel();
					}
					else
					{
						Sound.EXIT.play();
						this.level.removeEntity(this);
						frog.respawn();
					}
				}
			}
		}
	}
	
	//Used to animate the arrow on the exit tiles
	private void anim()
	{
		//Every 50 ticks...
		if(tick % 50 == 0)
		{
			//Move the arrow up 16 pixels
			arrowPos -= 16;
			//If the arrow is at the top of the exit
			if(arrowPos < 0)
			{
				//Reset the arrow's position to the bottom of the exit
				arrowPos = 32;
			}
		}
		tick++;
	}
	
	//Used to check if the exit collides with something
	public boolean intersectsWith(Point[] points)
	{
		return this.hitbox.intersectsWith(points);
	}
	
	//Used to get the exit's hitbox
	public Point[] hitbox()
	{
		return this.hitbox.hitbox();
	}
///////////////////////////////////////////////Getters and Setters///////////////////////////////////////////
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getArrowPos()
	{
		return arrowPos;
	}
}
