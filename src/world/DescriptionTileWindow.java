package world;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import Enums.Heights;
import abstractclasses.CustomWindow;
import abstractclasses.Tile;


public class DescriptionTileWindow extends CustomWindow {

	private Tile tile;

	public DescriptionTileWindow(Tile tile,Point point) {
		super(200,300,point,"Description of " + tile.getClass().getSimpleName(),0);
		this.tile = tile;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setFont(new Font("Default",Font.BOLD,15));
		g2.setColor(Color.WHITE);
		int y = 2;
		var res = new LinkedHashMap<String,String>();
		res.put("",tile.getClass().getSimpleName());
		res.put("Height",String.valueOf(Heights.getint(tile.getHeight())));
		res.put("Position","x:" + tile.getPosition().x + " y:" + tile.getPosition().y);
		res.put("Rotation",tile.getRotation().toString());
		res.put("Activated",String.valueOf(tile.getActivated()));
		res.put("Color",String.valueOf(tile.getColor()));
		res.put("Counter",tile.getActualanimationcounter().toString());
		tile.getdata(res);
		for (Entry<String,String> data : res.entrySet()) {
			g2.drawString(((data.getKey() != "") ? (data.getKey() + ":") : "") + data.getValue(),10,y += 15);
		}
		g2.dispose();
		return image;
	}

}
