package level;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import render.Renderer;

import entities.Entity;
import entities.EntityFastCar;
import entities.EntitySlowCar;
import entities.EntityFrog;
import entities.EntityTruck;
import entities.Hitbox;
import entities.tile.TileEntityExit;
import gui.GuiGameOver;
import gui.GuiHud;
import gui.GuiInGameMenu;
import gui.GuiScore;
import gui.GuiTutorial;
import gui.Resource;

import main.GameWindow;
import main.InputHandler;

public class Level extends Renderer
{
	//Setup constant variables
	public static final int ENTITY_SCALE = 5;
	public static final int SPACING_BETWEEN_ROWS = 10;
	private final int ENTITY_SIZE = Resource.ICON_SIZE;
	private final int ENTITY_INGAME_SIZE = ENTITY_SIZE * ENTITY_SCALE;
	
	//Setup global variables
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> entitiesToAdd = new ArrayList<Entity>();
	private ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
	
	private GuiHud hud;
	private int levelNumber;
	private int time;
	private int lives;
	public int numOfExits;
	private Random rand = new Random();
	
	public GameWindow game;
	private InputHandler input;
	private Hitbox levelBounds;
	
	//Constructor
	public Level(int levelNumber)
	{
		this.levelNumber = levelNumber;
	}
	
	//Called when the level changes
	public void load(GameWindow game) 
	{
		this.game = game;
		this.input = game.input;
		levelBounds = new Hitbox(-(SPACING_BETWEEN_ROWS + 1), -(SPACING_BETWEEN_ROWS + 1), GameWindow.WIDTH + (SPACING_BETWEEN_ROWS + 1) * 2, GameWindow.HEIGHT + (SPACING_BETWEEN_ROWS + 1) * 2);
		entities.clear();
		this.init();
		hud = new GuiHud(this);
		//If this is level 0, display the tutorial GUI
		if(levelNumber == 0)
			this.game.displayScreen(new GuiTutorial());
	}
	
	//Called when the level first loads
	protected void init()
	{	
		//Sets the number of exits and lives for the level using the selected profile's settings
		numOfExits = this.game.profiles.getProfileSettingValue("exits");
		lives = this.game.profiles.getProfileSettingValue("lives");
		
		//Adds the exits to the level
		for(int i = 0; i < numOfExits; i++)
		{
			entities.add(new TileEntityExit(this, ((GameWindow.WIDTH / 2) - (SPACING_BETWEEN_ROWS / 2)) - ((numOfExits - 1) * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + (i * ((ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS) * 2)), 0, ENTITY_SCALE));
		}
		
		//Adds the player to the level
		int playerColour = this.game.profiles.getProfileSettingValue("colour");
		entities.add(new EntityFrog(this, playerColour, (GameWindow.WIDTH / 2), GameWindow.HEIGHT - ENTITY_INGAME_SIZE, ENTITY_SCALE));
		
		//If this is the tutorial level, add 3 rows of vehicles
		//If it isn't, add rows of vehicles based on what level this is
		//(e.g. level 4 has 4 rows of vehicles)
		generateLevel(levelNumber == 0 ? 3 : levelNumber);
	}
	
	//Used to add the vehicles to the level
	private void generateLevel(int numOfLines)
	{
		for(int i = 0; i < numOfLines; i++)
		{
			//Used to pick what type of vehicle (slow car, fast car, truck)
			int vehiclePicker = rand.nextInt(3);
			//Used to pick how many vehicles will be a row (from 1 to 4)
			int vehiclesInRow = rand.nextInt(4) + 1;
			//Used to start the row of vehicles at a random x position
			int vehicleStartPosModifier = rand.nextInt(500);
			
			//Vehicles will be going from right to left
			if(i % 2 == 0)
			{
				//Row will consist of trucks
				if(vehiclePicker == 2)
				{
					//Adds vehicles to row based on the value of vehiclesInRow
					for(int j = 0; j < vehiclesInRow; j++)
					{
						entities.add(new EntityTruck(this, -j * ((ENTITY_INGAME_SIZE * 2) + 40) - (200 + vehicleStartPosModifier), GameWindow.HEIGHT - ((i * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + ((ENTITY_INGAME_SIZE * 2) + SPACING_BETWEEN_ROWS)), ENTITY_SCALE, 0));
					}
				}
				//Row will consist of slow cars
				else if(vehiclePicker == 1)
				{
					//Adds vehicles to row based on the value of vehiclesInRow
					for(int j = 0; j < vehiclesInRow; j++)
					{
						entities.add(new EntitySlowCar(this, -j * (ENTITY_INGAME_SIZE + 80) - (200 + vehicleStartPosModifier), GameWindow.HEIGHT - ((i * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + ((ENTITY_INGAME_SIZE * 2) + SPACING_BETWEEN_ROWS)), ENTITY_SCALE, 0));
					}
				}
				//Row will consist of fast cars
				else if(vehiclePicker == 0)
				{
					//Adds vehicles to row based on the value of vehiclesInRow
					for(int j = 0; j < vehiclesInRow; j++)
					{
						entities.add(new EntityFastCar(this, -j * (ENTITY_INGAME_SIZE + 80) - (200 + vehicleStartPosModifier), GameWindow.HEIGHT - ((i * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + ((ENTITY_INGAME_SIZE * 2) + SPACING_BETWEEN_ROWS)), ENTITY_SCALE, 0));
					}
				}
			}
			//Vehicles will be going from left to right
			else
			{
				//Row will consist of trucks
				if(vehiclePicker % 3 == 2)
				{
					//Adds vehicles to row based on the value of vehiclesInRow
					for(int j = 0; j < vehiclesInRow; j++)
					{
						entities.add(new EntityTruck(this, j * ((ENTITY_INGAME_SIZE * 2) + 40) - (200 + vehicleStartPosModifier), GameWindow.HEIGHT - ((i * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + ((ENTITY_INGAME_SIZE * 2) + SPACING_BETWEEN_ROWS)), ENTITY_SCALE, 1));
					}
				}
				//Row will consist of slow cars
				else if(vehiclePicker % 3 == 1)
				{
					//Adds vehicles to row based on the value of vehiclesInRow
					for(int j = 0; j < vehiclesInRow; j++)
					{
						entities.add(new EntitySlowCar(this, j * (ENTITY_INGAME_SIZE + 80) - (200 + vehicleStartPosModifier), GameWindow.HEIGHT - ((i * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + ((ENTITY_INGAME_SIZE * 2) + SPACING_BETWEEN_ROWS)), ENTITY_SCALE, 1));
					}
				}
				//Row will consist of fast cars
				else if(vehiclePicker % 3 == 0)
				{
					//Adds vehicles to row based on the value of vehiclesInRow
					for(int j = 0; j < vehiclesInRow; j++)
					{
						entities.add(new EntityFastCar(this, j * (ENTITY_INGAME_SIZE + 80) - (200 + vehicleStartPosModifier), GameWindow.HEIGHT - ((i * (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS)) + ((ENTITY_INGAME_SIZE * 2) + SPACING_BETWEEN_ROWS)), ENTITY_SCALE, 1));
					}
				}
			}
		}
	}
	
	//Called on every tick
	public void update() 
	{
		//If the game is not paused...
		if(!this.game.currentGuiPausesGame())
		{	
			//Remove all entities from the entitiesToRemove and entitiesToAdd arrays
			//This is done to stop the programme from trying to remove or add entities that have already been
			//added or removed
			entitiesToRemove.clear();
			entitiesToAdd.clear();
			//Updates the entities logic (moving, animation, etc)
			for(Entity e : entities)
			{	
				e.update();
			}
			//Updates the lives and time display
			hud.update();
			//Remove all entities that need to be removed
			entities.removeAll(entitiesToRemove);
			//Add all entities that need to be added
			entities.addAll(entitiesToAdd);
		}
	}

	//Used to add the graphics to the screen
	public void draw(int[] pixels) 
	{
		//Draws the grey rectangle to the screen (the road)
		this.drawFilledRect(pixels, 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, 0x404040);
		//Draws the purple rectangle at the top of the level (where the exits are)
		this.drawFilledRect(pixels, 0, 0, GameWindow.WIDTH, ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS, 0xac02ca);
		//Draws the purple rectangle at the bottom of the level (where the player spawns)
		this.drawFilledRect(pixels, 0, GameWindow.HEIGHT - (ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS) + 4, GameWindow.WIDTH, ENTITY_INGAME_SIZE + SPACING_BETWEEN_ROWS, 0xac02ca);
		//Renders the entities
		for(Entity e : entities)
		{
			game.renderMananger.renderEntity(e, pixels);
		}
		//Draws the lives and time to the screen
		hud.draw(pixels);
	}
	
	//Called when the player has gone through all the exits
	public void loadNextLevel()
	{
		if(levelNumber >= 10)
		{
			this.game.loadLevel(null);
			this.game.displayScreen(new GuiScore(this, true));
		}
		else
		{
			this.game.loadLevel(null);
			this.game.displayScreen(new GuiScore(this, false));
		}
	}
	
	//Called to add an entity
	public void addEntity(Entity entity)
	{
		this.entitiesToAdd.add(entity);
	}
	
	//Called to remove an entity
	public void removeEntity(Entity entity)
	{
		this.entitiesToRemove.add(entity);
	}
	
	//Called when the frog dies
	public void decreaseLives()
	{
		lives--;
		if(lives < 0)
		{
			game.displayScreen(new GuiGameOver());
		}
		hud.updateLives();
		this.game.profiles.setProfileSetting("lives", lives);
	}
	
////////////////////////////////////////Getters and Setters///////////////////////////////////////////////	
	public int getLevelNum()
	{
		return this.levelNumber;
	}
	
	public int getLives()
	{
		return this.lives;
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public void setTime(int time)
	{
		this.time = time;
	}
	
	public Point[] getLevelBounds()
	{
		return this.levelBounds.hitbox();
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	//Called when a key is pressed
	private void keyPressed(KeyEvent keyEvent) 
	{
		//Displayed the in game menu GUI when the escape key is pressed
		if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			game.displayScreen(new GuiInGameMenu());
		}
		
		//Moves the frog when one of the movement keys are pressed
		for(Entity e : entities)
		{
			if(e instanceof EntityFrog)
			{
				EntityFrog frog = (EntityFrog) e;
				//Moves the frog up when the up key is pressed
				if(keyEvent.getKeyCode() == game.settings.getValue("keyUp"))
				{
					frog.move((byte) 00);
					//Moves the frog the opposite way if it collides with the level bounds
					if(frog.intersectsWith(levelBounds.hitbox()))
						frog.move((byte) 10);
				}
				//Moves the frog left when the left key is pressed
				else if(keyEvent.getKeyCode() == game.settings.getValue("keyLeft"))
				{
					frog.move((byte) 01);
					//Moves the frog the opposite way if it collides with the level bounds
					if(frog.intersectsWith(levelBounds.hitbox()))
						frog.move((byte) 11);
				}
				//Moves the frog down when the down key is pressed
				else if(keyEvent.getKeyCode() == game.settings.getValue("keyDown"))
				{
					frog.move((byte) 10);
					//Moves the frog the opposite way if it collides with the level bounds
					if(frog.intersectsWith(levelBounds.hitbox()))
						frog.move((byte) 00);
				}
				//Moves the frog right when the right key is pressed
				else if(keyEvent.getKeyCode() == game.settings.getValue("keyRight"))
				{
					frog.move((byte) 11);
					//Moves the frog the opposite way if it collides with the level bounds
					if(frog.intersectsWith(levelBounds.hitbox()))
						frog.move((byte) 01);
				}
			}
		}
	}

	boolean keyDown = false;
	//Called on every tick
	public void handleInput()
	{
		if(!this.game.currentGuiPausesGame())
		{
			if(input.keyPressed)
			{
				if(!keyDown)
				{
					keyPressed(input.getKeyEvent());
					keyDown = true;
				}
			}
			else
			{
				if(keyDown)
				{
					//keyTyped method goes here
					keyDown = false;
				}
			}
		}
	}
}
