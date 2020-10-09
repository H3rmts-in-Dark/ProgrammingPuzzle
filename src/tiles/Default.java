package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(Floor);
		setLayeranimation(Layers.Floor,Images.loadLayeranimation("default",Layers.Floor,false));
		setDescription("Default Tile (for testing purposes)");
	}
}