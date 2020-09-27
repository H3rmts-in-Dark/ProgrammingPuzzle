package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import logic.Main;

public abstract class CustomWindow extends JComponent {

	private Integer scrollbarwidth = 4;
	private Integer cornerwidht = 6;
	
	private static Integer defaultX = 40;
	private static Integer defaultY = 40;
	private static Integer defaultWidht = 200;
	private static Integer defaultHeight = 100;
	private static Integer defaultMinWidht = 60;
	private static Integer defaultMinHeight = 45;
	private static Integer defaultMaxWidht = Main.frame.getWidth();
	private static Integer defaultMaxHeight = Main.frame.getHeight();
	
	public CustomWindow() {
		this(defaultWidht,defaultHeight);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight) {
		this(defaultWidht, defaultHeight, new Point(defaultX,defaultY));
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, Point defaultPosition) {
		setLocation(defaultPosition);
		setSize(Main.frame.getWidth() > defaultWidht + defaultPosition.getX() ? defaultWidht : Main.frame.getWidth() - defaultPosition.x * 2,
				Main.frame.getHeight() > defaultHeight + defaultPosition.getY() ? defaultHeight : Main.frame.getHeight() - defaultPosition.y * 2);
		
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(30, 30, 40));
		g2.fillRect(5, 5, getWidth() - 10, getHeight() - 10);

		BufferedImage draw = draw();
		if (draw == null)
			draw = getnullimage();

		// get visible part of the image
		BufferedImage subimage = draw.getSubimage(0, 0,
				draw.getWidth() > getWidth() - 18 ? (getWidth() - 18) : draw.getWidth(),
				draw.getWidth() > getHeight() - 30 ? (getHeight() - 30) : draw.getHeight());

		g2.drawImage(subimage, 10, 21, null);

		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.DARK_GRAY);
		g2.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 9, 9);
		g2.fillRect(5, 5, getWidth() - 10, 16);

		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 9, 9);
		g2.drawRoundRect(8, 20, getWidth() - 16, getHeight() - 28, 9, 9);
	}
	
	BufferedImage getEmptyImage() {
		BufferedImage image = new BufferedImage(getWidth() - 18, getHeight() - 30, BufferedImage.TYPE_INT_RGB);
		return image;
	}

	private BufferedImage getnullimage() {
		BufferedImage image = new BufferedImage(getWidth() - 18, getHeight() - 30, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.BLUE);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2.setFont(new Font("Default", Font.BOLD, 20));
		g2.setColor(Color.BLACK);
		g2.drawString("Nothing to paint", image.getWidth() / 2 - 60, image.getHeight() / 2);
		g2.dispose();
		return image;
	}
	
	Point pos = new Point(), pos2 = new Point();
	
	public void drag(MouseEvent e) {
		new Thread() {
			@Override
			public void run() {
				switch (Main.frame.getCursor().getType()) {
				case Cursor.MOVE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Main.frame.getX() - pos.x,e.getLocationOnScreen().y - Main.frame.getY() - pos.y);
					break;
				case Cursor.E_RESIZE_CURSOR:
					setSize(e.getPoint().x-getX(),getHeight());
					break;
				case Cursor.S_RESIZE_CURSOR:
					setSize(getWidth(),e.getPoint().y-getY());
					break;
				case Cursor.N_RESIZE_CURSOR:
					setLocation(getX(),e.getLocationOnScreen().y - Main.frame.getY() - pos.y);
					setSize(getWidth(),pos2.y - getY());
					break;
				case Cursor.W_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Main.frame.getX() - pos.x,getY());
					setSize(pos2.x - getX(),getHeight());
					break;
				case Cursor.SE_RESIZE_CURSOR:
					setSize(e.getPoint().x-getX(),e.getPoint().y-getY());
					break;
				case Cursor.SW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Main.frame.getX() - pos.x,getY());
					setSize(pos2.x - getX(),e.getPoint().y-getY());
					break;
				case Cursor.NW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Main.frame.getX() - pos.x,e.getLocationOnScreen().y - Main.frame.getY() - pos.y);
					setSize(pos2.x - getX(),pos2.y - getY());
					break;
				case Cursor.NE_RESIZE_CURSOR:
					setLocation(getX(),e.getLocationOnScreen().y - Main.frame.getY() - pos.y);
					setSize(e.getPoint().x-getX(),pos2.y - getY());
					break;
				}
				
				if (getWidth() <= defaultMinWidht) 
					setSize(defaultMinWidht,getHeight());
				if (getHeight() <= defaultMinHeight) 
					setSize(getWidth(),defaultMinHeight);
				if (getWidth() >= defaultMaxWidht) 
					setSize(defaultMaxWidht,getHeight());
				if (getHeight() >= defaultMaxHeight) 
					setSize(getWidth(),defaultMaxHeight);
				
			}
		}.start();
	}
	
	public void startmove(Point point) {
		pos = point;
	}
	
	public void startdrag() {
		pos2 = new Point(getX()+getWidth(),getY()+getHeight());
	}

	public void changeCursor(Point mousePoint) {
		// edges
		if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth && mousePoint.getY() < getHeight() - cornerwidht
				&& mousePoint.getY() > cornerwidht)
			Main.frame.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR)); // left
		else if (mousePoint.getX() >= getWidth() - scrollbarwidth && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() < getHeight() - cornerwidht && mousePoint.getY() > cornerwidht)
			Main.frame.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); // right
		else if (mousePoint.getX() > cornerwidht && mousePoint.getX() < getWidth() - cornerwidht && mousePoint.getY() <= scrollbarwidth
				&& mousePoint.getY() >= 0)
			Main.frame.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); // top
		else if (mousePoint.getX() > cornerwidht && mousePoint.getX() < getWidth() - cornerwidht && mousePoint.getY() <= getHeight()
				&& mousePoint.getY() >= getHeight() - scrollbarwidth)
			Main.frame.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); // bottom
		// corners
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth && mousePoint.getY() <= cornerwidht && mousePoint.getY() >= 0)
			Main.frame.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top left
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= cornerwidht && mousePoint.getY() <= scrollbarwidth && mousePoint.getY() >= 0)
			Main.frame.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top top
		else if (mousePoint.getX() >= getWidth() - cornerwidht && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= scrollbarwidth && mousePoint.getY() >= 0)
			Main.frame.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); // right top top
		else if (mousePoint.getX() >= getWidth() - scrollbarwidth && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= cornerwidht && mousePoint.getY() >= 0)
			Main.frame.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); // right top right
		else if (mousePoint.getX() >= getWidth() - cornerwidht && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - scrollbarwidth)
			Main.frame.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // right bottom bottom
		else if (mousePoint.getX() >= getWidth() - scrollbarwidth && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - cornerwidht)
			Main.frame.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // right bottom right
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth && mousePoint.getY() <= getHeight()
				&& mousePoint.getY() >= getHeight() - scrollbarwidth)
			Main.frame.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom left
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= cornerwidht	&& mousePoint.getY() <= getHeight() 
				&& mousePoint.getY() >= getHeight() - cornerwidht)
			Main.frame.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom bottom
		// Move
		else if (mousePoint.getX() > scrollbarwidth && mousePoint.getX() < getWidth() - scrollbarwidth && mousePoint.getY() < 18
				&& mousePoint.getY() > scrollbarwidth)
			Main.frame.setCursor(new Cursor(Cursor.MOVE_CURSOR)); // move
		else
			Main.frame.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * the returned image is cut to windowWidht - 20 and windowHeight - 32
	 * 
	 * @return must return a buffered image
	 */
	abstract BufferedImage draw();

}