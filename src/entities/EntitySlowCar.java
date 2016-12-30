package entities;

import level.Level;

public class EntitySlowCar extends EntityCar
{
	//Constructor
	public EntitySlowCar(Level level, int x, int y, int scale, int direction)
	{
		//Calls the constructor from EntityCar.
		//Used to set the level, x, y, scale and direction variables in EntityCar.
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
			//Easy: 2*1 = 2 pixels per tick
			//Normal: 2*2 = 4 pixels per tick
			//Hard: 2*3 = 6 pixels per tick
			//Impossible: 2*4 = 8 pixels per tick
			this.x -= (SLOW_CAR_BASE_SPEED * (level.game.settings.getValue("difficulty") + 1));
		}
		//If the car is going from left to right
		else if(direction == 1)
		{
			//Moves the car from left to right at a certain speed.
			this.x += (SLOW_CAR_BASE_SPEED * (level.game.settings.getValue("difficulty") + 1));
		}
		//Calls the update method from EntityVehicle.
		//Without this, the car wouldn't reset once it got to the edge of the level.
		super.update();
	}
}
