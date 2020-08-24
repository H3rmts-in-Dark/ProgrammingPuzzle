package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Imageholder {

	private final ArrayList<File> files;
	private Integer actualfile;
	private File source;

	public Imageholder(File source) {
		files = new ArrayList<File>();
		actualfile = 0;
		this.source = source;
		loadimages();
	}
	
	public BufferedImage getActualImg() {
		return Images.getImage(files.get(actualfile));
	}
	
	public Integer getImageslength() {
		return files.size();
	}

	public void nextImage() {
		if (actualfile < files.size()-1)
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
				files.add(new File(source.getPath() + "/" + i + ".png"));
			}
		} else {
			// single pic
			System.out.println(System.currentTimeMillis() + "b");
			files.add(new File(source.getPath() + ".png"));
			System.out.println(System.currentTimeMillis() + "a");
		}
	}
}
