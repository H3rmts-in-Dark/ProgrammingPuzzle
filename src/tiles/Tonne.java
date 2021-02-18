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

public class Tonne extends Tile {

	public Tonne() {
		super(Height.UNPASSABLE, 10, 20, Signalcolor.nocolor, Cabletype.notype);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor, Animation.noanimation, this, "Default");
		World.load(Rotation.norotation, Animation.deactivatedanimation, this);
	}

	@Override
	public void getdata(LinkedHashMap<String, String> List) {
	}

}
