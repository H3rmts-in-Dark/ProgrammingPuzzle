package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Tonne extends Tile {

	public Tonne() {
		super(Heights.UNPASSABLE,ANIMATED,10,20);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.defaultanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
	}

}
