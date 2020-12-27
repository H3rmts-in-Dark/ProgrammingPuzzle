package tiles;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import abstractclasses.Tile;
import logic.Animations;
import logic.Layers;
import logic.Rotations;
import world.Images;


public class Tonne extends Tile {

	public Tonne() {
		super(UNPASSABLE,ANIMATED,10,20,Rotations.norotation);
		setDescription("Barriere");
	}

	@Override
	public void loadAnimations() {
		loadPicture(Layers.Floor,Animations.noanimation,this,"Default");
		loadAnimation(Rotations.norotation,Animations.defaultanimation,this,true);
	}

	@Override
	public void updateimage() {
		BufferedImage image = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT,DEFAULTIMAGEWIDHTHEIGHT,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = image.getGraphics();
		g.drawImage(Images.getImage(pictures.get(Layers.Floor).get(Animations.noanimation).get(0)),0,0,null);

		g.drawImage(Images.getImage(animations.get(Rotations.norotation).get(actualanimation.get(Layers.Objects)).get(0)),
				0,0,null);

		drawimage = image.getScaledInstance((int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),
				(int) (TILEHEIGHTWIDHT * world.getWindow().getZoom()),Scaler);
	}

}
