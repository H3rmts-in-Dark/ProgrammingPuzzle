package tiles;

<<<<<<< HEAD
import world.Entity;
import world.Images;
import world.Tile;
=======
import world.*;
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
import world.World.Layers;

public class Empty extends Tile {

	public Empty(Integer x, Integer y) {
		super(true, false);
		setPassable(true);
		setDescription("");  //no description should be shown if clicked on tile
		addImage(Images.loadImage("Missing", Layers.Floor));
	}

	@Override
	public void onInteract(Entity entity) {
<<<<<<< HEAD
		
=======
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
	}

	@Override
	public void onSteppedUpon(Entity entity) {
<<<<<<< HEAD
		
=======
>>>>>>> 34f27f43ef66ee073176b88dfc3a5b60c5bad254
	}
}
