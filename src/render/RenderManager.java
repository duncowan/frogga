package render;

import java.util.HashMap;

import level.Level;
import render.tile.RenderExitTile;
import entities.*;
import entities.tile.TileEntityExit;

public class RenderManager 
{
	//Stores the map of all the entities to their corresponding renderers
	private HashMap<Class<?>, RenderEntity> entityRenderMap = new HashMap<Class<?>, RenderEntity>();
	
	//Constructor
	public RenderManager()
	{
		//Maps all the entities to their corresponding renderers
		entityRenderMap.put(EntityFrog.class, new RenderFrog(Level.ENTITY_SCALE));
		entityRenderMap.put(EntitySlowCar.class, new RenderCar(Level.ENTITY_SCALE, 0));
		entityRenderMap.put(EntityFastCar.class, new RenderCar(Level.ENTITY_SCALE, 1));
		entityRenderMap.put(EntityTruck.class, new RenderTruck(Level.ENTITY_SCALE));
		entityRenderMap.put(EntityExplosion.class, new RenderExplosion(Level.ENTITY_SCALE));
		entityRenderMap.put(TileEntityExit.class, new RenderExitTile());
	}
	
	//Called from the draw method in Level
	//Finds the renderer that corresponds to the selected entity, then draws it to the screen
	public void renderEntity(Entity entity, int[] pixels)
	{
		RenderEntity render = (RenderEntity) entityRenderMap.get(entity.getClass());
		render.draw(entity, pixels);
	}
}
