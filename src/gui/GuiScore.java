package gui;

import level.Level;
import main.Colour;
import main.GameWindow;
import main.Music;

public class GuiScore extends GuiScreen
{
	//Setup constant variables
	private final int POINTS_FOR_LIVES = 1000;
	private final int POINTS_FOR_LEVEL_COMPLETION = 500;
	private final int SCORE_Y_MODIFIER;
	
	//Setup global variables
	private int curLevel;
	private int totalScore;
	private String totalTime;
	private boolean isLastLevel = false;
	private boolean displayTotalScore = false;
	
	private int score;
	private int scoreColour;
	private int tick;
	
	//Constructor
	public GuiScore(Level level, boolean isLastLevel)
	{
		//If its the last level, set the score text's y position 80 pixels above the center.
		//If its not he last level, set the score text's y position 40 pixels above the center.
		SCORE_Y_MODIFIER = isLastLevel ? 80 : 40;
		
		this.isLastLevel = isLastLevel;
		curLevel = level.getLevelNum();
		
		//Gets the last total score
		int lastScore = level.game.profiles.getProfileSettingValue("score");
		//Calculates the score for the current level
		score = ((POINTS_FOR_LIVES * level.getLives()) * level.getTime()) + POINTS_FOR_LEVEL_COMPLETION;
		//Adds that score to the total score
		level.game.profiles.setProfileSetting("score", lastScore + score);
		//Sets the total score equal to the updated total score 
		totalScore = level.game.profiles.getProfileSettingValue("score");
		
		//Gets the last total time
		int lastTime = level.game.profiles.getProfileSettingValue("totalTime");
		//Adds the time from the current level
		level.game.profiles.setProfileSetting("totalTime", (lastTime + (99 - level.getTime())));
		//Sets total time equal to the updated total time
		totalTime = decToTime(level.game.profiles.getProfileSettingValue("totalTime"));
		
		//Resets the players settings
		level.game.profiles.setDefaultSettings();
		//Sets the level the player will be on next
		level.game.profiles.setProfileSetting("level",(curLevel + 1));
		//Saves the changes to the player's profile
		level.game.profiles.saveChanges();
	}
	
	//Called when GUI first loads
	public void init()
	{	
		//Play the level complete music
		Music.play("levelcomplete.wav");
		//Adds the "Continue" button to the screen
		this.buttons.add(new GuiButton(0, "Continue", (GameWindow.WIDTH / 2) - 200, 530, 400, 40));
	}
	
	//Called when a button is clicked
	protected void buttonClicked(GuiButton button) 
	{
		//If the "Continue" button is clicked
		if(button.id == 0)
		{
			//If the current level is not the last level, load the next level
			if(!isLastLevel)
			{
				game.displayScreen(new GuiLevelTransition(curLevel + 1));
			}
			//If the "Continue" button is clicked after the score for the final level has been displayed...
			if(displayTotalScore)
			{
				//Reset the players settings
				this.game.profiles.setDefaultSettings();
				//Set the players current level to 10
				this.game.profiles.setProfileSetting("level", 10);
				//Save the changes
				this.game.profiles.saveChanges();
				//Display the credits GUI
				game.displayScreen(new GuiCredits());
			}
			displayTotalScore = isLastLevel;
		}
	}

	//Called on every tick
	public void update() 
	{
		tick++;
		//Every 20 ticks, change the colour of the score text (causes a flashing effect)
		if(tick % 20 == 0)
		{
			if(scoreColour == Colour.CYAN)
			{
				scoreColour = Colour.PINK;
			}
			else
			{
				scoreColour = Colour.CYAN;
			}
		}
	}
	
	//Converts decimal time to minutes and seconds
	private String decToTime(int decTime)
	{
		int rawMin = decTime / 60;
		String min = String.valueOf(rawMin);
		
		int rawSec = decTime - (rawMin * 60);
		String sec = String.valueOf(rawSec);
		
		System.out.println(min + ":" + (rawSec < 10 ? "0" + sec : sec));
		return min + ":" + (rawSec < 10 ? "0" + sec : sec);
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//If the "Continue" button is clicked after the score for the final level has been displayed...
		if(!displayTotalScore)
		{
			//Adds the current level's score to the screen
			this.drawCenteredStringWithShadow(pixels, "SCORE:", 3, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) - SCORE_Y_MODIFIER - 70);
			this.drawCenteredString(pixels, String.valueOf(score), 10, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) - SCORE_Y_MODIFIER, scoreColour);
		}
		else
		{
			//Adds the total score and total time taken to the screen
			this.drawCenteredStringWithShadow(pixels, "FINAL SCORE:", 3, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) - SCORE_Y_MODIFIER - 70);
			this.drawCenteredString(pixels, String.valueOf(totalScore), 10, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) - SCORE_Y_MODIFIER, scoreColour);
			this.drawCenteredStringWithShadow(pixels, "TIME TAKEN:", 2, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) + SCORE_Y_MODIFIER - 50);
			this.drawCenteredString(pixels, totalTime, 6, GameWindow.WIDTH / 2, (GameWindow.HEIGHT / 2) + SCORE_Y_MODIFIER, scoreColour);
		}
		//Draws all the buttons and textboxes to the screen
		super.draw(pixels);
	}
}
