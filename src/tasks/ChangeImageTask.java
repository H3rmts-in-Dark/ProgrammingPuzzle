package tasks;

import abstractclasses.Task;
import abstractclasses.Tile;
import frame.Frame;
import world.World.Layers;

public class ChangeImageTask extends Task {

	final private Tile tile;
	final private Layers layer;

	public ChangeImageTask(Integer tickDifference, Tile tile, Layers layer, Integer loop) {
		super(tickDifference, loop);
		this.tile = tile;
		this.layer = layer;
	}
	
	public Tile getTile() {
		return tile;
	}

	@Override
	public void runCode() {
		tile.nextImage(layer);
		Frame.repaint();
	}
}