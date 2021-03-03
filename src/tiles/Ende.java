package tiles;

import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Height;
import Enums.Layer;
import Enums.Rotation;
import abstractclasses.Entity;
import abstractclasses.Tile;
import world.World;

public class Ende extends Tile {

	public Ende() {
		super(Height.FLOORHEIGHT, 0, 0, null, null);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor, Animation.noanimation, this, "Default");
		World.load(Rotation.norotation, Animation.deactivatedanimation, this);
	}

	@Override
	public void getdata(LinkedHashMap<String, String> List) {
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		System.out.println("Finished Level");
	}
}
