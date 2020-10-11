package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import logic.Constants;
import world.World.Layers;

public class Images implements Constants{

	static HashMap<File, BufferedImage> allimages = new HashMap<>();

	private Images() {
	}
	
	
	public static Animation loadLayeranimation(String ObjektName,Layers layer) {
		Animation animation = null;
		switch (layer) {
		case Floor:
			animation = new Animation(new File("rsc/floor pictures/" + ObjektName));
			break;
		case Cable:
			animation = new Animation(new File("rsc/floordecoration pictures/" + ObjektName));
			break;
		case Effects:
			animation = new Animation(new File("rsc/effects pictures/" + ObjektName));
			break;
		default:
			break;
		}
		return animation;
	}

	public static Animation loadObjektAnimation(String ObjektName,String animationName) {
		Animation animation = null;
		animation = new Animation(new File("rsc/objekt pictures/" + ObjektName + "/" + animationName));
		return animation;
	}
	
	public static Animation loaddefaultObjektAnimation(String ObjektName) {
		Animation animation = null;
		animation = new Animation(new File("rsc/objekt pictures/" + ObjektName + "/default animation"));
		return animation;
	}
	
	public static Animation loadEntityAnimation(String entityName,String animationName) {
		Animation animation = null;
		animation = new Animation(new File("rsc/entity pictures/" + entityName + "/" + animationName));
		return animation;
	}
	
	public static Animation loaddefaultEntityAnimation(String entityName) {
		Animation animation = null;
		animation = new Animation(new File("rsc/entity pictures/" + entityName + "/default animation"));
		return animation;
	}
	
	
	public static BufferedImage getImage(String path) {
		if (allimages.get(new File(path)) != null)
			return allimages.get(new File(path));
		BufferedImage newimage = null;
		try {
			newimage = ImageIO.read(new File(path));
			allimages.put(new File(path), newimage);
		} catch (IOException e) {
			return getmissingImage();
		}
		return newimage;
	}

	private static BufferedImage getmissingImage() {
		BufferedImage im = new BufferedImage(defaulttilewidth,defaulttilewidth,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2 = (Graphics2D) im.getGraphics();
		g2.setColor(Color.PINK);
		g2.fillRect(0,0,im.getWidth()/2,im.getHeight()/2);
		g2.fillRect(im.getWidth()/2,im.getHeight()/2,im.getWidth()/2,im.getHeight()/2);
		g2.setColor(Color.GREEN);
		g2.fillRect(im.getWidth()/2,0,im.getWidth()/2,im.getHeight()/2);
		g2.fillRect(0,im.getHeight()/2,im.getWidth()/2,im.getHeight()/2);
		g2.dispose();
		return im;
	}
}
