package main;

import gui.Resource;

import java.net.URL;
import java.util.Random;

import javax.sound.sampled.*;

public abstract class Music
{
	//Set global variables
	private static Clip clip;
	private static int volumeValue;
	private static String[] inGameMusic = {"music0.wav", "music1.wav", "music2.wav", "music3.wav"};
	private static int lastMusic;
	private static Random rand = new Random();

	//Called at the beginning of a new level
	public static void playRandom()
	{
		int musicPicker = rand.nextInt(inGameMusic.length);
		//Makes sure the same song doesn't play twice in a row
		while(musicPicker == lastMusic)
		{
			 musicPicker = rand.nextInt(inGameMusic.length);
		}
		lastMusic = musicPicker;
		play(inGameMusic[musicPicker]);
	}
	
	//Called when music needs to be played
	public static void play(String soundFileName) 
	{	
		//Stops the music that is currently playing
		stop();
		
		try 
		{
			//Loads the selected music file
			URL url = Resource.class.getResource("/resources/music/" + soundFileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Starts the song from the beginning 
		clip.setFramePosition(0);
		//Sets the volume of the music
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		int v = -(100 - volumeValue);
		volume.setValue(v);
		//Starts the song
		clip.start();
	}
	
	public static void stop()
	{
		if (clip != null)
			clip.stop();
	}
	
	//Called when the volume is changed
	public static void setVolume(int volumeVal)
	{
		volumeValue = volumeVal;
		if(clip != null)
		{
			//Sets the new volume
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			int v = -(100 - volumeVal);
			volume.setValue(v);
			//Refreshes the clip so the volume change takes effect
			clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		}
	}
}