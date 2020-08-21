package tiles;

public class Empty extends world.Tile {

	public Empty(Integer x, Integer y) {
		super(x, y, true, false, ""); // TODO Path einfügen
	}

	@Override
	public void onInteract() {
	}

	@Override
	public void onSteppedUpon() {
	}
}
