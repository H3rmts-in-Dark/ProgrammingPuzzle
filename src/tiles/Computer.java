package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Computer extends Tile {

	public Computer() {
		super(Heights.UNPASSABLE,ANIMATED,-5,-20);
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

	@Override
	public void getdata(LinkedHashMap<String,String> list) {
		list.put("","computer wow");
	}

}
