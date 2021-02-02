package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import Enums.Signalcolors;
import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Computer extends Tile {

	public Computer(Signalcolors signalcolor) {
		super(Heights.UNPASSABLE,-5,-20,signalcolor);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.norotation,Animations.activatedanimation,this);
		World.loadAnimation(Rotations.norotation,Animations.interactanimation,this);
	}

	@Override
	public void onInteract(Entity entity) {
		triggerAnimation(Animations.interactanimation);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> list) {

	}

}
