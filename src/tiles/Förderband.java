package tiles;


import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;
import world.World;


public class Förderband extends Tile {

	public Förderband(Rotations rotation) {
		super(FÖRDERBANDHÖHE,ANIMATED,0,-15,rotation,4);
		setDescription("Förderband");
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.left,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.right,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.down,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.up,Animations.defaultanimation,this);
	}

}
