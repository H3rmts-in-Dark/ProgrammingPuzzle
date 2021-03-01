package logic;

import Programming.ProgrammingWindow;
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

	public static void createProgrammingWindow() {
		new ProgrammingWindow(world);
	}

	public static void createWorldselectionWindow() {
		new WorldSelectionWindow();
	}

}
