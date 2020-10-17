package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(FLOORHEIGHT,NOTANIMATED);
		setDescription("Default Tile (for testing purposes)");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor,Images.loadLayeranimation("Default",Layers.Floor));
	}
}