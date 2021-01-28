package abstractclasses;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import logic.Constants;
import logic.Layers;
import tasks.ChangeImageTask;
import world.Images;


/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das Objekt sich
 * nicht bewegen können und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private Heights height;
	private int relativedrawX;
	private int relativedrawY;

	private Point position;
	protected Rotations rotation;

	private int ticksperimagechange;

	public HashMap<Rotations,HashMap<Animations,ArrayList<String>>> animations = new HashMap<>();
	public HashMap<Layers,HashMap<Animations,ArrayList<String>>> pictures = new HashMap<>();

	protected HashMap<Layers,Animations> actualanimation = new HashMap<>();
	protected HashMap<Layers,Integer> actualanimationcounter = new HashMap<>();
	protected HashMap<Layers,ChangeImageTask> tasks = new HashMap<>();

	protected Tile(Heights height) {
		this(height,false,0,0);
	}

	protected Tile(Heights height,Boolean animated,int relativedrawX,int relativedrawY) {
		this(height,animated,relativedrawX,relativedrawY,Rotations.norotation,DEFAULTIMAGECHANGETICKDELAY);
	}

	protected Tile(Heights height,Boolean animated,int relativedrawX,int relativedrawY,Rotations rotation,
			int ticksperimagechange) {
		this.height = height;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;
		this.ticksperimagechange = ticksperimagechange;

		loadAnimations();
		if (animated) {
			triggerAnimation(Animations.defaultanimation);
		}
	}

	public abstract void loadAnimations();

	public abstract void getdata(LinkedHashMap<String,String> List);

	public Boolean isPassable(Heights height) {
		return Heights.getheight(height) >= Heights.getheight(this.height);
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public int getRelativedrawX() {
		return relativedrawX;
	}

	public int getRelativedrawY() {
		return relativedrawY;
	}

	public BufferedImage getFloorImage() {
		try {
			return Images.getImage(pictures.get(Layers.Floor).get(Animations.noanimation).get(0));
		} catch (NullPointerException e) {
			return null;
		}
	}

	public BufferedImage getCableImage() {
		try {
			return Images.getImage(pictures.get(Layers.Cable).get(actualanimation.get(Layers.Cable))
					.get(actualanimationcounter.get(Layers.Cable)));
		} catch (NullPointerException e) {
		}
		return null;
	}

	public BufferedImage getObjektImage() {
		try {
			return Images.getImage(animations.get(rotation).get(actualanimation.get(Layers.Objects))
					.get(actualanimationcounter.get(Layers.Objects)));
		} catch (NullPointerException e) {
		}
		return null;
	}

	public BufferedImage getEffectsImage() {
		try {
			return Images.getImage(pictures.get(Layers.Effects).get(actualanimation.get(Layers.Effects))
					.get(actualanimationcounter.get(Layers.Effects)));
		} catch (NullPointerException e) {
		}
		return null;
	}

	public Rotations getRotation() {
		return rotation;
	}

	public Heights getHeight() {
		return height;
	}

	public void onInteract(Entity entity) {
	}

	public void onSteppedUpon(Entity entity) {
	}

	public void nextimage(Layers layer) {
		actualanimationcounter.replace(layer,actualanimationcounter.get(layer) + 1);
	}

	public void triggerAnimation(Animations animation) {
		Layers layer = Animations.getLayer(animation);
		actualanimation.put(layer,animation);
		actualanimationcounter.put(layer,0);
		try {
			tasks.get(layer).end();
		} catch (NullPointerException e) {
		}
		if (layer == Layers.Objects) {
			tasks.put(layer,new ChangeImageTask(ticksperimagechange,this,
					animations.get(rotation).get(animation).size() - 1,Layers.Objects));
		} else {
			tasks.put(layer,
					new ChangeImageTask(ticksperimagechange,this,pictures.get(layer).get(animation).size() - 1,layer));
		}
	}

	public void delete() {
		for (ChangeImageTask task : tasks.values()) {
			task.end();
		}
	}

}
