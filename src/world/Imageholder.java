package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Imageholder {

	final ArrayList<BufferedImage> images;
	Integer actualimage;

	public Imageholder(File source) {
		images = new ArrayList<BufferedImage>();
		actualimage = 0;
		try {
			if (source.exists()) {
				// directory with many pics (named 1.png ...)
				for (Integer i = 0; i < source.listFiles().length; i++) {
					images.add(ImageIO.read(new File(source.getPath() + "/" + i + ".png")));
				}
			} else {
				// single pic
				images.add(ImageIO.read(new File(source.getPath() + ".png")));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("problem with " + source.getAbsolutePath() + "  exists:" + source.exists());

			// load missing image instead
			try {
				images.add(ImageIO.read(new File("rsc/UI/Missing.png")));
			} catch (IOException e2) {
				System.out.println("could not find missing");
			}
		}
	}

	public BufferedImage getActualImg() {
		try {
			return images.get(actualimage);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ioobe");
			System.err.println(images.size() + "-" + actualimage);
		}
		return images.get(0);
	}

	public Integer getImageslength() {
		return images.size();
	}

	public void nextImage() {
		if (actualimage < images.size()-1)
			actualimage++;
		else
			actualimage = 0;
	}

	public int getCurrentImage() {
		return actualimage;
	}
}
