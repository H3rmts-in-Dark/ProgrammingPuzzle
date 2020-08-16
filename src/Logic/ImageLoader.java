package Logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage loadImage(String name) {
		try {return ImageIO.read(new File("Pictures/" + name + ".png"));
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}
	
}
