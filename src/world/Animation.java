package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import abstractclasses.Entity;
import abstractclasses.Tile;
import frame.Frame;
import logic.Constants;
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

	public Animation(File picturesFile, File soundFile, Object animatedObject, Boolean defaultanimtion) {
		this.paths = new ArrayList<>();
		this.actualFile = 0;
		this.animatedObject = animatedObject;
		this.defaultanimtion = defaultanimtion;
		for (Integer i = 0; i < picturesFile.listFiles().length; i++) {
			paths.add(picturesFile.getPath() + "/" + i + ".png");
		}
		if (new File(soundFile.getPath() + ".wav").exists())
			sound = soundFile.getPath() + ".wav";
	}

	public BufferedImage getActualImage() {
		return Images.getImage(paths.get(actualFile));
	}

	public Sound name() {
		return Sounds.getSound(sound);
	}

	public void start() {
		actualFile = 0;
		if (defaultanimtion)
			task = new ChangeImageTask(5, this, -1);
		else
			task = new ChangeImageTask(5, this, paths.size());
		if (sound!=null)
		Sounds.getSound(sound).play();
	}

	public void stop() {
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

	public void triggerdefault() {
		if (animatedObject instanceof Tile) {
			((Tile) animatedObject).triggerAnimation(DEFAULTANIMATION);
		} else if (animatedObject instanceof Entity) {
			((Entity) animatedObject).triggerAnimation(DEFAULTANIMATION);
		}
	}
	
	
	

	
	public static SimpleEntry<String, Animation> loadObjektAnimation(String ObjektName, String animationName,
			Tile animatedObject) {
		return new AbstractMap.SimpleEntry<>(animationName,
				new Animation(new File("rsc/objekt pictures/" + ObjektName + "/" + animationName),new File("rsc/sound/" + ObjektName + "/" + animationName), animatedObject,animationName == DEFAULTANIMATION));
	}
	
	public static SimpleEntry<String, Animation> loadEntityAnimation(String ObjektName, String animationName,
			Entity animatedObject) {
		return new AbstractMap.SimpleEntry<>(animationName,
				new Animation(new File("rsc/entity pictures/" + ObjektName + "/" + animationName),new File("rsc/sound/" + ObjektName + "/" + animationName), animatedObject,animationName == DEFAULTANIMATION));
	}
}
