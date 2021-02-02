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
import abstractclasses.Entity;


public class DescriptionEntityWindow extends CustomWindow {

	private Entity entity;

	public DescriptionEntityWindow(Entity entity,Point point) {
		super(200,300,point,"Description of " + entity.getClass().getSimpleName(),0);
		this.entity = entity;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setFont(new Font("Default",Font.BOLD,15));
		g2.setColor(Color.WHITE);
		int y = 2;
		var res = new LinkedHashMap<String,String>();
		res.put("",entity.getClass().getSimpleName());
		res.put("Height",String.valueOf(Heights.getint(entity.getHeight())));
		res.put("Position","x:" + entity.getPosition().x + " y:" + entity.getPosition().y);
		res.put("PixelPosi","x:" + entity.getPixelPosition().x + " y:" + entity.getPixelPosition().y);
		res.put("Rotation",entity.getRotation().toString());
		entity.getdata(res);
		for (Entry<String,String> data : res.entrySet()) {
			g2.drawString(((data.getKey() != "") ? (data.getKey() + ":") : "") + data.getValue(),10,y += 15);
		}
		g2.dispose();
		return image;
	}

}
