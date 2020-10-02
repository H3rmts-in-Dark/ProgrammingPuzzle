package logic;

import tiles.Computer;
import world.World;

public class MainControl {

	private static StateManager statemanager;
	private static GameTicker gameTicker;

	private static World world;

	private MainControl() {
	}

	public static void init() {
		statemanager = new StateManager();
		gameTicker = new GameTicker();
	}

	public static void start() {
		getGameTicker().start();
	}

	public static GameTicker getGameTicker() {
		return gameTicker;
	}

	public static StateManager getStatemanager() {
		return statemanager;
	}

	public static void createWorld() {
		world = new World(10, 10);
		world.setTile(3, 5, new Computer());
	}

	public static World getWorld() {
		return world;
	}

}
