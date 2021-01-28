package tiles;


import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import abstractclasses.Tile;
import logic.Layers;
import world.World;


public class Default extends Tile {

	public Default() {
		super(Heights.FLOORHEIGHT);
	}

	@Override
	public void loadAnimations() {
		World.loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
	}

	@Override
	public void getdata(LinkedHashMap<String,String> list) {
		list.put("Hehe","LLuuul");
	}

}
