package tiles;

import abstractclasses.Tile;

public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE, ANIMATED, 10, 20, null);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimation() {
// 	setImage(Layers.Floor, Images.loadLayerPicture("Default", Layers.Floor));
		loadAnimation("Tonne", null, DEFAULTANIMATION, this, true);
	}

}
