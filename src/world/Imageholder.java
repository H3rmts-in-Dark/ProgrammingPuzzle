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
					images.add(ImageIO.read(new File(source.getPath() + i.toString() + ".png")));
				}
			} else {
				// single pic
				images.add(ImageIO.read(new File(source.getPath() + ".png")));
			}
		} catch (IOException e) {
			System.err.println("problem with " + source.getAbsolutePath());
			try {
				// If no other Image is found, load the Missing Image instead
				images.add(ImageIO.read(new File("rsc/UI/Missing.png")));
			} catch (IOException e2) {
				System.out.println("could not even find Missing.png");
			}
		}
	}

	public BufferedImage getActualImg() {
		return images.get(actualimage);
	}

	public Integer getImageslength() {
		return images.size();
	}

	public void nextImage() {
		if (actualimage < images.size())
			actualimage++;
		else
			actualimage = 0;
	}

	public int getCurrentImage() {
		return actualimage;
	}
}
