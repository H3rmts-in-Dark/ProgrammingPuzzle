package abstractclasses;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;

import frame.Frame;

public abstract class CustomWindow extends JComponent {

	private static Integer scrollbarwidth = 4;
	private static Integer cornerwidht = 6;

	private static Integer defaultX = 40;
	private static Integer defaultY = 40;
	private static Integer defaultWidht = 200;
	private static Integer defaultHeight = 100;
	private static Integer defaultMinWidht = 120;
	private static Integer defaultMinHeight = 45;
	private static Integer defaultMaxWidht = Frame.getWidth();
	private static Integer defaultMaxHeight = Frame.getHeight();

	private String title = "Default";

	private JButton close;

	public CustomWindow(String title) {
		this(defaultWidht, defaultHeight, title);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, String title) {
		this(defaultWidht, defaultHeight, new Point(defaultX, defaultY), title);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, Point defaultPosition, String title) {
		setLocation(defaultPosition);
		setSize(Frame.getWidth() > defaultWidht + defaultPosition.getX() ? defaultWidht
				: Frame.getWidth() - defaultPosition.x * 2,
				Frame.getHeight() > defaultHeight + defaultPosition.getY() ? defaultHeight
						: Frame.getHeight() - defaultPosition.y * 2);
		setTitle(title);
		close = new JButton("x") {
			@Override
			public void paint(Graphics g) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				((Graphics2D) g).setStroke(new BasicStroke(3));
				g.setColor(getForeground());
				g.drawLine(4, 2, getWidth() - 4, getHeight() - 3);
				g.drawLine(getWidth() - 4, 2, 4, getHeight() - 3);
			}
		};
		close.setBackground(Color.DARK_GRAY);
		close.setForeground(Color.BLACK);
		close.setFocusPainted(false);
		close.setVisible(true);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Frame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		close.setBounds(getWidth() - 27, 6, 20, 20);
		add(close);
	}

	public void setTitle(String title) {
		this.title = title;
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
				draw.getWidth() > getHeight() - 40 ? (getHeight() - 40) : draw.getHeight());

		g2.drawImage(subimage, 10, 31, null);

		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.DARK_GRAY);
		g2.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 9, 9);
		g2.fillRect(5, 5, getWidth() - 10, 26);

		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 9, 9);
		g2.drawRoundRect(8, 30, getWidth() - 16, getHeight() - 38, 9, 9);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("default", Font.BOLD, 25));
		g2.drawString(title, cornerwidht + cornerwidht / 2, 25);

		close.setBounds(getWidth() - 27, 6, 20, 20);
		close.repaint();
	}

	protected BufferedImage getEmptyImage() {
		BufferedImage image = new BufferedImage(getImageborders().width,getImageborders().height, BufferedImage.TYPE_INT_RGB);
		return image;
	}

	public Rectangle getImageborders() {
		return new Rectangle(10, 30, getWidth() - 20, getHeight() - 40);
	}

	private BufferedImage getnullimage() {
		BufferedImage image = getEmptyImage();
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
				switch (Frame.getFrame().getCursor().getType()) {
				case Cursor.MOVE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - pos.x,
							e.getLocationOnScreen().y - Frame.getFrame().getY() - pos.y);
					break;
				case Cursor.E_RESIZE_CURSOR:
					setSize(e.getPoint().x - getX(), getHeight());
					break;
				case Cursor.S_RESIZE_CURSOR:
					setSize(getWidth(), e.getPoint().y - getY());
					break;
				case Cursor.N_RESIZE_CURSOR:
					setLocation(getX(), e.getLocationOnScreen().y - Frame.getFrame().getY() - pos.y);
					setSize(getWidth(), pos2.y - getY());
					break;
				case Cursor.W_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - pos.x, getY());
					setSize(pos2.x - getX(), getHeight());
					break;
				case Cursor.SE_RESIZE_CURSOR:
					setSize(e.getPoint().x - getX(), e.getPoint().y - getY());
					break;
				case Cursor.SW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - pos.x, getY());
					setSize(pos2.x - getX(), e.getPoint().y - getY());
					break;
				case Cursor.NW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - pos.x,
							e.getLocationOnScreen().y - Frame.getFrame().getY() - pos.y);
					setSize(pos2.x - getX(), pos2.y - getY());
					break;
				case Cursor.NE_RESIZE_CURSOR:
					setLocation(getX(), e.getLocationOnScreen().y - Frame.getFrame().getY() - pos.y);
					setSize(e.getPoint().x - getX(), pos2.y - getY());
					break;
				}

				if (getWidth() <= defaultMinWidht)
					setSize(defaultMinWidht, getHeight());
				if (getHeight() <= defaultMinHeight)
					setSize(getWidth(), defaultMinHeight);
				if (getWidth() >= defaultMaxWidht)
					setSize(defaultMaxWidht, getHeight());
				if (getHeight() >= defaultMaxHeight)
					setSize(getWidth(), defaultMaxHeight);

			}
		}.start();
	}

	public void startmove(Point point) {
		pos = point;
	}

	public void startdrag() {
		pos2 = new Point(getX() + getWidth(), getY() + getHeight());
	}

	public void changeCursor(Point mousePoint) {
		// edges
		if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth
				&& mousePoint.getY() < getHeight() - cornerwidht && mousePoint.getY() > cornerwidht)
			Frame.getFrame().setCursor(new Cursor(Cursor.W_RESIZE_CURSOR)); // left
		else if (mousePoint.getX() >= getWidth() - scrollbarwidth && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() < getHeight() - cornerwidht && mousePoint.getY() > cornerwidht)
			Frame.getFrame().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); // right
		else if (mousePoint.getX() > cornerwidht && mousePoint.getX() < getWidth() - cornerwidht
				&& mousePoint.getY() <= scrollbarwidth && mousePoint.getY() >= 0)
			Frame.getFrame().setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); // top
		else if (mousePoint.getX() > cornerwidht && mousePoint.getX() < getWidth() - cornerwidht
				&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - scrollbarwidth)
			Frame.getFrame().setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); // bottom
		// corners
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth && mousePoint.getY() <= cornerwidht
				&& mousePoint.getY() >= 0)
			Frame.getFrame().setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top left
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= cornerwidht && mousePoint.getY() <= scrollbarwidth
				&& mousePoint.getY() >= 0)
			Frame.getFrame().setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top top
		else if (mousePoint.getX() >= getWidth() - cornerwidht && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= scrollbarwidth && mousePoint.getY() >= 0)
			Frame.getFrame().setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); // right top top
		else if (mousePoint.getX() >= getWidth() - scrollbarwidth && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= cornerwidht && mousePoint.getY() >= 0)
			Frame.getFrame().setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); // right top right
		else if (mousePoint.getX() >= getWidth() - cornerwidht && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - scrollbarwidth)
			Frame.getFrame().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // right bottom bottom
		else if (mousePoint.getX() >= getWidth() - scrollbarwidth && mousePoint.getX() <= getWidth()
				&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - cornerwidht)
			Frame.getFrame().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // right bottom right
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth && mousePoint.getY() <= getHeight()
				&& mousePoint.getY() >= getHeight() - scrollbarwidth)
			Frame.getFrame().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom left
		else if (mousePoint.getX() >= 0 && mousePoint.getX() <= cornerwidht && mousePoint.getY() <= getHeight()
				&& mousePoint.getY() >= getHeight() - cornerwidht)
			Frame.getFrame().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom bottom
		// Move
		else if (mousePoint.getX() > scrollbarwidth && mousePoint.getX() < getWidth() - scrollbarwidth
				&& mousePoint.getY() < 18 && mousePoint.getY() > scrollbarwidth)
			Frame.getFrame().setCursor(new Cursor(Cursor.MOVE_CURSOR)); // move
		else
			Frame.getFrame().setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * the returned image is cut to windowWidht - 20 and windowHeight - 32
	 * 
	 * @return must return a buffered image
	 */
	public abstract BufferedImage draw();

}