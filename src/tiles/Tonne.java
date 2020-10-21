package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE, ANIMATED,10,20);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerAnimation("Default", Layers.Floor));
		addObjektAnimation(Images.loadObjektAnimation("Tonne", DEFAULTANIMATION, this));
	}

}
