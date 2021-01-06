package tiles;


import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;
import world.World;


public class Computer extends Tile {

	public Computer() {
		super(UNPASSABLE,ANIMATED,-5,-20,Rotations.norotation,DEFAULTTPIC);
		setDescription("Computer tile that gives you" + "\n" + "some information when interacted with");
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.norotation,Animations.interactanimation,this);
	}

	@Override
	public void onInteract(Entity entity) {
		System.out.println("interacted with computer " + toString() + "\n" + "USELESS INFORMATION");
	}

}
