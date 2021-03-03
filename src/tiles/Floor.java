package tiles;

import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Cabletype;
import Enums.Height;
import Enums.Layer;
import Enums.Signalcolor;
import abstractclasses.Tile;
import world.World;

public class Floor extends Tile {

	public Floor(Signalcolor color, Cabletype cabletype) {
		super(Height.FLOORHEIGHT, 0, 0, color, cabletype);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor, Animation.noanimation, this, "Default");
	}

	@Override
	public void getdata(LinkedHashMap<String, String> list) {
	}

}
