package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import Enums.Signalcolors;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Lampe extends Tile {

	public Lampe(Signalcolors signalcolor) {
		super(Heights.UNPASSABLE,ANIMATED,0,0,signalcolor);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.left,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.right,Animations.activatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
		// TODO Auto-generated method stub

	}

}
