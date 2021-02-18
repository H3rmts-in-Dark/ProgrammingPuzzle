package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import logic.Constants;

public class Images implements Constants {

	private static HashMap<String, BufferedImage> allimages = new HashMap<>();

	private Images() {
	}

	public static BufferedImage getImage(String path) {
		if (allimages.get(path) != null)
			return allimages.get(path);
		try {
			BufferedImage newimage = ImageIO.read(new File(path));
			allimages.put(path, newimage);
			return newimage;
		} catch (IOException e) {
			System.out.println("err:" + path);
			BufferedImage newimage = getmissingImage();
			allimages.put(path, newimage);
			return newimage;
		}
	}

	private static BufferedImage getmissingImage() {
		BufferedImage im = new BufferedImage(DEFAULTIMAGEWIDHTHEIGHT, DEFAULTIMAGEWIDHTHEIGHT,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2 = (Graphics2D) im.getGraphics();
		g2.setColor(Color.PINK);
		for (int i = 0; i < DEFAULTIMAGEWIDHTHEIGHT; i++)
			for (int j = 0; j < DEFAULTIMAGEWIDHTHEIGHT; j++) {
				g2.setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
				g2.drawRect(i, j, 1, 1);
			}
		g2.dispose();
		return im;
	}

	public static BufferedImage bufferedImage(Image img) {
		BufferedImage im = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = im.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return im;

	}

	public static void clear() {
		allimages.clear();
	}

}
