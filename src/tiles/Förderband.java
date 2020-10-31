package tiles;

import abstractclasses.Tile;
import logic.Layers;
import world.Animation;
import world.Images;

public class F�rderband extends Tile {

	public F�rderband() {
		super(F�RDERBANDH�HE, true, 0, 0);
		setDescription("F�rderband");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerpicture("Default", Layers.Floor));
		addObjektAnimation(Animation.loadObjektAnimation("F�rderband", DEFAULTANIMATION, this));
	}
}
