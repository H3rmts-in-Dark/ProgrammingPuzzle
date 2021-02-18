package tasks;

import Enums.Animation;
import abstractclasses.Task;
import abstractclasses.Tile;

public class ChangeTileImageTask extends Task {

	private Tile tile;
	private Animation animation;

	public ChangeTileImageTask(Integer tickDifference, Tile tile, Integer loop, Animation animation) {
		super(tickDifference, loop);
		this.tile = tile;
		this.animation = animation;
	}

	@Override
	public boolean runCode() {
		tile.nextimage();
		return true;
	}

	@Override
	public void onEnd() {
		if (Animation.getRepeat(animation))
			tile.triggerAnimation(animation);
		else if (tile.getActivated())
			tile.triggerAnimation(Animation.activatedanimation);
		else
			tile.triggerAnimation(Animation.deactivatedanimation);
	}

}
