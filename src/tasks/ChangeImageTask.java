package tasks;

import abstractclasses.Task;
import abstractclasses.Tile;
import world.World.Layers;

public class ChangeImageTask extends Task {

	final private Tile tile;
	final private Layers layer;

	public ChangeImageTask(Integer tickDifference, Tile tile, Layers layer, Boolean loop) {
		super(tickDifference, loop ? -1 : 0);
		this.tile = tile;
		this.layer = layer;
	}

	@Override
	public void runCode() {
		tile.nextImage(layer);
	}
}