package tiles;

import abstractclasses.Tile;
import logic.Layers;
import logic.Rotation;
import world.Animation;
import world.Images;

public class Förderband extends Tile {

	public Förderband(Rotation r) {
		super(FÖRDERBANDHÖHE, true, 0, 0, r);
		setDescription("Förderband");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerPicture("Default", Layers.Floor));
		addDirectionAnimation(Rotation.left,
				Animation.loadObjektAnimation("Förderband", Rotation.left, DEFAULTANIMATION, this));
		addDirectionAnimation(Rotation.right,
				Animation.loadObjektAnimation("Förderband", Rotation.right, DEFAULTANIMATION, this));
		setDirection(Rotation.left);
	}
}
