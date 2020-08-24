package logic;

import frame.JFrame;
import logic.Statemanager.States;
import world.World;

public class Main {

	public static Statemanager statemanager;
	public static World world;
	public static JFrame frame;
	public static GameTicker gameTicker;

	public static Integer tilewidth = 64;

	public Main() {
		statemanager = new Statemanager();
		gameTicker = new GameTicker();
		gameTicker.start();

		frame = new JFrame();
		statemanager.setState(States.mainmenu);

		world = new World(10, 10);
		world.fillempty();
<<<<<<< HEAD
=======

		// gameTicker.addTask(new tasks.ChangeImage((double) 10, null, true));
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
	}

}
