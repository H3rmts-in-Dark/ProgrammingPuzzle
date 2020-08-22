package logic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import world.World.Layers;

public class ImageLoader {

	public static Map<Layers, world.Imageholder> loadImage(String name, Layers layer) {
		Map<Layers, world.Imageholder> temp = new HashMap<Layers, world.Imageholder>();
		world.Imageholder imageholder = null;
		switch (layer) {
		case Floor:
			imageholder = new world.Imageholder(new File("rsc/floor pictures/" + name));
			break;
		case Floordecoration:
			imageholder = new world.Imageholder(new File("rsc/floordecoration pictures/" + name));
			break;
		case Objects:
			imageholder = new world.Imageholder(new File("rsc/objekt pictures/" + name));
			break;
		case Effects:
			imageholder = new world.Imageholder(new File("rsc/effects pictures/" + name));
			break;
		}
		temp.put(layer, imageholder);
		return temp;
	}

}
