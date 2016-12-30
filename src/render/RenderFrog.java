package render;

import main.Colour;
import entities.Entity;
import entities.EntityFrog;
import gui.Resource;

public class RenderFrog extends RenderEntity
{
	//Setup constant variables
	public final int FROG_SIZE = Resource.ICON_SIZE;
	private final int STATES = 2;
	private final int DIRECTIONS = 4;
	
	//Setup global variables
	private int[][][] statesAndDirections;
	
	//Constructor
	public RenderFrog(int scale)
	{
		//Create new array to store the frog's states and directions
		statesAndDirections = new int[STATES][DIRECTIONS][(FROG_SIZE * scale) * (FROG_SIZE * scale)];
		
		//Up facing frog in default state
		statesAndDirections[0][0] = scaleImg(spriteSheet.getIcon(0, 0), FROG_SIZE, FROG_SIZE, scale);
		//Up facing frog in jumping state
		statesAndDirections[1][0] = scaleImg(spriteSheet.getIcon(1, 0), FROG_SIZE, FROG_SIZE, scale);
		
		//Left facing frog in default state
		statesAndDirections[0][1] = scaleImg(rotate90DegLeft(spriteSheet.getIcon(0, 0), FROG_SIZE, FROG_SIZE), FROG_SIZE, FROG_SIZE, scale);
		//Left facing frog in jumping state
		statesAndDirections[1][1] = scaleImg(rotate90DegLeft(spriteSheet.getIcon(1, 0), FROG_SIZE, FROG_SIZE), FROG_SIZE, FROG_SIZE, scale);
		
		//Down facing frog in default state
		statesAndDirections[0][2] = scaleImg(flipVertical(spriteSheet.getIcon(0, 0), FROG_SIZE, FROG_SIZE), FROG_SIZE, FROG_SIZE, scale);
		//Down facing frog in jumping state
		statesAndDirections[1][2] = scaleImg(flipVertical(spriteSheet.getIcon(1, 0), FROG_SIZE, FROG_SIZE), FROG_SIZE, FROG_SIZE, scale);
		
		//Right facing frog in default state
		statesAndDirections[0][3] = scaleImg(rotate90DegRight(spriteSheet.getIcon(0, 0), FROG_SIZE, FROG_SIZE), FROG_SIZE, FROG_SIZE, scale);
		//Right facing frog in jumping state
		statesAndDirections[1][3] = scaleImg(rotate90DegRight(spriteSheet.getIcon(1, 0), FROG_SIZE, FROG_SIZE), FROG_SIZE, FROG_SIZE, scale);
	}
	
	//Draws coloured frog to screen
	public void draw(Entity entity, int[] pixels)
	{
		int[] colouredFrog = replaceColour(statesAndDirections[entity.getState()][entity.getDirection()], Colour.LIGHTGREY, ((EntityFrog)entity).getColour());
		this.drawImg(pixels, colouredFrog, entity.getX(), entity.getY(), FROG_SIZE * entity.getScale(), FROG_SIZE * entity.getScale());
	}
}
