package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class Images {
	
	static HashMap<File,BufferedImage> allimages = new HashMap<File,BufferedImage>();

	private Images() {}
	
	public static BufferedImage getImage(File file) {
		if (allimages.get(file) != null)
			return allimages.get(file);
		BufferedImage newim = null;
		try {newim = ImageIO.read(file);
		} catch (IOException e) {}
		allimages.put(file,newim);
		return newim;
	}
	
	public static void print() {
		System.out.println(allimages.size());
		for (Entry<File, BufferedImage> entry : allimages.entrySet()) {
			System.out.println(entry.toString());
		}
	}
}
