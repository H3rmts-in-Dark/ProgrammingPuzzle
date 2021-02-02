package entitys;


import java.awt.Point;
import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Rotations;
import abstractclasses.Entity;
import world.World;


public class Box extends Entity {

	public Box(int x,int y) {
		super(new Point(x,y),8,12);
	}

	@Override
	public void loadAnimations() {
		World.loadAnimation(Rotations.norotation,Animations.deactivatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
		List.put("lul","I'm box");
	}

}
