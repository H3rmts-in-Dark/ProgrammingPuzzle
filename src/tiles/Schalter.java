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


public class Schalter extends Tile {

	public Schalter(Signalcolors signalcolor) {
		super(Heights.FLOORHEIGHT,ANIMATED,0,-10,signalcolor);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.norotation,Animations.activatedanimation,this);
		World.loadAnimation(Rotations.norotation,Animations.deactivatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
	}

	@Override
	public void onInteract(Entity entity) {
		if (getActivated())
			onSignalRelayer(this,false);
		else
			onSignalRelayer(this,true);
	}

}
