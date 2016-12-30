package render;

import entities.Entity;
import gui.Resource;

public class RenderExplosion extends RenderEntity
{
	//Setup constant variables
	public final int NUM_FRAMES = 5;
	public final int EXPLOSION_SIZE = Resource.ICON_SIZE;
	private final int INDEX_OFFSET = 2;
	
	//Setup global variables
	private int[][] frames;
	
	//Constructor
	public RenderExplosion(int scale)
	{
		//Create new array to store the frames of the explosion
		frames = new int [NUM_FRAMES][(EXPLOSION_SIZE * scale) * (EXPLOSION_SIZE * scale)];
		
		//Adds all the frames of the explosion to the array
		for(int i = 0; i < NUM_FRAMES; i++)
		{
			int[] rawSprite = spriteSheet.getIcon(INDEX_OFFSET + i, 0);
			int[] scaledSprite = scaleImg(rawSprite, EXPLOSION_SIZE, EXPLOSION_SIZE, scale);
			frames[i] = scaledSprite;
		}
	}
	
	//Draws the explosion to the screen
	public void draw(Entity entity, int[] pixels)
	{
		if(entity.getState() < NUM_FRAMES)
		drawImg(pixels, frames[entity.getState()], entity.getX(), entity.getY(), EXPLOSION_SIZE * entity.getScale(), EXPLOSION_SIZE * entity.getScale());
	}
}
