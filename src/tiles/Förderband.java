package tiles;


import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;


public class Förderband extends Tile {

	public Förderband(Rotations rotation) {
		super(FÖRDERBANDHÖHE,ANIMATED,0,-15,rotation);
		setDescription("Förderband");
	}

	@Override
	public void loadAnimations() {
		loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		loadAnimation(Rotations.left,Animations.defaultanimation,this,true);
		loadAnimation(Rotations.right,Animations.defaultanimation,this,true);
		loadAnimation(Rotations.down,Animations.defaultanimation,this,true);
		loadAnimation(Rotations.up,Animations.defaultanimation,this,true);
	}

}
