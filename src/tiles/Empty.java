package tiles;

public class Empty extends tiles.Tile {

	public Empty(Integer x, Integer y) {
		super(x, y, true, false); // TODO Path einf�gen
	}

	@Override
	public void onInteract() {
	}

	@Override
	public void onSteppedUpon() {
	}
}
