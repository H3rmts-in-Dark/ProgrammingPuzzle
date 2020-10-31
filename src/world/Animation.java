package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Tile;
import frame.Frame;
import logic.Constants;
import logic.Rotation;
import sound.Sound;
import sound.Sounds;
import tasks.ChangeImageTask;

public class Animation implements Constants {

	private ArrayList<String> paths;
	private Integer actualFile;
	private Object animatedObject;
	private ChangeImageTask task;
	private String sound;
	private Boolean defaultanimtion;

	public Animation(String picturesFile, String soundFile, Object animatedObject, Boolean defaultanimtion) {
		this.paths = new ArrayList<>();
		this.actualFile = 0;
		this.animatedObject = animatedObject;
		this.defaultanimtion = defaultanimtion;

		for (String filePath : new File(picturesFile).list()) {
			if (filePath.contains(".png"))
				paths.add(picturesFile + "/" + filePath);
			else if (filePath.contains(".wav"))
				sound = picturesFile + "/" + filePath;
		}
	}

	public BufferedImage getActualImage() {
		return Images.getImage(paths.get(actualFile));
	}

	public Sound getSound() {
		return Sounds.getSound(sound);
	}

	public void startAnimation() {
		actualFile = 0;
		if (defaultanimtion)
			task = new ChangeImageTask(5, this, -1);
		else
			task = new ChangeImageTask(5, this, paths.size());
		if (sound != null)
			Sounds.getSound(sound).play();
	}

	public void stopAnimation() {
		task.end();
	}

	/**
	 * 
	 * @return true if animation finished
	 */
	public void nextImage() {
		if (actualFile < paths.size() - 1) {
			actualFile++;
			if (animatedObject instanceof Tile) {
				((Tile) animatedObject).getWorld().getWindow().triggerFullRepaint();
			} else if (animatedObject instanceof Entity) {
				((Entity) animatedObject).getWorld().getWindow().triggerFullRepaint();
			}
			Frame.repaint();
		} else if (defaultanimtion) {
			actualFile = 0;
		}
	}

	public void triggerDefault() {
		if (animatedObject instanceof Tile) {
			((Tile) animatedObject).triggerAnimation(DEFAULTANIMATION);
		} else if (animatedObject instanceof Entity) {
			((Entity) animatedObject).triggerAnimation(DEFAULTANIMATION);
		}
	}

<<<<<<< HEAD
	
	public static SimpleEntry<String, Animation> loadObjektAnimation(String ObjektName, String animationName,
			Tile animatedObject) {
		return new SimpleEntry<>(animationName,
				new Animation("rsc/objekt pictures/" + ObjektName + "/" + animationName,"rsc/sound/" + ObjektName + "/" + animationName, animatedObject,animationName == DEFAULTANIMATION));
=======
	public static SimpleEntry<String, Animation> loadObjektAnimation(String ObjektName, Rotation direction,
			String animationName, Tile animatedObject) {
		return new SimpleEntry<>(animationName,
				new Animation("rsc/objekt pictures/" + ObjektName + "/" + decodeRotation(direction) + animationName,
						"rsc/sound/" + ObjektName + "/" + animationName, animatedObject,
						animationName == DEFAULTANIMATION));
>>>>>>> ff2ac1be46ff8b2f82277a5636b4ecb4f44f2402
	}

	private static String decodeRotation(Rotation r) {
		try {
			switch (r) {
			case down:
				return "unten/";
			case left:
				return "links/";
			case right:
				return "rechts/";
			case up:
				return "unten/";
			default:
				return "";
			}
		} catch (NullPointerException npe) {
			return "";
		}
	}

	public static SimpleEntry<String, Animation> loadEntityAnimation(String ObjektName, String animationName,
			Entity animatedObject) {
<<<<<<< HEAD
		return new SimpleEntry<>(animationName,
				new Animation("rsc/entity pictures/" + ObjektName + "/" + animationName,"rsc/sound/" + ObjektName + "/" + animationName, animatedObject,animationName == DEFAULTANIMATION));
=======
		return new AbstractMap.SimpleEntry<>(animationName,
				new Animation("rsc/entity pictures/" + ObjektName + "/" + animationName,
						"rsc/sound/" + ObjektName + "/" + animationName, animatedObject,
						animationName == DEFAULTANIMATION));
>>>>>>> ff2ac1be46ff8b2f82277a5636b4ecb4f44f2402
	}
}
