package logic;

import frame.ClassJFrame;
import logic.Statemanager.States;
import world.World;

public class Main {

	public static Statemanager stateManager;
	public static World world;
	public static ClassJFrame gameFrame;

	public Main() {
<<<<<<< HEAD
		statemanager = new Statemanager();
		frame = new ClassJFrame();
		statemanager.setState(States.mainmenu);
		//world = new World();
=======
		stateManager = new Statemanager();
		gameFrame = new ClassJFrame();
		// world = new World();
>>>>>>> 4d4f60ed9fafffb2b90637841b303df9e224796b
	}

}
