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
		
		for (String filePath : new File(picturesFile).list()) 
			paths.add(picturesFile + "/" + filePath);
		
		if (new File(soundFile + ".wav").exists())
			sound = soundFile + ".wav";
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
			
		else
			task = new ChangeImageTask(5, this, paths.size());
		if (sound != null)
			Sounds.getSound(sound).play();
	}
	
	public void stopAnimation() {
		task.end();
	}
	
	public void redrawimage() {
		if (animatedObject instanceof Tile) {
			((Tile) animatedObject).updateimage();
		} else if (animatedObject instanceof Entity) {
			//((Entity) animatedObject).updateimage();
		}
	}

	/**
	 * 
	 * @return true if animation finished
	 */
	public void nextImage() {
		if (actualFile < paths.size() - 1) {
			actualFile++;
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

	
}
