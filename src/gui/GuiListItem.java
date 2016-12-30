package gui;

import main.Colour;

public class GuiListItem extends GuiButton
{
	//Setup constant variables
	private final static int WIDTH = 500;
	private final static int HEIGHT = 80;
	private final int BORDER_THICKENSS = 2;
	
	//Constructor
	public GuiListItem(int id, String text, int x, int y) 
	{
		super(id, text, x, y, WIDTH, HEIGHT);
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Sets the background as a dark grey filled rectangle
		this.drawFilledRect(pixels, x, y, WIDTH, HEIGHT, Colour.DARKGREY);
		//Adds the list item's text to the screen
		this.drawCenteredStringWithShadow(pixels, getText(), 2, x + (WIDTH / 2) , y + (HEIGHT / 2));
		//Draws a white boarder around the edge of the list item when it has focus (is clicked on)
		if(this.isFocused)
			this.drawRect(pixels, x, y, WIDTH, HEIGHT, BORDER_THICKENSS, Colour.WHITE);
	}
}
