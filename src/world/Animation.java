package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Tile;
import tasks.ChangeImageTask;
import world.World.Layers;

public class Animation {

	private ArrayList<String> paths;
	private Integer actualfile;
	private File source;
	private Object animatedObject;

	public Animation(File source, Object animatedObject) {
		this.paths = new ArrayList<>();
		this.actualfile = 0;
		this.source = source;
		this.animatedObject = animatedObject;
		loadimages();
	}

	public BufferedImage getActualImg() {
		return Images.getImage(paths.get(actualfile));
	}
	
	public void start() {
		new ChangeImageTask(5, this, paths.size());
	}

	/**
	 * 
	 * @return true if animation finished
	 */
	public void nextImage() {
		if (actualfile < paths.size() - 1) {
			actualfile++;
			if (animatedObject instanceof Tile) {
				((Tile) animatedObject).getWorld().getWindow().setrepaintfull();
			} else if (animatedObject instanceof Entity) {
				((Entity) animatedObject).getWorld().getWindow().setrepaintfull();
			}
		}
	}
	
	public void triggerdefault() {
		if (animatedObject instanceof Tile) {
			((Tile) animatedObject).triggerdefaultanimation();
			System.out.println("schould triggert default" + toString());
		} else if (animatedObject instanceof Entity) {
			((Entity) animatedObject).triggerdefaultanimation();
			System.out.println("schould triggert default" + toString());
		}
	}

	private void loadimages() {
		if (source.exists()) {
			for (Integer i = 0; i < source.listFiles().length; i++) {
				paths.add(source.getPath() + "/" + i + ".png");
			}
		} else {
			paths.add(source.getPath() + ".png");
		}
	}
}
