package tiles;


import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;


public class Computer extends Tile {

	public Computer() {
		super(UNPASSABLE,ANIMATED,-5,-20,Rotations.norotation);
		setDescription("Computer tile that gives you" + "\n" + "some information when interacted with");
	}

	@Override
	public void loadAnimations() {
		loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		loadAnimation(Rotations.norotation,Animations.defaultanimation,this,true);
		loadAnimation(Rotations.norotation,Animations.interactanimation,this,true);
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USE LESS INFORMATION");
	}

}
