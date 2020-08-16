package tiles;

import world.Tile;

public class Default extends Tile {

	public Default(Integer X,Integer Y,Boolean interactable) {
		super(X,Y,interactable);
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