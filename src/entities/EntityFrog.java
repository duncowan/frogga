package entities;

import gui.Resource;

import java.awt.Point;

import level.Level;
import main.Sound;

public class EntityFrog extends Entity
{
	//Sets how long the jump animation lasts for.
	//Default is 13 ticks.
	private final int MOVE_ANIM_DELAY = 13;
	
	//Stores the colour of the frog
	private int colour;
	//Stores the starting position of the frog.
	//Used when respawning the frog.
	private int startX, startY;
	//Stores the hitbox for the frog.
	private Hitbox hitbox;
	
	//Keeps track of how many ticks have passed
	//Is set to the value of MOVE_ANIM_DELAY so the still/default frog sprite is used when the frog is created.
	private int tick = MOVE_ANIM_DELAY;
	
	//Constructor
	public EntityFrog(Level level, int colour, int x, int y, int scale)
	{
		//Calls the first constructor from Entity.
		//Used to set the level, x, y and scale variables in Entity.
		super(level, x, y, scale);
		//Sets global variable startX equal to local variable x
		this.startX = x;
		//Sets global variable startY equal to local variable y
		this.startY = y;
		//Sets global variable colour equal to local variable colour
		this.colour = colour;
		//Sets global variable hitbox equal to a new Hitbox with the same location and dimensions as the frog.
		hitbox = new Hitbox(x, y, (Resource.ICON_SIZE * scale), (Resource.ICON_SIZE * scale));
	}

	//Called on every tick
	//Overrides the update method in Entity
	public void update() 
	{
		//This is used to stop the tick variable from increasing once it has reached its desired value.
		//By doing this, there will be no chance of getting an 'Integer number too large' error.
		if(tick <= MOVE_ANIM_DELAY)
		{
			tick++;
		}
		//Animates the frog.
		moveAnim();
	}
	
	//Called every tick to animate the frog.
	private void moveAnim()
	{
		//Sets state equal to 0 if tick is more than MOVE_ANIM_DELAY and 1 if tick is less than MOVE_ANIM_DELAY
		state = (tick > MOVE_ANIM_DELAY ? 0 : 1);
	}
	
	//Called from the Level class when an action key (up, down, left, right) is pressed.
	public void move(byte dirct)
	{
		//Plays the jump sound effect
		Sound.JUMP.play();
		//Used to select the section of code to run depending on the value of 'dirct'
		switch(dirct)
		{
		//Called when the up action key is pressed
		case 00:
			//Sets tick to 0 to trigger the move animation.
			tick = 0;
			//Moves the frog up by it's in game size + the spacing between the rows of vehicles
			//Default: 8 * 5 = 40 + 10 = 50 pixels
			y -= (Resource.ICON_SIZE * scale) + Level.SPACING_BETWEEN_ROWS;
			//Sets frog sprite direction facing up.
			direction = 0;
			break;
		
		//Called when the left action key is pressed
		case 01:
			//Sets tick to 0 to trigger the move animation.
			tick = 0;
			//Moves the frog left by it's in game size + the spacing between the rows of vehicles
			x -= (Resource.ICON_SIZE * scale) + Level.SPACING_BETWEEN_ROWS;
			//Sets frog sprite direction facing left.
			direction = 1;
			break;
		
		//Called when the down action key is pressed
		case 10:
			//Sets tick to 0 to trigger the move animation.
			tick = 0;
			//Moves the frog down by it's in game size + the spacing between the rows of vehicles
			y += (Resource.ICON_SIZE * scale) + Level.SPACING_BETWEEN_ROWS;
			//Sets frog sprite direction facing down.
			direction = 2;
			break;
		
		//Called when the right action key is pressed
		case 11:
			//Sets tick to 0 to trigger the move animation.
			tick = 0;
			//Moves the frog right by it's in game size + the spacing between the rows of vehicles
			x += (Resource.ICON_SIZE * scale) + Level.SPACING_BETWEEN_ROWS;
			//Sets frog sprite direction facing right.
			direction = 3;
			break;
		}
		//Updates the frog's hitbox location
		hitbox.setX(this.x);
		hitbox.setY(this.y);
	}
	
	//Used to see if the frog has collided with an object.
	public boolean intersectsWith(Point[] points)
	{
		return this.hitbox.intersectsWith(points);
	}
	
	//Gets the hitbox for the frog.
	public Point[] hitbox()
	{
		return this.hitbox.hitbox();
	}
	
	//Is called by something when that something kills the frog.
	//e.g. when a vehicle hits the frog, it will call this method.
	public void kill()
	{
		//Plays the frog death sound
		Sound.DEATH.play();
		//Removes the frog from the level and replaces it with...
		level.removeEntity(this);
		//A new EntityExplosion at the same location as the frog.
		level.addEntity(new EntityExplosion(level, x, y, scale));
		
//		Old code for frog death which just resets it's position to the center bottom of the screen.
//		x = GameWindow.WIDTH / 2;
//		y = GameWindow.HEIGHT - (scale * renderFrog.FROG_SIZE);
//		hitbox.setX(this.x);
//		hitbox.setY(this.y);
	}
	
	//Called when the frog "goes through" an exit.
	public void respawn()
	{
		//Set the frog's location and the hitbox's location to the frog's starting position.
		//Default is bottom center of the level
		this.x = this.startX;
		this.y = this.startY;
		hitbox.setX(startX);
		hitbox.setY(startY);
	}
	
	//Gets the colour of the frog
	public int getColour() 
	{
		return colour;
	}

	//Sets the colour of the frog
	public void setColour(int colour) 
	{
		this.colour = colour;
	}
}
