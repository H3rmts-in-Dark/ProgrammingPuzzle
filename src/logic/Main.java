package logic;

import frame.ClassJFrame;
import world.World;

public class Main {

	public static Statemanager stateManager;
	public static World world;
	public static ClassJFrame gameFrame;

	public Main() {
		stateManager = new Statemanager();
		gameFrame = new ClassJFrame();
		// world = new World();
	}

}
