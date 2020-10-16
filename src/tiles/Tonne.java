package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE, ANIMATED);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayeranimation("Default", Layers.Floor));
		addObjektAnimation(Images.loadObjektAnimation("Tonne", DEFAULTANIMATION, this));
	}

}
