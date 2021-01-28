package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Förderband extends Tile {

	public Förderband(Rotations rotation) {
		super(Heights.TRANSPORT,ANIMATED,0,-15,rotation,4);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.left,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.right,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.down,Animations.defaultanimation,this);
		World.loadAnimation(Rotations.up,Animations.defaultanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
	}

}
