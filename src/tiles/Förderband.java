package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Förderband extends Tile {

	public Förderband() {
		super(FÖRDERBANDHÖHE, true,0,0);
		setDescription("Förderband");
	}

	@Override
	public void loadAnimation() {
		setImage(Layers.Floor,Images.loadLayerpicture("Default",Layers.Floor));
		addObjektAnimation(Images.loadObjektAnimation("Förderband", DEFAULTANIMATION, this));
	}

}
