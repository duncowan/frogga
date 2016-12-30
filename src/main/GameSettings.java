package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameSettings 
{
	//Setup constant variables
	private final int UP_KEY_DEFAULT = 38;
	private final int DOWN_KEY_DEFAULT = 40;
	private final int LEFT_KEY_DEFAULT = 37;
	private final int RIGHT_KEY_DEFAULT = 39;
	private final int DIFFICULTY_DEFAULT = 1;
	private final int VOLUME_DEFAULT = 80;
	
	//Setup global variables
	private File settingsFile = new File("config.cfg");
	private HashMap<String, Integer> settings = new HashMap<String, Integer>();
	
	//Constructor
	public GameSettings()
	{
		//If the config file doesn't exist, create one
		if (!settingsFile.exists()) 
		{
			System.out.println("Creating configuration file...");
			
			try 
			{
				settingsFile.createNewFile();
			} 
			catch (IOException e) 
			{
				System.out.println("Could not create configuration file");
			}
			//Full the new config file with the default settings
			setDefaultSettings();
		}
		//If the config file does exist, load it
		else
		{
			System.out.println("Loading configuration file...");
			loadSettings();
			System.out.println("Loaded configuration file.");
		}
	}
	
	//Used to set a value of a setting (e.g. volume)
	public void setValue(String setting, int value)
	{
		settings.put(setting, value);
	}
	
	//Used to get the value of a setting 
	public int getValue(String setting)
	{
		return settings.get(setting);
	}
	
	//Write the current settings to the config file
	public void saveChanges()
	{
		try 
		{
			BufferedWriter buffSettingsFile = new BufferedWriter(new FileWriter(settingsFile));
			
			for(Map.Entry<String, Integer> setting : settings.entrySet())
			{
				buffSettingsFile.write(setting.getKey() + "=" + setting.getValue());
				buffSettingsFile.newLine();
			}
			
			buffSettingsFile.close();
		}
		catch (IOException e) 
		{
			System.out.println("Could not write settings to configuration file");
		}
	}
	
	//Load the settings from the config file (Only used when game first starts)
	private void loadSettings()
	{
		try 
		{
			BufferedReader buffSettingsFile = new BufferedReader(new FileReader(settingsFile));
			
			String line = "";
		    while ((line = buffSettingsFile.readLine()) != null)
		    {
		    	String[] setting = line.split("=");
		    	settings.put(setting[0], Integer.valueOf(setting[1]));
		    }
		    
		    buffSettingsFile.close();
		} 
		catch (Exception e) 
		{
			System.out.println("Could not load configuration file");
		}
	}
	
	//Sets the default values the settings (only called when a new config file is created)
	private void setDefaultSettings()
	{
		settings.put("keyUp", UP_KEY_DEFAULT);
		settings.put("keyDown", DOWN_KEY_DEFAULT);
		settings.put("keyLeft", LEFT_KEY_DEFAULT);
		settings.put("keyRight", RIGHT_KEY_DEFAULT);
		settings.put("difficulty", DIFFICULTY_DEFAULT);
		settings.put("volume", VOLUME_DEFAULT);
		
		saveChanges();
		System.out.println("Created configuration file.");
	}
}
