package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import world.World.Layers;

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
	
	public static Map<Layers,Imageholder> loadImage(String name, Layers layer) {
		Map<Layers,Imageholder> temp = new HashMap<Layers,Imageholder>();
		Imageholder imageholder = null;
		switch (layer) {
		case Floor:
			imageholder = new Imageholder(new File("rsc/floor pictures/" + name));
			break;
		case Floordecoration:
			imageholder = new Imageholder(new File("rsc/floordecoration pictures/" + name));
			break;
		case Objects:
			imageholder = new Imageholder(new File("rsc/objekt pictures/" + name));
			break;
		case Effects:
			imageholder = new Imageholder(new File("rsc/effects pictures/" + name));
			break;
		}
		temp.put(layer, imageholder);

		return temp;
	}

	
	public static void print() {
		System.out.println(allimages.size());
		for (Entry<File, BufferedImage> entry : allimages.entrySet()) {
			System.out.println(entry.toString());
		}
	}
}
