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

public class Computer extends Tile {

	public Computer(Signalcolor signalcolor, Cabletype cabletype) {
		super(Height.UNPASSABLE, -5, -20, signalcolor, cabletype);
	}

	@Override
	public void loadAnimations() {
		World.load(Layer.Floor, Animation.noanimation, this, "Default");
		World.load(Rotation.norotation, Animation.deactivatedanimation, this);
		World.load(Rotation.norotation, Animation.activatedanimation, this);
		World.load(Rotation.norotation, Animation.interactanimation, this);
	}

	@Override
	public void onInteract(Entity entity) {
		triggerAnimation(Animation.interactanimation);
	}

	@Override
	public void getdata(LinkedHashMap<String, String> list) {
	}

}
