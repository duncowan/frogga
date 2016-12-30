package main;

public abstract class Colour 
{
	//Setup constant colours
	public static final int TRANSPARENT = 0xff010101;
	public static final int WHITE = 0xffffffff;
	public static final int LIGHTGREY = 0xffcccccc;
	public static final int DARKGREY = 0xff222222;
	public static final int BLACK = 0xff000000;
	public static final int PINK = 0xffff00ff;
	public static final int RED = 0xffff0000;
	public static final int YELLOW = 0xffffff00;
	public static final int GREEN = 0xff00ff00;
	public static final int CYAN = 0xff00ffff;
	public static final int BLUE = 0xff0000ff;
	
	//Used to convert an RGB value to a hex colour value
	public static int rgbToHex(int r, int g, int b)
	{
		String hexStr = "";
		String red = (r < 16) ? "0" + Integer.toHexString(r) : Integer.toHexString(r);
		String green = (g < 16) ? "0" + Integer.toHexString(g) : Integer.toHexString(g);
		String blue = (b < 16) ? "0" + Integer.toHexString(b) : Integer.toHexString(b);
		hexStr = red + green + blue;
		return (int) Long.parseLong(hexStr, 16);
	}
	
	//Used to change the brightness of a hex colour
	public static int changeBrightness(int colour, double percent)
	{
		String rawHexStr = Integer.toHexString(colour);
		String hexStr = (rawHexStr.length() == 8) ? rawHexStr.substring(2) : rawHexStr;
		int newRed = (int) (Long.parseLong(hexStr.substring(0, 2), 16) * percent);
		int newGreen = (int) (Long.parseLong(hexStr.substring(2, 4), 16) * percent);
		int newBlue = (int) (Long.parseLong(hexStr.substring(4), 16) * percent);
		return rgbToHex(newRed, newGreen, newBlue);
	}
}
