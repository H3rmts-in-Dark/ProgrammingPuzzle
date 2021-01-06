package tasks;


import abstractclasses.Entity;
import abstractclasses.Task;
import abstractclasses.Tile;
import frame.WorldWindow;


public class UpadateWorld extends Task {

	final WorldWindow window;
	private Tile tile;
	private Entity entity;

	protected UpadateWorld(int tickDifference,WorldWindow window,Tile tile,Entity entity) {
		super(1,1);
		this.window = window;
		this.tile = tile;
		this.entity = entity;
	}

	@Override
	public void runCode() {
		if (tile != null)
			window.renewImage(tile);
		else 
			window.renewImage(entity);
	}

}
