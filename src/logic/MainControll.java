package logic;

import tiles.Computer;
import world.World;

public class MainControll {

	private static Statemanager statemanager;
	private static GameTicker gameTicker;
	
	private static World world;
	
	private MainControll() {}
	
	public static void init() {
		statemanager = new Statemanager();
		gameTicker = new GameTicker();
	}
	
	public static void start() {
		getGameTicker().start();
	}

	public static GameTicker getGameTicker() {
		return gameTicker;
	}
	
	public static Statemanager getStatemanager() {
		return statemanager;
	}
	
	public static void createWorld() {
		world = new World(10,10);
		world.setTile(3,5,new Computer());
	}

	public static World getWorld() {
		return world;
	}
	
}
