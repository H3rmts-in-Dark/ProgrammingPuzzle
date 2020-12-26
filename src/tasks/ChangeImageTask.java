package tasks;

import abstractclasses.Task;
import abstractclasses.Tile;
import world.Animation;

public class ChangeImageTask extends Task {

	final private Tile tile;

	public ChangeImageTask(Integer tickDifference,Tile tile, Integer loop) {
		super(tickDifference, loop);
		this.tile = tile;
	}

	@Override
	public void runCode() {
		tile.nextimage();
		tile.updateimage();
	}
}