package entities;

import level.Level;

public class EntityCar extends EntityVehicle
{
	//Constructor
	public EntityCar(Level level, int x, int y, int scale, int direction)
	{
		//This super method has to be called in every class that extends EntityVehicle.
		//It calls the constructor of EntityVehicle and sets the level, x, y, scale, length and direction accordingly.
		super(level, x, y, scale, 1, direction);
	}
}
