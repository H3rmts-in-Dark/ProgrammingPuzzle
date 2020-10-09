package tiles;

import abstractclasses.Tile;
import world.Images;
import world.World.Layers;

public class Tonne extends Tile {

	public Tonne() {
		super(Unpassable);
		setLayeranimation(Layers.Floor,Images.loadLayeranimation("Default", Layers.Floor,false));
		addObjektAnimation(Images.loadObjektAnimation("Tonne","default animation"));
		setDescription("Barriere");
		
		triggerdefaultanimation();
	}
}
