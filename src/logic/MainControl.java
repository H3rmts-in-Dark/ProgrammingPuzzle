package logic;

import tiles.Computer;
import tiles.Förderband;
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
		world = new World(8, 8);
		world.setTile(1, 1, new Computer());
		world.setTile(4, 2, new Computer());
		world.setTile(1, 3, new Computer());
		world.setTile(1, 4, new Computer());
		world.setTile(0, 2, new Förderband(Rotations.right));
		world.setTile(1, 2, new Förderband(Rotations.right));
		world.setTile(2, 2, new Förderband(Rotations.right));
		world.setTile(3, 4, new Förderband(Rotations.right));
	}

	public static World getWorld() {
		return world;
	}
}
