package entities;

import level.Level;
import main.GameWindow;

public class EntityExplosion extends Entity
{
	//Sets the respawn delay for the frog after it dies.
	//Default is 8 ticks
	private final int RESPAWN_DELAY = 8;
	//Used to keep track of how many ticks have passed
	private int tick = 0;
	
	//Constructor
	public EntityExplosion(Level level, int x, int y, int scale) 
	{
		//Calls the first constructor in Entity
		//This is used to set the level, x, y and scale parameters in Entity
		super(level, x, y, scale);
	}

	//Called on every tick
	//Overrides the update method in Entity
	public void update() 
	{
		//Adds one to 'tick' every tick
		tick++;
		//Is true every 10 ticks
		if(tick % 10 == 0)
		{
			//Add one to the 'state' variable which is in Entity
			state++;
			//Is true when 'state' >= 8
			if(state >= RESPAWN_DELAY)
			{
				//Removes one life
				level.decreaseLives();
				//Adds a new frog to the game at the starting position with the same properties as the old frog
				level.addEntity(new EntityFrog(level, level.game.profiles.getProfileSettingValue(level.game.profiles.getSelectedProfile(), "colour"), GameWindow.WIDTH / 2, GameWindow.HEIGHT - (scale * 8), scale));
				//Removes the explosion entity from the game
				level.removeEntity(this);
			}
		}
	}
}
