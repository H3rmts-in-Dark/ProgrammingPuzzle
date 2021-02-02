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
		super(Heights.UNPASSABLE,0,-16,signalcolor);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadPicture(Layers.Cable,Animations.onanimation,this,getColor().toString());
		World.loadPicture(Layers.Cable,Animations.offanimation,this,getColor().toString());
		World.loadAnimation(Rotations.norotation,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.norotation,Animations.activatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {

	}

}
