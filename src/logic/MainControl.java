package logic;


import world.World;
import world.WorldSelectionWindow;


public class MainControl {

	private static GameTicker gameTicker;
	private static World world;

	private MainControl() {
	}

	public static void initialize() {
		gameTicker = new GameTicker();
	}

	public static void start() {
		getGameTicker().start();
	}

	public static GameTicker getGameTicker() {
		return gameTicker;
	}

	public static void deleteWorld() {
		world.delete();
	}

	public static void setWorld(World world) {
		MainControl.world = world;
	}
	
	public static World getWorld() {
		return world;
	}

	public static void createWorldselectionWindow() {
		new WorldSelectionWindow();
	}

}
