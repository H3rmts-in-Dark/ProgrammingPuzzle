package logic;

import java.awt.Point;

import entitys.Box;
import frame.DescriptionWindow;
import frame.Frame;
import logic.Statemanager.States;
import tiles.Computer;
import world.World;

public class MainControll {

	public static Statemanager statemanager;
	public static GameTicker gameTicker;
	
	private World world;

	public MainControll() {
		statemanager = new Statemanager();
		gameTicker = new GameTicker();
		gameTicker.start();
		
		statemanager.setState(States.programming);

		world = new World(8,8);
		world.setTile(2, 3, new Computer());
		world.setTile(1, 3, new Computer());
		world.addEntity(new Box(new Point(5,6)));
		Frame.addWindow(new DescriptionWindow(world.getTile(2,3)));
	}
}
