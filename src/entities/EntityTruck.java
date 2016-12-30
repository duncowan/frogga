package entities;

import level.Level;

public class EntityTruck extends EntityVehicle
{
	//Constructor
	public EntityTruck(Level level, int xOffset, int y, int scale, int direction) 
	{
		//Calls the constructor from EntityVehicle.
		//Used to set the level, x, y, scale, length and direction variables in EntityVehicle.
		super(level, xOffset, y, scale, 2, direction);
	}
	
	//Called on every tick
	//Overrides the update method in EntityVehicle
	public void update()
	{
		//If the truck is going from right to left
		if(direction == 0)
		{
			//Moves the truck from right to left at a certain speed.
			//Easy: 1*1 = 1 pixel per tick
			//Normal: 1*2 = 2 pixels per tick
			//Hard: 1*3 = 3 pixels per tick
			//Impossible: 1*4 = 4 pixels per tick
			this.x -= (TRUCK_BASE_SPEED * (level.game.settings.getValue("difficulty") + 1));
		}
		//If the truck is going from left to right
		else if(direction == 1)
		{
			//Moves the truck from left to right at a certain speed.
			this.x += (TRUCK_BASE_SPEED * (level.game.settings.getValue("difficulty") + 1));
		}
		//Calls the update method from EntityVehicle.
		//Without this, the truck wouldn't reset once it got to the edge of the level.
		super.update();
	}
}
