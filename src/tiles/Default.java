package tiles;


import java.awt.image.BufferedImage;

import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;
import world.Images;


public class Default extends Tile {

	public Default() {
		super(FLOORHEIGHT,NOTANIMATED,0,0,Rotations.norotation);
		setDescription("Default Tile (for testing purposes)");
	}

	@Override
	public void loadAnimations() {
		loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
	}

	@Override
	public void updateimage() {
		BufferedImage image = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT,
				BufferedImage.TYPE_4BYTE_ABGR);

		image.getGraphics().drawImage(Images.getImage(pictures.get(Layers.Floor).get(Animations.noanimation).get(0)),0,0,
				null);

		drawimage = image.getScaledInstance((int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),
				(int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),Scaler);
	}

}
