package tiles;


import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Cabletype;
import Enums.Height;
import Enums.Layer;
import Enums.Rotation;
import Enums.Signalcolor;
import abstractclasses.Tile;
import world.World;


public class Lampe extends Tile {

	public Lampe(Signalcolor signalcolor,Cabletype cabletype) {
		super(Height.UNPASSABLE,0,-6,signalcolor,cabletype);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor,Animation.noanimation,this,"Default");
		World.load(Rotation.norotation,Animation.deactivatedanimation,this);
		World.load(Rotation.norotation,Animation.activatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
	}

}
