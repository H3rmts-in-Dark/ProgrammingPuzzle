package abstractclasses;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Enums.Animations;
import Enums.Heights;
import Enums.Rotations;
import Enums.Signalcolors;
import logic.Constants;
import logic.Layers;
import tasks.ChangeTileImageTask;
import world.Images;
import world.World;


/**
 * Die Grundklasse aller Tiles. Um als Tile klassifiziert zu werden, darf das Objekt sich
 * nicht bewegen können und muss auf einer World platziert werden.
 */
public abstract class Tile implements Constants {

	private Heights height;
	private int relativedrawX;
	private int relativedrawY;

	private Point position;
	private Rotations rotation;

	private Signalcolors color;
	private boolean activated;

	private int ticksperimagechange;

	private HashMap<Rotations,HashMap<Animations,ArrayList<String>>> animations = new HashMap<>();
	private HashMap<Layers,HashMap<Animations,ArrayList<String>>> pictures = new HashMap<>();

	protected HashMap<Layers,Animations> actualanimation = new HashMap<>();
	protected HashMap<Layers,Integer> actualanimationcounter = new HashMap<>();
	protected HashMap<Layers,ChangeTileImageTask> tasks = new HashMap<>();

	private World world;

	protected Tile(Heights height) {
		this(height,false,0,0,Signalcolors.nocolor);
	}

	protected Tile(Heights height,Boolean animated,int relativedrawX,int relativedrawY,Signalcolors signalcolor) {
		this(height,animated,relativedrawX,relativedrawY,Rotations.norotation,DEFAULTIMAGECHANGETICKDELAY,signalcolor);
	}

	protected Tile(Heights height,Boolean animated,int relativedrawX,int relativedrawY,Rotations rotation,
			int ticksperimagechange,Signalcolors signalcolor) {
		this.height = height;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;
		this.ticksperimagechange = ticksperimagechange;
		this.color = signalcolor;
		loadAnimations();
		if (animated) {
			triggerAnimation(Animations.deactivatedanimation);
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

	public void setWorld(World world) {
		this.world = world;
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
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			return null;
		}
	}

	public BufferedImage getCableImage() {
		try {
			return Images.getImage(pictures.get(Layers.Cable).get(actualanimation.get(Layers.Cable))
					.get(actualanimationcounter.get(Layers.Cable)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public BufferedImage getObjektImage() {
		try {
			return Images.getImage(animations.get(rotation).get(actualanimation.get(Layers.Objects))
					.get(actualanimationcounter.get(Layers.Objects)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public BufferedImage getEffectsImage() {
		try {
			return Images.getImage(pictures.get(Layers.Effects).get(actualanimation.get(Layers.Effects))
					.get(actualanimationcounter.get(Layers.Effects)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public Rotations getRotation() {
		return rotation;
	}

	public Heights getHeight() {
		return height;
	}

	public Signalcolors getColor() {
		return color;
	}

	public HashMap<Layers,HashMap<Animations,ArrayList<String>>> getPictures() {
		return pictures;
	}

	public HashMap<Rotations,HashMap<Animations,ArrayList<String>>> getAnimations() {
		return animations;
	}

	public HashMap<Layers,Integer> getActualanimationcounter() {
		return actualanimationcounter;
	}

	public World getWorld() {
		return world;
	}

	public int getTicksperimagechange() {
		return ticksperimagechange;
	}

	public void onInteract(Entity entity) {
	}

	public void onSteppedUpon(Entity entity) {
	}

	public void onEntityLeft(Entity entity) {
	}

	public void onSignal(Tile caller,boolean activate) {
	}

	public void onSignalRelayer(Tile caller,boolean activate) {
		if (activate == activated)
			return;
		activated = activate;
		if (activate)
			this.triggerAnimation(Animations.activatedanimation);
		else
			this.triggerAnimation(Animations.deactivatedanimation);
		if (color != Signalcolors.nocolor) {
			if (getPosition().x > 0)
				if (world.getTile(getPosition().x - 1,getPosition().y).getColor() == color)
					world.getTile(getPosition().x - 1,getPosition().y).onSignalRelayer(this,activate);
			if (getPosition().y > 0)
				if (world.getTile(getPosition().x,getPosition().y - 1).getColor() == color)
					world.getTile(getPosition().x,getPosition().y - 1).onSignalRelayer(this,activate);
			if (getPosition().x < world.getWidth() - 1)
				if (world.getTile(getPosition().x + 1,getPosition().y).getColor() == color)
					world.getTile(getPosition().x + 1,getPosition().y).onSignalRelayer(this,activate);
			if (getPosition().y < world.getHeight() - 1)
				if (world.getTile(getPosition().x,getPosition().y + 1).getColor() == color)
					world.getTile(getPosition().x,getPosition().y + 1).onSignalRelayer(this,activate);
		}
		onSignal(caller,activate);
	}

	public boolean getActivated() {
		return activated;
	}

	public void nextimage(Layers layer) {
		getActualanimationcounter().replace(layer,getActualanimationcounter().get(layer) + 1);
	}

	public void triggerAnimation(Animations animation) {
		Layers layer = Animations.getLayer(animation);
		actualanimation.put(layer,animation);
		try {
			tasks.get(layer).end();
		} catch (NullPointerException e) {
		}
		actualanimationcounter.put(layer,0);
		if (layer == Layers.Objects) {
			int size = getAnimations().get(rotation).get(animation).size() - 1;
			if (size > 2)
				tasks.put(layer,new ChangeTileImageTask(ticksperimagechange,this,size,animation));
		} else {
			int size = getPictures().get(layer).get(animation).size() - 1;
			if (size > 2)
				tasks.put(layer,new ChangeTileImageTask(ticksperimagechange,this,size,animation));
		}
	}

	public void delete() {
		for (Task task : tasks.values()) {
			task.end();
		}
	}

}
