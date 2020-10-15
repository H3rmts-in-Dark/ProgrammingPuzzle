package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Tonne extends Tile {

	public Tonne() {
		super(Unpassable,animated);
		setDescription("Barriere");
	}

	@Override
	public void loadanimation() {
		setImage(Layers.Floor, Images.loadLayeranimation("Default", Layers.Floor));
		addObjektAnimation(Images.loadObjektAnimation("Tonne", "default animation",this),defaultanimation);
	}

}
