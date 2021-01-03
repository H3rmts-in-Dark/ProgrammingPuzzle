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

}
