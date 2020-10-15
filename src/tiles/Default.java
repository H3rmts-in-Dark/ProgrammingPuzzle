package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(Floor,notanimated);
		setDescription("Default Tile (for testing purposes)");
	}

	@Override
	public void loadanimation() {
		setImage(Layers.Floor,Images.loadLayeranimation("Default",Layers.Floor));
	}
}