package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import world.World.Layers;

public class ImageLoader {
	
	public static Map<Layers, BufferedImage> loadImage(String name,Layers layer) {
		Map<Layers, BufferedImage> temp = new HashMap<Layers,BufferedImage>();
		try {
			switch (layer) {
			case Floor:
				temp.put(layer,ImageIO.read(new File("rsc/floor pictures/" + name + ".png")));
				break;
			case Floordecoration:
				temp.put(layer,ImageIO.read(new File("rsc/floordecoration pictures/" + name + ".png")));
				break;
			case Objects:
				temp.put(layer,ImageIO.read(new File("rsc/objekt pictures/" + name + ".png")));
				break;
			case Effects:
				temp.put(layer,ImageIO.read(new File("rsc/effects pictures/" + name + ".png")));
				break;
			}
		} catch (IOException e) {e.printStackTrace();}
		return temp;
	}
	
}
