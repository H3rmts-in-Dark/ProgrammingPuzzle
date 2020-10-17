package logic;

import entitys.Box;
import tiles.*;
import world.World;

public class MainControl {

	private static StateManager stateManager;
	private static GameTicker gameTicker;
	private static World world;

	private MainControl() {
	}

	public static void initialize() {
		stateManager = new StateManager();
		gameTicker = new GameTicker();
	}

	public static void start() {
		getGameTicker().start();
	}

	public static GameTicker getGameTicker() {
		return gameTicker;
	}

	public static StateManager getStatemanager() {
		return stateManager;
	}

	public static void createWorld() {
		world = new World(10, 10);
		world.setTile(0, 1, new Computer());
		world.addEntity(new Box(1, 1));
		world.setTile(1, 2, new Computer());
		world.addEntity(new Box(2, 1));
		world.setTile(2, 0, new Computer());
	}

	public static World getWorld() {
		return world;
	}

}
