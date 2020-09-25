package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Imageholder {

	private final ArrayList<String> paths;
	private Integer actualfile;
	private File source;

	public Imageholder(File source) {
		paths = new ArrayList<String>();
		actualfile = 0;
		this.source = source;
		loadimages();
	}

	public BufferedImage getActualImg() {
		return Images.getImage(paths.get(actualfile));
	}

	public Integer getImageslength() {
		return paths.size();
	}

	public void nextImage() {
		if (actualfile < paths.size() - 1)
			actualfile++;
		else
			actualfile = 0;
		
	}

	public int getCurrentImage() {
		return actualfile;
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
