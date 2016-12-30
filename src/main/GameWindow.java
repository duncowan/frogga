package main;

import gui.GuiScreen;
import gui.GuiMainMenu;
import gui.Resource;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import render.RenderManager;

import level.Level;

@SuppressWarnings("serial")
public class GameWindow extends Canvas implements Runnable
{	
	//Setup constant variables
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	private final int OUTPUT_DELAY_MS = 1000;
	
	//Setup global variables
	private ImageIcon icon = new ImageIcon(Resource.class.getResource("/resources/icon/icon.png"));
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	
	private int ups;
	private int fps;
	
	private JFrame frame;
	private GameWindowListener windowListener;
	public GameSettings settings;
	public PlayerProfiles profiles;
	public RenderManager renderMananger;
	private Level currentLevel = null;
	private GuiScreen currentScreen = null;
	public InputHandler input;
	
	public int tick = -7;
	
	//Constructor
	public GameWindow()
	{
		//Sets the maximum, minimum and preferred size of the game window
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		//Sets the window's title to "FROGGA" and the icon to the default frog sprite
		frame = new JFrame("  FROGA");
		frame.setIconImage(icon.getImage());
		
		//Sets the layout of the window and what happens when the window is closed
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Adds the image where everything is displayed to the form
		frame.add(this, BorderLayout.CENTER);
		//Makes sure there is no space between the edge of the window and the image
		frame.pack();
		
		//Make the window unable to be resized
		frame.setResizable(false);
		//Center the window in the middle of the screen
		frame.setLocationRelativeTo(null);
		//Make the window visable
		frame.setVisible(true);
		
		this.init();
	}
	
	//Called after the window is created
	private void init()
	{	
		//Initialises all the variables
		windowListener = new GameWindowListener(this);
		frame.addWindowListener(windowListener);
		settings = new GameSettings();
		profiles = new PlayerProfiles();
		renderMananger = new RenderManager();
		Sound.init();
		Sound.setVolume(settings.getValue("volume"));
		Music.setVolume(settings.getValue("volume"));
		input = new InputHandler(this);
		//Display the main menu GUI
		displayScreen(new GuiMainMenu());
		//Play the main menu music
		Music.play("intro.wav");
		//Start the main game loop
		Thread t = new Thread(this);
		t.start();
		
	}
	
	//Called when the main game loop first starts
	public void run() 
	{
		long lastTime = System.nanoTime();
		double nsPerUpdate = 1000000000.0 / 60.0;
		
		int updates = 0;
		int frames = 0;
		
		long timer = System.currentTimeMillis();
		double delta = 0;
		
		//Main game loop
		while(true)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;
			
			if(delta >= 1)
			{
				updates++;
				//Calls the update method 60 times a second 
				update();
				delta--;
			}
			frames++;
			//Calls the render method as fast as possible
			render();
			
			//Runs every second
			if((System.currentTimeMillis() - timer) >= OUTPUT_DELAY_MS)
			{
				//Resets the timer
				timer += OUTPUT_DELAY_MS;
				ups = updates;
				fps = frames;
				//Outputs the frames per second, updates per second, java version, 
				//memory used by the application and the maximum memory allocated to the application
				//(Used for debugging)
				frame.setTitle("  FROGA    [" + frames + " fps" + "  |  " + updates + " ups]     Java version: " + System.getProperty("java.version") + "      Memory used " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 100 / Runtime.getRuntime().maxMemory()) + "%    Max Memory: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "MB");
				//Resets the frames per second and updates per second
				frames = 0;
				updates = 0;
			}
		}
	}
	
	//Used to load a new level
	public void loadLevel(Level level)
	{
		this.currentLevel = level;
		if(currentLevel != null)
		{
			currentLevel.load(this);
		}
	}
	
	//Used to display a new GUI
	public void displayScreen(GuiScreen screen)
	{
		this.currentScreen = screen;
		if(currentScreen != null)
		{
			currentScreen.initialize(this);
		}
	}
	
	//Called 60 times a second from the main game loop
	private void update()
	{
		tick++;
		//Update the current level logic
		if(currentLevel != null)
		{
			currentLevel.update();
			if(currentLevel != null)
			currentLevel.handleInput();
		}
		//Update the current GUI logic
		if(currentScreen != null)
		{
			currentScreen.update();
			if(currentScreen != null)
			currentScreen.handleInput();
		}
	}
	
	//Called as fast as possible from the main game loop
	private void render()
	{
		//Tries to get the buffer strategy
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null)
		{
			//Create a buffer strategy with 3 levels of buffering
			createBufferStrategy(3);
			return;
		}
		
		//Get the graphics object from the buffer strategy
		Graphics g = bs.getDrawGraphics();
		//Remove the last frame from the pixels array
		clear(pixels);
		//Add all the current level's components to the screen (vehicles, player, background)
		if(currentLevel != null)
		{
			currentLevel.draw(pixels);
		}
		//Add all the current GUI's components to the screen (buttons, textboxes, strings)
		if(currentScreen != null)
		{
			currentScreen.draw(pixels);
		}
		//Draw the image where everything is displayed on
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		//Clean up
		g.dispose();
		//Show the image
		bs.show();
	}
	
	//Sets all the main image's pixels to 0 (black)
	private void clear(int[] pixels)
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0;
		}
	}
	
	//Called just before the application exits
	public void exit()
	{
		if(isInGame())
			profiles.saveChanges();
		settings.saveChanges();
		System.exit(0);
	}
	
////////////////////////////////////////////////////Getters and Setters//////////////////////////////////////
	public int getUpdates()
	{
		return this.ups;
	}
	
	public int getFrames()
	{
		return this.fps;
	}
	
	public boolean currentGuiPausesGame()
	{
		if(this.currentScreen == null)
		{
			return false;
		}
		else
		{
			return this.currentScreen.pausesGame();
		}
	}
	
	public boolean isInGame()
	{
		return currentLevel != null;
	}
	
	public Level getCurrentLevel()
	{
		return this.currentLevel;
	}
	
	//The first thing to be called when the application starts
	public static void main(String[] args) 
	{
		new GameWindow();
	}
}
