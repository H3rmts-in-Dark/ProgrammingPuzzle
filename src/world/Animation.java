package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Tile;
import tasks.ChangeImageTask;

public class Animation {

	private ArrayList<String> paths;
	private Integer actualFile;
	private File source;
	private Object animatedObject;

	public Animation(File source, Object animatedObject) {
		this.paths = new ArrayList<>();
		this.actualFile = 0;
		this.source = source;
		this.animatedObject = animatedObject;
		loadimages();
	}

	public BufferedImage getActualImage() {
		return Images.getImage(paths.get(actualFile));
	}

	public void start() {
		new ChangeImageTask(5, this, paths.size());
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
		}
	}

	public ArrayList<String> getPaths() {
		return paths;
	}

	public void triggerdefault() {
		if (animatedObject instanceof Tile) {
			((Tile) animatedObject).triggerDefaultAnimation();
			// System.out.println("schould triggert default" + toString());
		} else if (animatedObject instanceof Entity) {
			((Entity) animatedObject).triggerDefaultAnimation();
			// System.out.println("schould triggert default" + toString());
		}
	}

	private void loadimages() {
		for (Integer i = 0; i < source.listFiles().length; i++) {
			paths.add(source.getPath() + "/" + i + ".png");
		}
	}
}
