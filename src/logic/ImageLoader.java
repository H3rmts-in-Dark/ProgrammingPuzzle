package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import world.Entity;
import world.World.Layers;

public class ImageLoader {
	
	public static Entry<Layers, BufferedImage> loadImage(String name,Layers layer) {
		try {
			switch (layer) {
			case Floor:
				return new Entry<Layers, BufferedImage> //ImageIO.read(new File("rsc/floor pictures/" + name + ".png"));
			case Floordecoration:
				return ImageIO.read(new File("rsc/floordecoration pictures/" + name + ".png"));
			case Objects:
				return ImageIO.read(new File("rsc/floor pictures/" + name + ".png"));
			case Effects:
				return ImageIO.read(new File("rsc/effects pictures/" + name + ".png"));
			}
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
}
