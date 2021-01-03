package tiles;


import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;

public class Default extends Tile {

	public Default() { 
		super(FLOORHEIGHT,NOTANIMATED,0,0,Rotations.norotation);
		setDescription("Default Tile (for testing purposes)"); 
	}

	@Override
	public void loadAnimations() {
		loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
	}
}
