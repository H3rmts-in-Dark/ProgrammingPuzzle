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


public class Förderband extends Tile {

	public Förderband(Rotations rotation,Signalcolors signalcolor,int speed) {
		super(Heights.TRANSPORT,0,-10,rotation,speed,signalcolor);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		World.loadAnimation(Rotations.left,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.right,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.down,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.up,Animations.deactivatedanimation,this);
		World.loadAnimation(Rotations.left,Animations.activatedanimation,this);
		World.loadAnimation(Rotations.right,Animations.activatedanimation,this);
		World.loadAnimation(Rotations.down,Animations.activatedanimation,this);
		World.loadAnimation(Rotations.up,Animations.activatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		if (getActivated())
			entity.startmove(getTicksperimagechange(),getRotation());
	}

	@Override
	public void onSignal(Tile caller,boolean activate) {
		if (getWorld().getEntityAt(this) != null)
			if (activate)
				for (Entity entity : getWorld().getEntitysAt(this)) {
					entity.startmove(getTicksperimagechange(),getRotation());
				}
	}

}
