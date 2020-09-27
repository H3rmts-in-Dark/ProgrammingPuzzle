package logic;

import java.awt.Point;

import entitys.Box;
import frame.JFrame;
import logic.Statemanager.States;
import tiles.Computer;
import world.World;

public class Main {

	public static Statemanager statemanager;
	public static JFrame frame;
	public static GameTicker gameTicker;
	
	private World world;

	public Main() {
		statemanager = new Statemanager();
		gameTicker = new GameTicker();
		gameTicker.start();

		frame = new JFrame();
		statemanager.setState(States.programming);

		world = new World(8,8);
		world.setTile(2, 3, new Computer());
		world.setTile(1, 3, new Computer());
		world.addEntity(new Box(new Point(5,6)));
	}
}
