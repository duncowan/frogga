package main;

import gui.Resource;

import java.net.URL;
import javax.sound.sampled.*;

public enum Sound
{
	//Calls the sound class passing different sound files depending on the sound selected
	//To play the click sound, Sound.CLICK.play() would be used.
	//I'm new to enum classes so I don't fully understand how they work
	CLICK("click.wav"), JUMP("jump.wav"), DEATH("death.wav"), EXIT("exit.wav");

	//Setup global variables
	private Clip clip;
	private static int volumeValue;

	//Constructor
	private Sound(String soundFileName)
	{
		try 
		{
			//Loads the selected sound file
			URL url = Resource.class.getResource("/resources/sounds/" + soundFileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	//Called to play the selected sound
	public void play() 
	{
		if (clip.isRunning())
		{
			clip.stop();
		}
		//Start the clip from the beginning
		clip.setFramePosition(0);
		//Sets the volume
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		int v = -(100 - volumeValue);
		volume.setValue(v);
		//Plays the clip
		clip.start();
	}
	
	//Used to set the volume of the sounds 
	public static void setVolume(int volume)
	{
		volumeValue = volume;
	}

	//Called to preload all the sounds
	static void init() 
	{
		values();
	}
}