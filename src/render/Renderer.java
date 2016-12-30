package render;

import gui.Resource;

import java.util.Arrays;

import main.Colour;
import main.GameWindow;

public class Renderer 
{
	//Setup constant variables
	private final int TEXT_SHADOW_DISTANCE = 2;
	private final int SHADOW_COLOUR = Colour.changeBrightness(Colour.WHITE, 0.27);
	
	//Setup global variables
	protected Resource spriteSheet = new Resource("/resources/textures/spriteSheet.png");
	protected CharSet charSet = new CharSet();
	
	//Used to draw something to the screen
	//Most methods in this class call this method
	protected void drawImg(int[] pixels, int[] image, int x, int y, int width, int height)
	{
		for(int h = 0; h < height; h++)
		{
			//If the current pixel is outside the screen bounds, don't render it (could cause out of bounds error)
			if((h + y) >= 0 && (h + y) < GameWindow.HEIGHT)
			for(int w = 0; w < width; w++)
			{
				int currentPixel = image[w + h * width];
				//If the current pixel is equal to the transparent colour, don't render it
				if(currentPixel != Colour.TRANSPARENT)
				{
					//If the current pixel is outside the screen bounds, don't render it (could cause out of bounds error)
					if((w + x) >= 0 && (w + x) < GameWindow.WIDTH)
					pixels[(w + x) + (h + y) * GameWindow.WIDTH] = currentPixel;
				}
			}
		}
	}
	
	//Used to scale images
	protected int[] scaleImg(int[] image, int height, int width, int scale)
	{
		int[] scaledImage = new int[(width * scale) * (height * scale)];
		for(int h = 0; h < (height * scale); h++)
		{
			for(int w = 0; w < (width * scale); w++)
			{
				scaledImage[w + h * (width * scale)] = image[(w / scale) + (h / scale) * width];
			}
		}
		return scaledImage;
	}
	
	//Used to flip images vertically (used for frog sprite)
	protected int[] flipVertical(int[] img, int height, int width)
	{
		int[] flipedImg = new int[height * width];
		for(int h = 0; h < height; h++)
		{
			for(int w = 0; w < width; w++)
			{
				flipedImg[w + h * width] = img[w + ((height - 1) - h) * width];
			}
		}
		return flipedImg;
	}
	
	//Used to flip images horizontally (used for frog sprite and vehicle spites)
	protected int[] flipHorizontal(int[] img, int height, int width)
	{
		int[] flipedImg = new int[height * width];
		for(int h = 0; h < height; h++)
		{
			for(int w = 0; w < width; w++)
			{
				flipedImg[w + h * width] = img[((width - 1) - w) + h * width];
			}
		}
		return flipedImg;
	}
	
	//Used to rotate images 90 degrees to the left (used for frog sprite)
	protected int[] rotate90DegLeft(int[] img, int height, int width)
	{
		int[] rotatedImg = new int[height * width];
		for(int h = 0; h < height; h++)
		{
			for(int w = 0; w < width; w++)
			{
				rotatedImg[w + h * width] = img[h + w * width];
			}
		}
		return rotatedImg;
	}
	
	//Used to rotate images 90 degrees to the rihgt (used for frog sprite)
	protected int[] rotate90DegRight(int[] img, int height, int width)
	{
		return flipHorizontal(rotate90DegLeft(img, height, width), height, width);
	}
	
	//Used to replace a colour with another (used for frog sprite)
	protected int[] replaceColour(int[] pixels, int oldColour, int newColour)
	{
		int[] newPixels = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++)
		{
			if(pixels[i] == oldColour)
			{
				newPixels[i] = newColour;
			}
			else
			{
				newPixels[i] = pixels[i];
			}
		}
		return newPixels;
	}

	//Used to create a filled rectangle (used for most GUI components)
	protected void drawFilledRect(int[] pixels, int x, int y, int width, int height, int colour)
	{
		int[] solid = new int [width * height];
		Arrays.fill(solid, colour);
		drawImg(pixels, solid, x, y, width, height);
		
//		Old code to create filled rectangle 
//		for(int h = 0; h < height; h++)
//		{
//			if((h + y) >= 0 && (h + y) < GameWindow.HEIGHT)
//			for(int w = 0; w < width; w++)
//			{
//				if((w + x) >= 0 && (w + x) < GameWindow.WIDTH)
//				pixels[(w + x) + (h + y) * GameWindow.WIDTH] = colour;
//			}
//		}
	}
	
	//Used to create a striped rectangle (used for tutorial bubbles and the background of in game menus)
	protected void drawStripedRect(int[] pixels, int x, int y, int width, int height, int colour)
	{
		int[] solid = new int [width * height];
		Arrays.fill(solid, colour);
		for(int i = 0; i < solid.length; i++)
		{
			if(i % 3 == 0)
			{
				solid[i] = Colour.TRANSPARENT;
			}
		}
		drawImg(pixels, solid, x, y, width, height);
	}
	
	//Used to draw a rectangle (used in list item and the profile selection GUI)
	protected void drawRect(int[] pixels, int x, int y, int width, int height, int thickness, int colour)
	{
		int[] solid = new int [width * height];
		Arrays.fill(solid, colour);
		for(int h = thickness; h < height - thickness; h++)
		{
			for(int w = thickness; w < width - thickness; w++)
			{
				solid[w + h * width] = Colour.TRANSPARENT;
			}
		}
		drawImg(pixels, solid, x, y, width, height);
	}
	
	//Used to draw a basic string of a cretin colour
	protected void drawString(int[] pixels, String text, int scale, int x, int y, int colour)
	{
		for(int c = 0; c < text.length(); c++)
		{
			drawImg(pixels, scaleImg(charSet.getChar(text.charAt(c), colour), CharSet.CHAR_SIZE, CharSet.CHAR_SIZE, scale), (x + (c * (scale * CharSet.CHAR_SIZE))), y, (scale * CharSet.CHAR_SIZE), (scale * CharSet.CHAR_SIZE));
		}
	}
	
	//Used to draw a basic string with a shadow and colour
	protected void drawStringWithShadow(int[] pixels, String text, int scale, int x, int y, int colour)
	{
		this.drawString(pixels, text, scale, x + TEXT_SHADOW_DISTANCE, y + TEXT_SHADOW_DISTANCE, SHADOW_COLOUR);
		this.drawString(pixels, text, scale, x, y, colour);
	}
	
	//Used to draw a basic string with the default white colour
	protected void drawString(int[] pixels, String text, int scale, int x, int y)
	{
		this.drawString(pixels, text, scale, x, y, Colour.WHITE);
	}
	
	//Used to draw a basic string with a shadow and the default white colour
	protected void drawStringWithShadow(int[] pixels, String text, int scale, int x, int y)
	{
		this.drawString(pixels, text, scale, x + TEXT_SHADOW_DISTANCE, y + TEXT_SHADOW_DISTANCE, SHADOW_COLOUR);
		this.drawString(pixels, text, scale, x, y, Colour.WHITE);
	}
	
	//Used to draw a basic string that is centred with the default white colour
	protected void drawCenteredString(int[] pixels, String text, int scale, int x, int y)
	{
		drawString(pixels, text, scale, x - ((text.length() * (CharSet.CHAR_SIZE * scale)) / 2), y - ((CharSet.CHAR_SIZE * scale) / 2));
	}
	
	//Used to draw a basic string that is centred and has a shadow with the default white colour
	protected void drawCenteredStringWithShadow(int[] pixels, String text, int scale, int x, int y)
	{
		drawCenteredString(pixels, text, scale, x + TEXT_SHADOW_DISTANCE, y + TEXT_SHADOW_DISTANCE, SHADOW_COLOUR);
		drawCenteredString(pixels, text, scale, x, y);
	}
	
	//Used to draw a basic string that is centred with colour
	protected void drawCenteredString(int[] pixels, String text, int scale, int x, int y, int colour)
	{
		drawString(pixels, text, scale, x - ((text.length() * (CharSet.CHAR_SIZE * scale)) / 2), y - ((CharSet.CHAR_SIZE * scale) / 2), colour);
	}
	
	//Used to draw a basic string that is centred and has a shadow with colour
	protected void drawCenteredStringWithShadow(int[] pixels, String text, int scale, int x, int y, int colour)
	{
		drawCenteredString(pixels, text, scale, x + TEXT_SHADOW_DISTANCE, y + TEXT_SHADOW_DISTANCE, SHADOW_COLOUR);
		drawCenteredString(pixels, text, scale, x, y, colour);
	}
}
