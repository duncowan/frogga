package gui;

import java.util.ArrayList;

import main.Colour;

public class GuiTutBubble extends GuiButton
{
	//Setup constant variables
	private final static int SCALE = 2;
	private final static int PADDING = 10;
	private final int BORDER = 16;
	private final int LINE_HEIGHT = 4;
	private final static int LENGTH_OF_LINE = 20;
	
	//Setup global variables
	private ArrayList<String> lineText = new ArrayList<String>();
	private int spaceIndex, lastSpaceIndex;
	
	//Constructor
	public GuiTutBubble(int id, String text, int x, int y)
	{
		super(id, text, x, y, ((text.length() > LENGTH_OF_LINE ? LENGTH_OF_LINE : text.length()) * (Resource.ICON_SIZE * SCALE)) + PADDING, (Resource.ICON_SIZE * SCALE) + PADDING);
		//Splits the string to make it fit in the rectangle
		if(text.length() > LENGTH_OF_LINE)
		{
			for(int i = 0, j = 0; i < text.length(); i++, j++)
			{
				spaceIndex = text.charAt(i) == ' ' ? i : spaceIndex;
				if(j == LENGTH_OF_LINE)
				{
					lineText.add(text.substring(lastSpaceIndex, spaceIndex));
					lastSpaceIndex = spaceIndex + 1;
					j = (i - spaceIndex);
				}
			}
			lineText.add(text.substring(lastSpaceIndex));
		}
		else
		{
			lineText.add(text);
		}
		this.height = ((Resource.ICON_SIZE * SCALE) * lineText.size()) + ((lineText.size() - 1) * LINE_HEIGHT) + PADDING;
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels)
	{
		//If the tutorial bubble is visible
		if(isVisible)
		{
			//Draws striped boarder
			this.drawStripedRect(pixels, x - (BORDER / 2), y - (BORDER / 2), ((text.length() > LENGTH_OF_LINE ? LENGTH_OF_LINE : text.length()) * (Resource.ICON_SIZE * SCALE)) + (PADDING + BORDER), ((Resource.ICON_SIZE * SCALE) * lineText.size()) + ((lineText.size() - 1) * LINE_HEIGHT) + (PADDING + BORDER), Colour.BLACK);
			//Draws yellow filed rectangle 
			this.drawFilledRect(pixels, x, y, ((text.length() > LENGTH_OF_LINE ? LENGTH_OF_LINE : text.length()) * (Resource.ICON_SIZE * SCALE)) + PADDING, ((Resource.ICON_SIZE * SCALE) * lineText.size()) + ((lineText.size() - 1) * LINE_HEIGHT) + PADDING, Colour.YELLOW);
			//Adds tutorial bubble text to screen
			for(int i = 0; i < lineText.size(); i++)
			{
				this.drawString(pixels, lineText.get(i), SCALE, x + (PADDING / 2), y + (i  * ((Resource.ICON_SIZE * SCALE) + LINE_HEIGHT)) + (PADDING / 2), Colour.BLACK);
			}
		}
	}
}
