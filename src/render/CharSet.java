package render;

import main.Colour;
import gui.Resource;

public class CharSet
{
	protected Resource font = new Resource("/resources/textures/font.png");
	
	//Setup constant variables
	public final static int CHAR_SIZE = Resource.ICON_SIZE;
	private final int NUM_OF_CHARS_PER_LINE = font.getIconSheetWidth() / CHAR_SIZE;
	protected final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 .+-:=_!@#$%^*(),<>'\"";
	
	//Setup global variables
	protected int[][] charImg = new int[chars.length()][CHAR_SIZE * CHAR_SIZE];
	
	//Constructor
	public CharSet()
	{
		int x = 0;
		int y = 0;
		//Adds all the characters from the font image to the charImg array
		for(int c = 0; c < chars.length(); c++)
		{
			for(int h = 0; h < CHAR_SIZE; h++)
			{
				for(int w = 0; w < CHAR_SIZE; w++)
				{
					charImg[c][w + h * CHAR_SIZE] = font.getIcon(x, y)[w + h * CHAR_SIZE];
				}
			}
			x++;
			if(x == NUM_OF_CHARS_PER_LINE)
			{
				x = 0;
				y++;
			}	
		}
	}
	
	//Used to get a character from the charImg array and colour it
	public int[] getChar(char c, int colour)
	{
		int[] colouredChar = new int[CHAR_SIZE * CHAR_SIZE];
		for(int i = 0; i < colouredChar.length; i++)
		{
			//Finds the desired character in the charImg array
			//And sets every white pixel to the desired colour...
			if(charImg[chars.indexOf(c)][i] == Colour.WHITE)
			{
				colouredChar[i] = colour;
			}
			//And sets every other colour pixel to the transparent colour
			else
			{
				colouredChar[i] = Colour.TRANSPARENT;
			}
		}
		return colouredChar;
	}
	
	//Used to get all the characters that can be used
	public String charList()
	{
		return chars;
	}
}
