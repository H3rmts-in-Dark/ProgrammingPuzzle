package tiles;

import abstractclasses.Tile;
import world.Animation;
import world.Images;
import logic.Layers;

public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE, ANIMATED, 10, 20, null);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerPicture("Default", Layers.Floor));
		addObjektAnimation(Animation.loadObjektAnimation("Tonne", null, DEFAULTANIMATION, this));
	}

}
