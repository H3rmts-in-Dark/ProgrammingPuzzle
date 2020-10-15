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

public class Images implements Constants {

	static HashMap<File, BufferedImage> allimages = new HashMap<>();

	private Images() {
	}

	public static String loadLayeranimation(String ObjektName, Layers layer) {
		switch (layer) {
		case Floor:
			return "rsc/floor pictures/" + ObjektName + ".png";
		case Cable:
			return "rsc/floordecoration pictures/" + ObjektName + ".png";
		case Effects:
			return "rsc/effects pictures/" + ObjektName + ".png";
		default:
			return "";
		}
	}

	public static Animation loadObjektAnimation(String ObjektName, String animationName, Object animatedObject) {
		Animation animation = null;
		animation = new Animation(new File("rsc/objekt pictures/" + ObjektName + "/" + animationName), animatedObject);
		return animation;
	}

	public static Animation loaddefaultObjektAnimation(String ObjektName, Object animatedObject) {
		Animation animation = null;
		animation = new Animation(new File("rsc/objekt pictures/" + ObjektName + "/default animation"), animatedObject);
		return animation;
	}

	public static Animation loadEntityAnimation(String entityName, String animationName, Object animatedObject) {
		Animation animation = null;
		animation = new Animation(new File("rsc/entity pictures/" + entityName + "/" + animationName), animatedObject);
		return animation;
	}

	public static Animation loaddefaultEntityAnimation(String entityName, Object animatedObject) {
		Animation animation = null;
		animation = new Animation(new File("rsc/entity pictures/" + entityName + "/default animation"), animatedObject);
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
		BufferedImage im = new BufferedImage(defaulttilewidth, defaulttilewidth, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2 = (Graphics2D) im.getGraphics();
		g2.setColor(Color.PINK);
		g2.fillRect(0, 0, im.getWidth() / 2, im.getHeight() / 2);
		g2.fillRect(im.getWidth() / 2, im.getHeight() / 2, im.getWidth() / 2, im.getHeight() / 2);
		g2.setColor(Color.GREEN);
		g2.fillRect(im.getWidth() / 2, 0, im.getWidth() / 2, im.getHeight() / 2);
		g2.fillRect(0, im.getHeight() / 2, im.getWidth() / 2, im.getHeight() / 2);
		g2.setColor(Color.BLUE);
		g2.drawRect(0,0, im.getWidth(), im.getHeight());
		g2.dispose();
		return im;
	}
	
}
