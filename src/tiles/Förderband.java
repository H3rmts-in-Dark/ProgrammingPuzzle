package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Constants;
import logic.Rotation;
import world.Images;
import world.World.Layers;

public class F�rderband extends Tile implements Constants {

	private Rotation direction;
	private boolean activityStatus;

	protected F�rderband(Integer height, Boolean animated, Integer relativedrawX, Integer relativedrawY,
			Rotation rotation) {
		super(height, animated, relativedrawX, relativedrawY);
		this.direction = rotation;
		activityStatus = false;
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerAnimation("F�rderband", Layers.Floor));
		addObjektAnimation(Images.loadObjektAnimation("F�rderband", DEFAULTANIMATION, this));
		addObjektAnimation(Images.loadObjektAnimation("F�rderband", ACTIVEANIMATION, this));
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		if (activityStatus) {
			entity.turn(direction);
			entity.move();
		}
	}

	public void setActivityState(boolean state) {
		activityStatus = state;
	}
}
