package render;

import entities.Entity;
import gui.Resource;

public class RenderCar extends RenderEntity
{
	//Setup constant variables
	private final int CAR_SIZE = Resource.ICON_SIZE;
	private final int DIRECTIONS = 2;
	private final int TEXTURES = 2;	
	
	//Setup global variables
	private int[][][] carDirections;
	private int type = 0;
	
	//Constructor
	public RenderCar(int scale, int type)
	{
		this.type = type;
		
		//Create new array to store the car textures and directions
		carDirections = new int [TEXTURES][DIRECTIONS][(CAR_SIZE * scale) * (CAR_SIZE * scale)];
		
		//Left facing slow car texture
		carDirections[0][0] = scaleImg(spriteSheet.getIcon(0, 1), CAR_SIZE, CAR_SIZE, scale);
		//Right facing slow car texture
		carDirections[0][1] = scaleImg(flipHorizontal(spriteSheet.getIcon(0, 1), CAR_SIZE, CAR_SIZE), CAR_SIZE, CAR_SIZE, scale);
		
		
		//Left facing fast car texture
		carDirections[1][0] = scaleImg(spriteSheet.getIcon(1, 1), CAR_SIZE, CAR_SIZE, scale);
		//Right facing fast car texture
		carDirections[1][1] = scaleImg(flipHorizontal(spriteSheet.getIcon(1, 1), CAR_SIZE, CAR_SIZE), CAR_SIZE, CAR_SIZE, scale);
	}
	
	//Called when the car needs to be drawn to the screen
	public void draw(Entity entity, int[] pixels)
	{
		drawImg(pixels, carDirections[type][entity.getDirection()], entity.getX(), entity.getY(), CAR_SIZE * entity.getScale(), CAR_SIZE * entity.getScale());
	}
}
