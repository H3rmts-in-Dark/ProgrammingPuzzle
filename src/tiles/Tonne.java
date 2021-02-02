package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import Enums.Signalcolors;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Tonne extends Tile {

	public Tonne() {
		super(Heights.UNPASSABLE,10,20,Signalcolors.nocolor);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.deactivatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
	}

}
