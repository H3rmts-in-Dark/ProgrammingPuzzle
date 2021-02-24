package entitys;


import java.awt.Point;
import java.util.LinkedHashMap;

import Enums.Animation;
import Enums.Height;
import Enums.Rotation;
import abstractclasses.Entity;
import abstractclasses.Tile;
import world.World;


public class Player extends Entity implements Playercontroll {

	public Player(Point position,Rotation rotation) {
		super(position,0,0,rotation,2);
	}

	@Override
	public void loadAnimations() {
		World.load(Rotation.right,Animation.deactivatedanimation,this);
	}

	@Override
	public void getdata(LinkedHashMap<String,String> List) {

	}

	@Override
	public String getStrRotation() {
		return Rotation.convert(super.getRotation());
	}

	@Override
	public String getX() {
		return String.valueOf(super.getPosition().y);
	}

	@Override
	public String getY() {
		return String.valueOf(super.getPosition().x);
	}

	@Override
	public String getBlockActivated() {
		Tile t = super.getBlock();
		if (t != null && t.getActivated())
			return String.valueOf(true);
		return String.valueOf(false);
	}

	@Override
	public String getBlockSolid() {
		Tile t = super.getBlock();
		if (t != null && (Height.getint(t.getHeight()) <= Height.getint(super.getHeight())))
			return String.valueOf(true);
		return String.valueOf(false);
	}

	@Override
	public void move(int s) {
		for (int i = 0; i < s; i++) {
			super.move();
		}
	}

	@Override
	public void changeRotation(String s) {
		Rotation r = Rotation.convert(s);
		if (r != null && r != Rotation.norotation)
			super.setRotation(r);
	}

	@Override
	public void interact() {
		super.interact();
	}

}



interface Playercontroll {

	String getStrRotation();

	String getX();

	String getY();

	String getBlockActivated();

	String getBlockSolid();

	void move(int s); // blocking

	void changeRotation(String s);

	void interact();

}
