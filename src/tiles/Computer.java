package tiles;

import abstractclasses.Entity;
import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Computer extends Tile {

	public Computer() {
		super(Unpassable,animated);
		setLayeranimation(Layers.Floor,Images.loadLayeranimation("Default", Layers.Floor));
		addObjektAnimation(Images.loaddefaultObjektAnimation("Computer"));
		addObjektAnimation(Images.loadObjektAnimation("Computer","interact animation"));
		setDescription("Computer tile that gives you" + "\n" + "some information when interacted with");

		triggerdefaultanimation();
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USELESS INFORMATION");
	}
}
