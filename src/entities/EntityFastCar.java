package entities;

import level.Level;

public class EntityFastCar extends EntityCar
{
	//Constructor
	public EntityFastCar(Level level, int x, int y, int scale, int direction)
	{
		//Calls the constructor from EntityCar.
		//This is used to set the level, x, y, scale and direction variables in EntityCar.
		super(level, x, y, scale, direction);
	}

	//Called on every tick
	//Overrides the update method in EntityVehicle
	public void update() 
	{
		//If the car is going from right to left
		if(direction == 0)
		{
			//Moves the car from right to left at a certain speed.
			//Easy: 4*1 = 4 pixels per tick
			//Normal: 4*2 = 8 pixels per tick
			//Hard: 4*3 = 12 pixels per tick
			//Impossible: 4*4 = 16 pixels per tick
			this.x -= (FAST_CAR_BASE_SPEED * (level.game.settings.getValue("difficulty") + 1));
		}
		//If the car is going from left to right
		else if(direction == 1)
		{
			//Moves the car from left to right at a certain speed.
			this.x += (FAST_CAR_BASE_SPEED * (level.game.settings.getValue("difficulty") + 1));
		}
		//Calls the update method from EntityVehicle.
		//Without this, the car wouldn't reset once it got to the edge of the level.
		super.update();
	}
}
