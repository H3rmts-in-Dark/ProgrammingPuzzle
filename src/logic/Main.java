package logic;

import frame.JFrame;
import logic.Statemanager.States;
import tiles.Computer;
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

		world = new World(10,7);
		System.out.println(System.currentTimeMillis());
		world.fillempty();
		world.setTile(5,3,new Computer());
		world.setTile(6,3,new Computer());
		System.out.println(System.currentTimeMillis());
	}

}
