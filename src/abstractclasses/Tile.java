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
	private HashMap<Layers,HashMap<Animations,String>> pictures = new HashMap<>();

	protected HashMap<Layers,Animations> actualanimations = new HashMap<>();
	protected int actualanimationcounter;
	protected ChangeTileImageTask task;

	private World world;

	protected Tile(Heights height,int relativedrawX,int relativedrawY,Signalcolors signalcolor) {
		this(height,relativedrawX,relativedrawY,Rotations.norotation,DEFAULTIMAGECHANGETICKDELAY,signalcolor);
	}

	protected Tile(Heights height,int relativedrawX,int relativedrawY,Rotations rotation,int ticksperimagechange,
			Signalcolors signalcolor) {
		this.height = height;
		this.relativedrawX = relativedrawX;
		this.relativedrawY = relativedrawY;
		this.rotation = rotation;
		this.ticksperimagechange = ticksperimagechange;
		this.color = signalcolor;
		loadAnimations();

		triggerAnimation(Animations.deactivatedanimation);
		triggerAnimation(Animations.offanimation);
		triggerAnimation(Animations.noanimation);
	}

	public abstract void loadAnimations();

	public abstract void getdata(LinkedHashMap<String,String> List);

	public Boolean isPassable(Heights height) {
		return Heights.getint(height) >= Heights.getint(this.height);
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
			return Images.getImage(pictures.get(Layers.Floor).get(actualanimations.get(Layers.Floor)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			return null;
		}
	}

	public BufferedImage getCableImage() {
		try {
			return Images.getImage(pictures.get(Layers.Cable).get(actualanimations.get(Layers.Cable)));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public BufferedImage getObjektImage() {
		try {
			return Images.getImage(
					animations.get(rotation).get(actualanimations.get(Layers.Objects)).get(actualanimationcounter));
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		}
		return null;
	}

	public BufferedImage getEffectsImage() {
		try {
			return Images.getImage(pictures.get(Layers.Effects).get(actualanimations.get(Layers.Effects)));
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

	public HashMap<Layers,HashMap<Animations,String>> getPictures() {
		return pictures;
	}

	public HashMap<Rotations,HashMap<Animations,ArrayList<String>>> getAnimations() {
		return animations;
	}

	public Integer getActualanimationcounter() {
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
		if (activate) {
			triggerAnimation(Animations.activatedanimation);
			triggerAnimation(Animations.onanimation);
		} else {
			triggerAnimation(Animations.deactivatedanimation);
			triggerAnimation(Animations.offanimation);
		}
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

	public void nextimage() {
		actualanimationcounter++;
	}

	public void triggerAnimation(Animations animation) {
		actualanimations.put(Animations.getLayer(animation),animation);
		if (Animations.getLayer(animation) == Layers.Objects) {
			if (!(animations.containsKey(rotation) && animations.get(rotation).containsKey(animation)))
				return;
			actualanimationcounter = 0;
			try {
				task.end();
			} catch (NullPointerException e) {
			}
			task = new ChangeTileImageTask(ticksperimagechange,this,animations.get(rotation).get(animation).size() - 1,
					animation);
		}
	}

}
