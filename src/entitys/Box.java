package entitys;


import java.awt.Point;

import abstractclasses.Entity;
import logic.Animations;
import logic.Rotations;
import world.World;


public class Box extends Entity {

	public Box(int x,int y,Rotations rotation) {
		super(true,new Point(x,y),8,15,rotation);
		setDescription("geudrsgzhiuedhgt");
	}

	@Override
	public void onInteract(Entity entity) {
		getWorld().removeEntity(this);
	}

	@Override
	public void loadAnimation() {
		World.loadAnimation(Rotations.norotation,Animations.defaultanimation,this);
	}

}
