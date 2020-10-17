package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Tile;
import frame.Frame;
import logic.Constants;
import tasks.ChangeImageTask;

public class Animation implements Constants{

	private ArrayList<String> paths;
	private Integer actualFile;
	private Object animatedObject;
	private ChangeImageTask task;

	public Animation(File source, Object animatedObject) {
		this.paths = new ArrayList<>();
		this.actualFile = 0;
		this.animatedObject = animatedObject;
		for (Integer i = 0; i < source.listFiles().length; i++) {
			paths.add(source.getPath() + "/" + i + ".png");
		}
	}

	public BufferedImage getActualImage() {
		return Images.getImage(paths.get(actualFile));
	}

	public void start() {
		actualFile = 0;
		task = new ChangeImageTask(5, this, paths.size());
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
				((Tile) animatedObject).getWorld().getWindow().setrepaintfull();
			} else if (animatedObject instanceof Entity) {
				((Entity) animatedObject).getWorld().getWindow().setrepaintfull();
			}
			Frame.repaint();
		}
	}

	public void triggerdefault() {
		if (animatedObject instanceof Tile) {
			((Tile) animatedObject).triggerAnimation(DEFAULTANIMATION);
		} else if (animatedObject instanceof Entity) {
			((Entity) animatedObject).triggerDefaultAnimation();
		}
	}
}
