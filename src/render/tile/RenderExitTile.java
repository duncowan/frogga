package render.tile;

import entities.Entity;
import entities.tile.TileEntityExit;
import render.RenderEntity;

public class RenderExitTile extends RenderEntity
{
	//Draws exit tile to the screen
	public void draw(Entity entity, int[] pixels)
	{
		//Creates a grey rectangle
		this.drawFilledRect(pixels, entity.getX(), entity.getY(), ((TileEntityExit)entity).getWidth(), ((TileEntityExit)entity).getHeight(), 0x404040);
		//Create a string with the text "^" (it looks like an arrow)
		this.drawString(pixels, "^", entity.getScale() - 1, entity.getX() + 8, entity.getY() + ((TileEntityExit)entity).getArrowPos(), 0xffff00);
	}
}
