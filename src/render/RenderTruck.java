package render;

import entities.Entity;
import gui.Resource;

public class RenderTruck extends RenderEntity
{
	//Setup constant variables
	private final int LENGTH = 2;
	private final int DIRECTIONS = 2;
	
	//Setup constant variables
	private int[][][] truckDirections;
	
	//Constructor
	public RenderTruck(int scale)
	{
		//Create new array to store the trucks's directions and different sections 
		//(because its 2 spites long as opposed to the usual 1)
		truckDirections = new int [DIRECTIONS][LENGTH][(Resource.ICON_SIZE * scale) * (Resource.ICON_SIZE * scale)];
		
		//Left facing, first section of the truck
		truckDirections[0][0] = scaleImg(spriteSheet.getIcon(0, 2), Resource.ICON_SIZE, Resource.ICON_SIZE, scale);
		//Left facing, second section of the truck
		truckDirections[0][1] = scaleImg(spriteSheet.getIcon(1, 2), Resource.ICON_SIZE, Resource.ICON_SIZE, scale);
		
		//Right facing, second section of the truck
		truckDirections[1][0] = scaleImg(this.flipHorizontal(spriteSheet.getIcon(1, 2), Resource.ICON_SIZE, Resource.ICON_SIZE), Resource.ICON_SIZE, Resource.ICON_SIZE, scale);
		//Right facing, first section of the truck
		truckDirections[1][1] = scaleImg(this.flipHorizontal(spriteSheet.getIcon(0, 2), Resource.ICON_SIZE, Resource.ICON_SIZE), Resource.ICON_SIZE, Resource.ICON_SIZE, scale);
	}
	
	//Draw both sections of the truck to the screen
	public void draw(Entity entity, int[] pixels)
	{
		for(int i = 0; i < LENGTH; i++)
		{
			drawImg(pixels, truckDirections[entity.getDirection()][i], entity.getX() + (i * (Resource.ICON_SIZE * entity.getScale())), entity.getY(), Resource.ICON_SIZE * entity.getScale(), Resource.ICON_SIZE * entity.getScale());
		}
	}
}
