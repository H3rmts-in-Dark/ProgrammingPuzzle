package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import abstractclasses.Tile;

public class Animation {

	private ArrayList<String> paths;
	private Integer actualfile;
	private File source;
	private Tile tile;

	public Animation(File source) {
		this.paths = new ArrayList<>();
		this.actualfile = 0;
		this.source = source;
		loadimages();
	}

	public BufferedImage getActualImg() {
		return Images.getImage(paths.get(actualfile));
	}

	public void nextImage() {
		if (actualfile < paths.size() - 1) {
			actualfile++;
			tile.getWorld().getWindow().setrepaintfull();
		}
		else
			tile.triggerdefaultanimation();
	}
	
	public Integer getSize() {
		return paths.size();
	}
	
	public Animation start(Tile tile) {
		this.tile = tile;
		return this;
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
