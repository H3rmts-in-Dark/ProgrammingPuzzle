package world;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import logic.Constants;


public class Images implements Constants {

	private static HashMap<File,BufferedImage> allimages = new HashMap<>();

	private Images() {
	}

	public static BufferedImage getImage(String path) {
		if (path == null) {
			return getmissingImage();
		}
		if (allimages.get(new File(path)) != null)
			return allimages.get(new File(path));
		BufferedImage newimage = null;
		try {
			newimage = ImageIO.read(new File(path));
			allimages.put(new File(path),newimage);
		} catch (IOException e) {
			System.out.println("err:" + path);
			return getmissingImage();
		}
		return newimage;
	}

	private static BufferedImage getmissingImage() {
		BufferedImage im = new BufferedImage(TILEHEIGHTWIDHT,TILEHEIGHTWIDHT,BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2 = (Graphics2D) im.getGraphics();
		g2.setColor(Color.PINK);
		g2.fillRect(0,0,im.getWidth() / 2,im.getHeight() / 2);
		g2.fillRect(im.getWidth() / 2,im.getHeight() / 2,im.getWidth() / 2,im.getHeight() / 2);
		g2.setColor(Color.GREEN);
		g2.fillRect(im.getWidth() / 2,0,im.getWidth() / 2,im.getHeight() / 2);
		g2.fillRect(0,im.getHeight() / 2,im.getWidth() / 2,im.getHeight() / 2);
		g2.setColor(Color.BLUE);
		g2.drawRect(0,0,im.getWidth(),im.getHeight());
		g2.dispose();
		return im;
	}

	public static BufferedImage bufferedImage(Image img) {
		BufferedImage im = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = im.createGraphics();
		g.drawImage(img,0,0,null);
		g.dispose();
		return im;

	}

}
