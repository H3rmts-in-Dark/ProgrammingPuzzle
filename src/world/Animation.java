package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import abstractclasses.Tile;
import tasks.ChangeImageTask;
import world.World.Layers;

public class Animation {

	private ArrayList<String> paths;
	private Integer actualfile;
	private File source;
	
	private Boolean loop;
	
	private ChangeImageTask task;
	private Boolean pause;

	public Animation(File source,Boolean loop) {
		paths = new ArrayList<>();
		actualfile = 0;
		this.source = source;
		this.loop = loop;
		pause = false;
		loadimages();
		
	}

	public BufferedImage getActualImg() {
		return Images.getImage(paths.get(actualfile));
	}
	
	public Animation resume(Tile tile) {
		if (task == null)
			task = new ChangeImageTask(10,tile,Layers.Objects,loop ? -1 : paths.size());
		pause = false;
		actualfile = 0;
		return this;
	}
	
	public Animation continu() {
		
		return this;
	}

	public void nextImage() {
		if (pause)
			return;
		if (actualfile < paths.size() - 1)
			actualfile++;
		else
			testend();
	}
	
	private void testend() {
		if (loop)
			actualfile = 0;
		else 
			task.getTile().triggerdefaultanimation();
	}

	public void pause() {
		pause = true;
	}

	public Integer getCurrentFile() {
		return actualfile;
	}
	
	public Integer getSize() {
		return paths.size();
	}

	private void loadimages() {
		if (source.exists()) {
			// directory with many pics (named 1.png ...)
			for (Integer i = 0; i < source.listFiles().length; i++) {
				paths.add(source.getPath() + "/" + i + ".png");
			}
		} else {
			// single pic
			paths.add(source.getPath() + ".png");
		}
	}
}
