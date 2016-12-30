package gui;

import main.Colour;

public class GuiButton extends GuiScreen
{
	//Sets global variables
	public int x, y, width, height;
	public int id;
	protected String text;
	private int textColour = Colour.WHITE;
	private int buttonColour = Colour.PINK;
	
	protected boolean isEnabled = true;
	protected boolean isFocused = false;
	protected boolean isVisible = true;
	protected boolean isSelected = false;

	//Constructor
	public GuiButton(int id, String text, int x, int y, int width, int height)
	{
		//Sets global variables to local variables passed from the constructor
		this.id = id;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//Used to set the colour of the button's text
	public void setTextColour(int colour)
	{
		this.textColour = colour;
	}
	
	//Used to check if the mouse cursor is within the the bounds of the button
	public boolean contains(int x, int y)
	{
		return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
	}
	
	//Called when the mouse cursor is in the bounds of the button
	public void mouseEntered()
	{
		buttonColour = Colour.RED;
	}
	
	//Called when the mouse cursor leaves the button
	public void mouseExited()
	{
		buttonColour = Colour.PINK;
	}
	
	//Used to add the graphics to the screen
	public void draw(int[] pixels)
	{
		//If the button is visible...
		if(isVisible)
		{
			//And is enabled...
			if(isEnabled)
			{
				//Create a filled rectangle with the dimensions, location and colour specified when the button was created
				this.drawFilledRect(pixels, x, y, width, height, buttonColour);
				//Draw a string in the center of the button with the text specified when the button was created
				this.drawCenteredStringWithShadow(pixels, text, (height / 10) / 2, x + (width / 2) - 1, y + (height / 2), textColour);
			}
			//If the button is disabled
			else
			{
				//Draw a filled rectangle and set the colour to a dark pink
				this.drawFilledRect(pixels, x, y, width, height, Colour.changeBrightness(Colour.PINK, 0.5));
				//Draw a string and set its colour to grey
				this.drawCenteredStringWithShadow(pixels, text, (height / 10) / 2, x + (width / 2) - 1, y + (height / 2), Colour.changeBrightness(textColour, 0.5));
			}
		}
	}
//////////////////////////////////////////////Getters and setters/////////////////////////////////////////////
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public GuiButton setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		this.isEnabled = isVisible;
		return this;
	}

	public void setFocused(boolean isFocused) {
		this.isFocused  = isFocused;
	}
	
	public boolean isFocused() {
		return this.isFocused;
	}
}
