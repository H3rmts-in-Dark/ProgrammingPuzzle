package tiles;


import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;
import world.World;


public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE,ANIMATED,10,20,Rotations.norotation,DEFAULTTPIC);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.defaultanimation,this);
	}

}
