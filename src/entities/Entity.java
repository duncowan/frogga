package entities;

import level.Level;

public abstract class Entity 
{
	//Setup global parameters for all entities
	protected Level level;
	protected int x;
	protected int y;
	protected int scale;
	protected int direction;
	protected int state;
	
	//First Constructor
	//Used in EntityFrog and EntityExplosion
	public Entity(Level level, int x, int y, int scale)
	{
		this.level = level;
		this.x = x;
		this.y = y;
		this.scale = scale;
	}
	
	//Second Constructor
	//Used in EntityVehicle
	public Entity(Level level)
	{
		this.level = level;
	}
	
	//Every class that extends this class must override this method
	//Used to update the entity logic and is called on every tick
	public abstract void update();
	
	//Used to be used to render the entities
	//In order to try and optimize the game, the entity rendering has been moved to RenderManager
	//public abstract void draw(int[] pixels);
	
	//Used to get the entity's x position on the screen
	public int getX() 
	{
		return x;
	}

	//Used to set the entity's x position on the screen
	public void setX(int x) 
	{
		this.x = x;
	}

	//Used to get the entity's y position on the screen
	public int getY() 
	{
		return y;
	}
	
	//Used to set the entity's y position on the screen
	public void setY(int y) 
	{
		this.y = y;
	}
	
	//Used to get the entity's scale
	public int getScale() 
	{
		return scale;
	}

	//Used to set the entity's scale
	public void setScale(int scale) 
	{
		this.scale = scale;
	}

	//Used to get which direction the entity is facing.
	//For vehicles: 0 = right, 1 = left
	//For frog: 0 = up, 1 = left, 2 = down, 3 = right
	public int getDirection() 
	{
		return direction;
	}

	//Used to set which direction the entity is facing.
	public void setDirection(int direction) 
	{
		this.direction = direction;
	}

	//Used to get which state the entity is in.
	//This is used to animate the entity.
	//e.g. When the frog's state is 1, the renderer uses the jumping frog sprite, and when the frog's state 
	//is 0, the renderer uses the still/default frog sprite.
	public int getState() 
	{
		return state;
	}

	//Used to set the entities state.
	public void setState(int state) 
	{
		this.state = state;
	}
}
