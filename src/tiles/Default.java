package tiles;

import logic.ImageLoader;
import world.Tile;
import world.World.Layers;

public class Default extends Tile {

	public Default(Integer X,Integer Y) {
		super(X,Y,false);
		setImage(Layers.Floor,ImageLoader.loadImage("Default"));
		setImage(Layers.Objects,ImageLoader.loadImage("Defaultdeco"));
		setPassable(false);
	}
	
	@Override
	public void onInteract() {
		System.out.println("interacted with default tile on X:"
				+ location.getX() + " Y:" + location.getY());
	}
	
	@Override
	public void onSteppedUpon() {
		System.out.println("stepped on default tile on X:"
				+ location.getX() + " Y:" + location.getY());
	}
}