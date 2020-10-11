package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(Floor,notanimated);
		setLayeranimation(Layers.Floor,Images.loadLayeranimation("default",Layers.Floor));
		setDescription("Default Tile (for testing purposes)");
	}
}