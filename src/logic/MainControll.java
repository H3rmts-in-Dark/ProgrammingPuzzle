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
		world.setTile(0,1,new Computer());
		world.setTile(1,1,new Computer());
		world.setTile(2,1,new Computer());
		world.setTile(3,1,new Computer());
		world.setTile(4,1,new Computer());
		world.setTile(1,5,new Computer());
		world.setTile(5,1,new Computer());
		world.setTile(7,5,new Computer());
	}

	public static World getWorld() {
		return world;
	}
	
}
