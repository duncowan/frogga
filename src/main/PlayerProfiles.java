package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerProfiles 
{
	//Setup constant variables
	private final int DEFAULT_TIME = 99;
	private final int DEFAULT_LEVEL = 1;
	private final int DEFAULT_LIVES = 2;
	private final int DEFAULT_EXITS = 4;
	
	//Setup global variables
	private File playerProfilesDir = new File("profiles");
	private HashMap<String, Integer> settingAndValue = new HashMap<String, Integer>();
	private ArrayList<String> profileNames = new ArrayList<String>();
	private HashMap<String, HashMap<String, Integer>> profiles = new HashMap<String, HashMap<String, Integer>>();
	private String selectedProfile;
	
	//Constructor
	public PlayerProfiles()
	{
		//If there is no profiles folder, make one
		if(!playerProfilesDir.exists())
		{
			System.out.println("No profiles folder");
			playerProfilesDir.mkdir();
		}
	}
	
	//Used to load all the profiles from the profiles folder
	public void loadProfiles()
	{
		profiles.clear();
		profileNames.clear();
		
		//Gets all profiles from the profiles folder
		for(File p : playerProfilesDir.listFiles())
		{
			String[] fileName = p.getName().split(".cfg");
			//Gets the profile name
			String name = fileName[0];
			
			profileNames.add(name);
			
			try 
			{
				//Reads the profile file and gets all the settings and values for that profile
				BufferedReader buffSettingsFile = new BufferedReader(new FileReader(p.getAbsoluteFile()));
				
				String line = "";
				HashMap<String, Integer> SnV = new HashMap<String, Integer>();
			    while ((line = buffSettingsFile.readLine()) != null)
			    {
			    	String[] setting = line.split("=");
			    	SnV.put(setting[0], Integer.valueOf(setting[1]));
			    }
			    profiles.put(name, SnV);
			    
			    buffSettingsFile.close();
			} 
			catch (Exception e) 
			{
				System.out.println("Could not load player profile: " + p.getName());
				profiles.remove(name);
				profileNames.remove(name);
			}
		}
	}
	
	//Used to get the currently selected profile
	public String getSelectedProfile() 
	{
		return selectedProfile;
	}

	//Used to set the selected profile
	public void setSelectedProfile(String selectedProfile) 
	{
		this.selectedProfile = selectedProfile;
		settingAndValue = profiles.get(selectedProfile);
	}

	//Used to set a setting in the selected profile
	public void setProfileSetting(String setting, int value)
	{
		settingAndValue.put(setting, value);
		profiles.put(selectedProfile, settingAndValue);
	}
	
	//Writes current settings to the currently selected profile file
	public void saveChanges()
	{
		try 
		{
			File profile = new File(playerProfilesDir.toString() + File.separator + selectedProfile + ".cfg");
			
			BufferedWriter buffProfileFile = new BufferedWriter(new FileWriter(profile));
			
			for(Map.Entry<String, Integer> setting : profiles.get(selectedProfile).entrySet())
			{
				buffProfileFile.write(setting.getKey() + "=" + setting.getValue());
				buffProfileFile.newLine();
			}
			
			buffProfileFile.close();
		}
		catch (IOException e) 
		{
			System.out.println("Could not save changes to profile");
		}
	}
	
	//Called when the user cerates a new player
	public void createProfile(String name)
	{
		setDefaultSettings();
		settingAndValue.put("score", 0);
		settingAndValue.put("totalTime", 0);
		
		profiles.put(name, settingAndValue);
		try 
		{
			File newProfile = new File(playerProfilesDir.toString() + File.separator + name + ".cfg");
			newProfile.createNewFile();
			
			BufferedWriter buffSettingsFile = new BufferedWriter(new FileWriter(newProfile));
			
			for(Map.Entry<String, Integer> setting : profiles.get(name).entrySet())
			{
				buffSettingsFile.write(setting.getKey() + "=" + setting.getValue());
				buffSettingsFile.newLine();
			}
			
			buffSettingsFile.close();
		}
		catch (IOException e) 
		{
			System.out.println("Could not create profile");
		}
	}
	
	//Used to set the default settings
	public void setDefaultSettings()
	{
		settingAndValue.put("time", DEFAULT_TIME);
		settingAndValue.put("level", DEFAULT_LEVEL);
		settingAndValue.put("lives", DEFAULT_LIVES);
		settingAndValue.put("exits", DEFAULT_EXITS);
	}
	
	//Called when the user wants to delete a profile
	public void deleteProfile(String profileName)
	{
		File profileToDelete = new File(playerProfilesDir.toString() + File.separator + profileName + ".cfg");
		profileToDelete.delete();
		loadProfiles();
	}
	
	//Gets a specific profile's setting value
	public int getProfileSettingValue(String profileName, String setting)
	{
		return profiles.get(profileName).get(setting);
	}
	
	//Gets a setting's value from the currently selected profile
	public int getProfileSettingValue(String setting)
	{
		int settingVal = getProfileSettingValue(selectedProfile, setting);
		return settingVal;
	}
	
	//Used to get all the names of the loaded profiles
	public ArrayList<String> getProfileNames()
	{
		return this.profileNames;
	}
}
