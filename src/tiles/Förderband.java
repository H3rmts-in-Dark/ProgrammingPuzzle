package tiles;

import abstractclasses.Tile;
import logic.Layers;
import logic.Rotation;
import world.Animation;
import world.Images;

public class F�rderband extends Tile {

	public F�rderband(Rotation r) {
		super(F�RDERBANDH�HE, true, 0, 0, r);
		setDescription("F�rderband");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerPicture("Default", Layers.Floor));
		addDirectionAnimation(Rotation.left,
				Animation.loadObjektAnimation("F�rderband", Rotation.left, DEFAULTANIMATION, this));
		addDirectionAnimation(Rotation.right,
				Animation.loadObjektAnimation("F�rderband", Rotation.right, DEFAULTANIMATION, this));
		setDirection(Rotation.left);
	}
}
