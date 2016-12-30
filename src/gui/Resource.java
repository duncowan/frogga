package gui;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Resource 
{
	//Size of spites on sprite sheet (Default: 8x8 pixels)
	public static final int ICON_SIZE = 8;
	
	//Setup global variables
	private int[] pixels;
	private int iconSheetWidth;
	
	//Constructor
	public Resource(String file)
	{
		this.load(file);
	}
	
	//Loads the image from the specified location 
	public void load(String file)
	{
		try
		{
			//Create new buffered image and load the image into it
			BufferedImage iconSheet = ImageIO.read(Resource.class.getResource(file));
			pixels = new int[iconSheet.getWidth() * iconSheet.getHeight()];
			//Adds all the pixel data from the image to the pixels array
			iconSheet.getRGB(0, 0, iconSheet.getWidth(), iconSheet.getHeight(), pixels, 0, iconSheet.getWidth());
			this.iconSheetWidth = iconSheet.getWidth();
		} 
		catch (Exception e) 
		{
			System.out.println("Could not load icon sheet");
		}
	}
	
	//Used to get a specific icon from the icon sheet
	//(e.g. to get the default frog sprite, getIcon(0, 0) would be used)
	public int[] getIcon(int x, int y)
	{
		int[] icon = new int[ICON_SIZE * ICON_SIZE];
		for(int h = 0; h < ICON_SIZE; h++)
		{
			for(int w = 0; w < ICON_SIZE; w++)
			{
				icon[w + h * ICON_SIZE] = pixels[(w + (x * ICON_SIZE)) + (h + (y * ICON_SIZE)) * iconSheetWidth];
			}
		}
		return icon;
	}
	
	//Gets width of the icon sheet
	//Used in the CharSet class
	public int getIconSheetWidth()
	{
		return this.iconSheetWidth;
	}
}
