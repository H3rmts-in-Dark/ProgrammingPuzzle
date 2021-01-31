package tasks;


import Enums.Animations;
import abstractclasses.Task;
import abstractclasses.Tile;
import logic.Layers;


public class ChangeTileImageTask extends Task {

	private Tile tile;
	private Layers layer;
	private Animations animation;

	public ChangeTileImageTask(Integer tickDifference,Tile tile,Integer loop,Animations animation) {
		super(tickDifference,loop);
		this.tile = tile;
		this.animation = animation;
		this.layer = Animations.getLayer(animation);
	}

	@Override
	public boolean runCode() {
		tile.nextimage(layer);
		return true;
	}

	@Override
	public void onEnd() {
		if (Animations.getRepeat(animation))
			tile.triggerAnimation(animation);
		else if (tile.getActivated())
			tile.triggerAnimation(Animations.activatedanimation);
		else
			tile.triggerAnimation(Animations.deactivatedanimation);
	}

}
