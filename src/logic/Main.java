package logic;

import frame.ClassJFrame;
import logic.Statemanager.States;
import world.World;

public class Main {
	
	public static Statemanager statemanager;
	public static World world;
	public static ClassJFrame frame;
	
	public Main() {
		statemanager = new Statemanager();
		frame = new ClassJFrame();
		statemanager.setState(States.mainmenu);
		//world = new World();
	}
	
}
