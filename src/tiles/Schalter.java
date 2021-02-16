package tiles;


import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Cabletype;
import Enums.Height;
import Enums.Layer;
import Enums.Rotation;
import Enums.Signalcolor;
import abstractclasses.Entity;
import abstractclasses.Tile;
import world.World;


public class Schalter extends Tile {

	public Schalter(Signalcolor signalcolor,Cabletype cabletype) {
		super(Height.FLOORHEIGHT,0,-10,signalcolor,cabletype);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor,Animation.noanimation,this,"Default");
		World.load(Rotation.norotation,Animation.activatedanimation,this);
		World.load(Rotation.norotation,Animation.deactivatedanimation,this);
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
