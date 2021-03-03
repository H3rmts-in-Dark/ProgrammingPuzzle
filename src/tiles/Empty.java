package tiles;

import java.util.LinkedHashMap;

import Enums.Cabletype;
import Enums.Height;
import Enums.Signalcolor;
import abstractclasses.Tile;

public class Empty extends Tile {

	public Empty() {
		super(Height.UNPASSABLE, 0, 0, Signalcolor.nocolor, Cabletype.notype);
	}

	@Override
	public void loadAnimations() {
	}

	@Override
	public void getdata(LinkedHashMap<String, String> List) {
		List.clear();
	}

}
