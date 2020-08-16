package logic;

import States.Statemanager;
import frame.ClassJFrame;
import world.World;

public class Main {
	
	public static Statemanager statemanager;
	public static World world;
	public static ClassJFrame frame;
	
	public Main() {
		statemanager = new Statemanager();
		frame = new ClassJFrame();
		//world = new World();
	}
	
}
