package tiles;


import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;
import world.World;


public class Default extends Tile {

	public Default() {
		super(FLOORHEIGHT,Rotations.norotation);
		setDescription("Default Tile (for testing purposes)");
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
	}

}
