package entitys;


import java.awt.Point;
import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Rotation;
import abstractclasses.Entity;
import world.World;


public class Box extends Entity {

	public Box(int x,int y) {
		super(new Point(x,y),8,12);
	}

	@Override
	public void loadAnimations() {
		World.load(Rotation.norotation,Animation.deactivatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {
		List.put("lul","I'm box");
	}

}
