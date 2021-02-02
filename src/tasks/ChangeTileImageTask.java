package tasks;


import Enums.Animations;
import abstractclasses.Task;
import abstractclasses.Tile;


public class ChangeTileImageTask extends Task {

	private Tile tile;
	private Animations animation;

	public ChangeTileImageTask(Integer tickDifference,Tile tile,Integer loop,Animations animation) {
		super(tickDifference,loop);
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
		if (Animations.getRepeat(animation))
			tile.triggerAnimation(animation);
		else if (tile.getActivated())
			tile.triggerAnimation(Animations.activatedanimation);
		else
			tile.triggerAnimation(Animations.deactivatedanimation);
	}

}
