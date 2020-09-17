package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Imageholder {

	private final ArrayList<String> pahts;
	private Integer actualfile;
	private File source;

	public Imageholder(File source) {
		pahts = new ArrayList<String>();
		actualfile = 0;
		this.source = source;
		loadimages();
	}
	
	public BufferedImage getActualImg() {
		return Images.getImage(pahts.get(actualfile));
	}
	
	public Integer getImageslength() {
		return pahts.size();
	}

	public void nextImage() {
		if (actualfile < pahts.size()-1)
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
				pahts.add(source.getPath() + "/" + i + ".png");
			}
		} else {
			// single pic
			System.out.println(System.currentTimeMillis() + "b");
			pahts.add(source.getPath() + ".png");
			System.out.println(System.currentTimeMillis() + "a");
		}
	}
}
