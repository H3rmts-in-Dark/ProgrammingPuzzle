package tiles;

import logic.ImageLoader;
import tasks.ChangeImage;
import world.Tile;
import world.World.Layers;

public class Default extends Tile {

	public Default() {
		super(false, false);
		setPassable(false);
		
		addImage(ImageLoader.loadImage("Default", Layers.Floor));
		//addImage(ImageLoader.loadImage("Defaultobjekt", Layers.Objects));
		
		new ChangeImage(5,getImageholder(Layers.Floor),true);
		
	}
}