package tasks;

import abstractclasses.Task;
import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;

public class ChangeImageTask extends Task {

	final private Tile tile;
	final private Layers	layer;

	public ChangeImageTask(Integer tickDifference,Tile tile, Integer loop,Layers layer) {
		super(tickDifference, loop);
		this.tile = tile;
		this.layer = layer;
	}

	@Override
	public void runCode() {
		tile.nextimage(layer);
		tile.triggerimageupdate();
		WindowRepaintTask.RepaintWindow(tile.getWorld().getWindow());
	}
	
	@Override
	public void onEnd() {
		tile.triggerAnimation(Animations.defaultanimation);
	}
}