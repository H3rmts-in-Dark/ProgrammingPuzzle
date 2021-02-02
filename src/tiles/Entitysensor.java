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


public class Entitysensor extends Tile {

	public Entitysensor(Signalcolors signalcolor) {
		super(Heights.FLOORHEIGHT,0,0,signalcolor);
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		onSignalRelayer(this,true);
	}

	@Override
	public void onEntityLeft(Entity entity) {
		onSignalRelayer(this,false);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.norotation,Animations.activatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {

	}

}
