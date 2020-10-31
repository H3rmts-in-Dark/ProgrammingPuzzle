package tiles;

import abstractclasses.Tile;
import logic.Layers;
import world.Animation;
import world.Images;

public class Förderband extends Tile {

	public Förderband() {
		super(FÖRDERBANDHÖHE, true, 0, 0);
		setDescription("Förderband");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerpicture("Default", Layers.Floor));
		addObjektAnimation(Animation.loadObjektAnimation("Förderband", DEFAULTANIMATION, this));
	}
}
