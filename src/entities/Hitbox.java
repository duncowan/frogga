package entities;

import java.awt.Point;

public class Hitbox 
{
	//Setup global variables
	private int x;
	private int y;
	private int width;
	private int height;

	//Constructor
	public Hitbox(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = (width - 1);
		this.height = (height - 1);
	}
	
	//Used to see if the hitbox collides with another hitbox (array of Points)
	public boolean intersectsWith(Point[] hitbox)
	{
		for(int p = 0; p < hitbox.length; p++)
		{
			if(hitbox[p].x >= this.x && hitbox[p].x <= this.x + this.width && hitbox[p].y >= this.y && hitbox[p].y <= this.y + this.height)
			{
				return true;
			}
		}
		return false;
	}
	
	//Used to get the points of the hitbox
	public Point[] hitbox()
	{
		Point[] pointsInHitbox = new Point[(this.height * 2) + (this.width * 2)];
		//Gets points of top edge
		for(int w = 0; w < this.width; w++)
		{
			pointsInHitbox[w] = new Point((x + w), y);
		}
		
		//Gets points on bottom edge
		for(int w = 0; w < this.width; w++)
		{
			pointsInHitbox[w + this.width] = new Point((x + w), (y + this.height));
		}
		
		//Gets points on left edge
		for(int h = 0; h < this.height; h++)
		{
			pointsInHitbox[h + (this.width * 2)] = new Point(x, (y + h));
		}
		
		//Gets points on right edge
		for(int h = 0; h < this.height; h++)
		{
			pointsInHitbox[h + ((this.width) * 2) + this.height] = new Point((x + this.width), (y + h));
		}
		return pointsInHitbox;
	}

//////////////////////////////////////Getters and Setters///////////////////////////////////////////////////
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
