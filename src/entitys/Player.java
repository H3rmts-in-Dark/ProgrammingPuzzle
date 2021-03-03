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

	public Player(Point position, Rotation rotation) {
		super(position, 0, 0, rotation, 2);
	}

	@Override
	public void loadAnimations() {
		World.load(Rotation.right, Animation.deactivatedanimation, this);
		World.load(Rotation.left, Animation.deactivatedanimation, this);
		World.load(Rotation.up, Animation.deactivatedanimation, this);
		World.load(Rotation.down, Animation.deactivatedanimation, this);
	}

	@Override
	public void getdata(LinkedHashMap<String, String> List) {

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
			return String.valueOf(false);
		return String.valueOf(true);
	}

	@Override
	public void move(int s) {
		for (int i = 0; i < s; i++)
			if (!Boolean.parseBoolean(getBlockSolid()))
				super.move();
	}

	@Override
	public void changeRotation(String s) {
		Rotation r = Rotation.convert(s);
		if (r != null && r != Rotation.norotation)
			super.setRotation(r);
	}

	@Override
	public void interact() {
		Tile tile = getBlock();
		if (tile != null)
			tile.onInteract(this);
		// else
		// throw new MethodNotFoundExeption(firs,line);
	}

	@Override
	public void turn(String s) {
		if (!(s == "right" || s == "left"))
			return;
		switch (rotation) {
		case down:
			if (s == "right")
				rotation = Rotation.left;
			else
				rotation = Rotation.right;
			break;
		case left:
			if (s == "right")
				rotation = Rotation.up;
			else
				rotation = Rotation.down;
			break;
		case right:
			if (s == "right")
				rotation = Rotation.down;
			else
				rotation = Rotation.up;
			break;
		case up:
			if (s == "right")
				rotation = Rotation.right;
			else
				rotation = Rotation.left;
			break;
		case norotation:
			break;
		}
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

	void turn(String s);

}
