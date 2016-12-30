package render;

import entities.Entity;

//Every entity renderer needs to extend this class
public class RenderEntity extends Renderer
{
	//Called by the RenderManger class to draw the entity to the screen
	public void draw(Entity entity, int[] pixels){}
}
