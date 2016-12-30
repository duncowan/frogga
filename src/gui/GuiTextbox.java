package gui;

import java.awt.event.KeyEvent;

import main.Colour;

public class GuiTextbox extends GuiScreen
{
	//Setup global variables
	protected int id;
	private String text = "";
	private int x;
	private int y;
	private int width;
	private int height;
	private String filter;
	
	private boolean isFocused;
	private int cursorCounter;
	
	//First construction (used when input needs to be filtered)
	public GuiTextbox(int id, int x, int y, int width, int height, String filter)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.filter = filter;
	}
	
	//Second constructor (used when input dosn't need to be filtered)
	public GuiTextbox(int id, int x, int y, int width, int height)
	{
		this(id, x, y, width, height, "letters digits symbols");
	}
	
	//Called when a key is typed
	protected void textboxKeyTyped(KeyEvent keyEvent) 
	{
		if(isFocused)
		{
			if(charSet.charList().contains(String.valueOf(keyEvent.getKeyChar())))
			{
				//If the textbox is filtering letters and the typed key is a letter...
				if(filter.contains("letters") && Character.isLetter(keyEvent.getKeyChar()))
				{
					//Add the letter to the textbox
					text += keyEvent.getKeyChar();
					cursorCounter = 0;
				}
				
				//If the textbox is filtering digits and the typed key is a digit...
				if(filter.contains("digits") && Character.isDigit(keyEvent.getKeyChar()))
				{
					//Add the digit to the textbox
					text += keyEvent.getKeyChar();
					cursorCounter = 0;
				}
				
				//If the textbox is filtering symbols and the typed key is not a letter or digit...
				if(filter.contains("symbols") && !Character.isLetterOrDigit(keyEvent.getKeyChar()) && !Character.isSpaceChar(keyEvent.getKeyChar()))
				{
					//Add the symbol to the textbox
					text += keyEvent.getKeyChar();
					cursorCounter = 0;
				}
				
				//The space character is always added to the textbox no matter what filter it has 
				if(Character.isSpaceChar(keyEvent.getKeyChar()))
				{
					text += keyEvent.getKeyChar();
					cursorCounter = 0;
				}
				
			}
			//If the backspace key is pressed, remove the last character in the textbox
			if(keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE)
			{
				text = text.substring(0, text.length() - 1);
			}
		}
	}
	
	//Used to see if the textbox was clicked on
	protected boolean contains(int x, int y)
	{
		return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
	}

	//Causes the textbox cursor to flash
	protected void updateCursorCounter()
	{
		cursorCounter++;
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Creates a dark grey rectangle with a light grey boarder
		this.drawFilledRect(pixels, x, y, width, height, Colour.LIGHTGREY);
		this.drawFilledRect(pixels, x + 2, y + 2, width - 4, height - 4, Colour.DARKGREY);
		String displayString = text;
		boolean drawCusor = true;
		//If the length of the text is more than the width of the textbox...
		if(text.length() > ((width / 16) - 2))
		{
			//Trim the text to be displayed
			displayString = text.substring(text.length() - ((width / 16) - 1), text.length());
			//Don't draw the cursor
			drawCusor = false;
		}
		//Adds the textbox text to the screen
		this.drawStringWithShadow(pixels, displayString, 2, x + 5, (y - 8) + (height / 2));
		
		//If the textbox is focused and drawCusor is true, draw the cursor
		//cursorCounter / 40 % 2 == 0 causes the cursor to flash
		if(isFocused && cursorCounter / 40 % 2 == 0 && drawCusor)
		{
			this.drawString(pixels, "_", 2, (x + 5) + (displayString.length() * 16), (y - 8) + (height / 2));
		}
	}
///////////////////////////////////////////////////Getters and Setters////////////////////////////////////////
	public boolean isFocused() {
		return isFocused;
	}

	public void setFocused(boolean isFocused) 
	{
		if(isFocused)
		{
			cursorCounter = 0;
		}
		this.isFocused = isFocused;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
