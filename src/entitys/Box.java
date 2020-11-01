
package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import logic.Rotation;
import world.Animation;

public class Box extends Entity {

	public Box(Integer X, Integer Y, Rotation r) {
		super(true, new Point(X, Y), 8, 15, r);
		setDescription("geudrsgzhiuedhgt");
	}

	@Override
	public void onInteract(Entity entity) {
		getWorld().removeEntity(this);
	}

	@Override
	public void loadAnimation() {
		addAnimation(Animation.loadEntityAnimation("Box", DEFAULTANIMATION, this));
	}

}
