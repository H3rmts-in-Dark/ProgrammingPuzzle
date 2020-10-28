package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import world.Animation;
import world.Images;
import logic.Layers;

public class Computer extends Tile {

	public Computer() {
		super(UNPASSABLE, ANIMATED, -5, -20);
		setDescription("Computer tile that gives you" + "\n" + "some information when interacted with");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor, Images.loadLayerpicture("Default", Layers.Floor));
		addObjektAnimation(Animation.loadObjektAnimation("Computer", DEFAULTANIMATION, this));
		addObjektAnimation(Animation.loadObjektAnimation("Computer", INTERACTANIMATION, this));
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USELESS INFORMATION");
	}
}
