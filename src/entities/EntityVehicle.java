package entities;

import gui.Resource;

import java.awt.Point;
import java.util.ArrayList;

import level.Level;
import main.GameWindow;

public class EntityVehicle extends Entity
{	//Sets up constant variables
	private final int VEHICLE_SIZE = Resource.ICON_SIZE;
	
	protected final int TRUCK_BASE_SPEED = 1;
	protected final int SLOW_CAR_BASE_SPEED = 2;
	protected final int FAST_CAR_BASE_SPEED = 4;
	
	private final int LEFT_START_POS;
	private final int RIGHT_START_POS;
	
	//Sets up global variables
	protected int length;
	private boolean hasPassedLevelBounds = false;
	private Hitbox hitbox;

	//Constructor
	public EntityVehicle(Level level, int xOffset, int y, int scale, int length, int direction)
	{
		super(level);
		//Sets the default values for the left and right starting positions of the vehicle rows
		this.LEFT_START_POS = -((VEHICLE_SIZE * scale) * length);
		this.RIGHT_START_POS = GameWindow.WIDTH;
		//Sets the start position of the vehicle based on what direction its going.
		if(direction == 0)
		{
			this.x = RIGHT_START_POS + xOffset;
		}
		else if(direction == 1)
		{
			this.x = LEFT_START_POS - xOffset;
		}
		//Sets the y, scale, length and direction variables in Entity
		this.y = y;
		this.scale = scale;
		this.length = length;
		this.direction = direction;
		
		//Creates a new Hitbox with the same dimensions and location as the vehicle
		this.hitbox = new Hitbox(x, y, ((VEHICLE_SIZE * scale) * length), (VEHICLE_SIZE * scale));
	}
	
	//Called on every tick
	public void update() 
	{
		//This code is used to find the EntityFrog entity in the Level's 'entities' array
		//Then checks to see if the vehicle has collided with the EntityFrog.
		ArrayList<Entity> entityList = this.level.getEntities();
		for(Entity e : entityList)
		{
			if(e instanceof EntityFrog)
			{
				EntityFrog frog = (EntityFrog) e;
				
				if(this.intersectsWith(frog.hitbox()))
				{
					//If the vehicle does collide with the frog, remove the vehicle from the level
					//and call the 'kill' method from EntityFrog
					level.removeEntity(this);
					frog.kill();
				}
			}
		}
		
		//Used to check if the vehicle has collided with the level bounds
		if(this.intersectsWith(level.getLevelBounds()) && x > GameWindow.WIDTH || this.intersectsWith(level.getLevelBounds()) && (x + ((VEHICLE_SIZE * scale) * length)) < 0)
		{
			if(hasPassedLevelBounds)
			{
				//If it does collide with the bounds, reset the vehicle's position
				reset();
			}
			hasPassedLevelBounds = false;
		}
		else
		{
			hasPassedLevelBounds = true;
		}
		
		//Update the vehicle's hitbox location
		hitbox.setY(y);
		hitbox.setX(x);
	}
    //Used to reset the vehicle to its starting position depending on what direction it's going
	public void reset()
	{
		if(direction == 0)
		{
			this.x = RIGHT_START_POS;
		}
		else if(direction == 1)
		{
			this.x = LEFT_START_POS;
		}
	}
	
	//Used to see if the vehicle collides with something else
	public boolean intersectsWith(Point[] points)
	{
		return this.hitbox.intersectsWith(points);
	}
	
	//Used to get the vehicle's hitbox
	public Point[] hitbox()
	{
		return this.hitbox.hitbox();
	}
	//Used to get the length of the vehicle
	public int getLength() {
		return length;
	}

	//Used to set the length of the vehicle
	public void setLength(int length) {
		this.length = length;
	}
}
