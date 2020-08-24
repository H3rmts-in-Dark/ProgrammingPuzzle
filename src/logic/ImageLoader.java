package logic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import world.Imageholder;
import world.World.Layers;

public class ImageLoader {

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

}
