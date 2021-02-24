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

public class GewichtsSensor extends Tile {

	public GewichtsSensor(Signalcolor signalcolor, Cabletype cabletype) {
		super(Height.FLOORHEIGHT, 0, 0, signalcolor, cabletype);
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		onSignalRelayer(this, true);
	}

	@Override
	public void onEntityLeft(Entity entity) {
		onSignalRelayer(this, false);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor, Animation.noanimation, this, "Default");
		World.load(Rotation.norotation, Animation.deactivatedanimation, this);
		World.load(Rotation.norotation, Animation.activatedanimation, this);
	}

	@Override
	public void getdata(LinkedHashMap<String, String> List) {

	}

}
