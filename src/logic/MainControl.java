package logic;

import entitys.Box;
import tiles.*;
import world.World;

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

	public static void createWorld() {
		world = new World(10, 10);
		world.setTile(0, 1, new Computer());
		world.addEntity(new Box(1, 1));
		world.setTile(1, 2, new Computer());
		//world.addEntity(new Box(2, 1));
		world.setTile(0, 2, new F�rderband());
	}

	public static World getWorld() {
		return world;
	}

}
