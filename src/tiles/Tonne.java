package tiles;

import abstractclasses.Tile;
import world.Animation;
import world.Images;
import logic.Layers;

public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE, ANIMATED,10,20);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerpicture("Default", Layers.Floor));
		addObjektAnimation(Animation.loadObjektAnimation("Tonne", DEFAULTANIMATION, this));
	}

}
