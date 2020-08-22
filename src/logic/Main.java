package logic;

import frame.JFrame;
import logic.Statemanager.States;
import world.World;

public class Main {

	public static Statemanager statemanager;
	public static World world;
	public static JFrame frame;
	public static GameTick gameTicker;
	
	public static Integer tilewitdh = 64;
	
	public Main() {
		statemanager = new Statemanager();
		gameTicker = new GameTick();
		
		frame = new JFrame();
		statemanager.setState(States.mainmenu);
		
		world = new World(10,10);
		world.fillempty();
	}

}
