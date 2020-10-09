package logic;

import java.awt.Point;

import entitys.Box;
import tiles.Computer;
import tiles.Tonne;
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
		world.setTile(2,1,new Tonne());
		world.addEntity(new Box(new Point(2,2)));
	}

	public static World getWorld() {
		return world;
	}
	
}
