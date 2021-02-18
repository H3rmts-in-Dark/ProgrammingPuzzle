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

public class Förderband extends Tile {

	public Förderband(Rotation rotation, int speed, Signalcolor signalcolor, Cabletype cabletype) {
		super(Height.TRANSPORT, 0, -10, rotation, speed, signalcolor, cabletype);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor, Animation.noanimation, this, "Default");
		World.load(Rotation.left, Animation.deactivatedanimation, this);
		World.load(Rotation.right, Animation.deactivatedanimation, this);
		World.load(Rotation.down, Animation.deactivatedanimation, this);
		World.load(Rotation.up, Animation.deactivatedanimation, this);
		World.load(Rotation.left, Animation.activatedanimation, this);
		World.load(Rotation.right, Animation.activatedanimation, this);
		World.load(Rotation.down, Animation.activatedanimation, this);
		World.load(Rotation.up, Animation.activatedanimation, this);
	}

	@Override
	public void getdata(LinkedHashMap<String, String> List) {
	}

	@Override
	public void onSteppedUpon(Entity entity) {
		if (getActivated())
			entity.startmove(getTicksperimagechange(), getRotation());
	}

	@Override
	public void onSignal(Tile caller, boolean activate) {
		if (getWorld().getEntityAt(this) != null)
			if (activate)
				for (Entity entity : getWorld().getEntitysAt(this)) {
					entity.startmove(getTicksperimagechange(), getRotation());
				}
	}

}
