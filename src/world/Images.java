package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import world.World.Layers;

public class Images {
	
	static HashMap<File,BufferedImage> allimages = new HashMap<>();

	private Images() {}
	
	public static BufferedImage getImage(String path) {
		if (allimages.get(new File(path)) != null)
			return allimages.get(new File(path));
		BufferedImage newimage = null;
		try {newimage = ImageIO.read(new File(path));
			allimages.put(new File(path),newimage);
		} catch (IOException e) {}
		return newimage;
	}
	
	public static Map<Layers,Imageholder> loadImage(String name, Layers layer) {
		if(name.equals(""))
			return null;
		Map<Layers,Imageholder> temp = new HashMap<>();
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
		case Entitys:
			imageholder = new Imageholder(new File("rsc/entity pictures/" + name));
			break;
		}
		temp.put(layer, imageholder);

		return temp;
	}
}
