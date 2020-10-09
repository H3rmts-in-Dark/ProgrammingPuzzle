package abstractclasses;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;

import frame.Frame;
import logic.Constants;
import tasks.ChangeButtonBrightnessTask;

public abstract class CustomWindow extends JComponent implements Comparable<CustomWindow>, Constants {

	private String title = "Default";
	private CloseButton close;

	private Integer layer = 0;
	private Boolean focused = false;

	public CustomWindow(String title) {
		this(defaultWidht, defaultHeight, title);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, String title) {
		this(defaultWidht, defaultHeight, new Point(defaultX, defaultY), title);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, Point defaultPosition, String title) {
		setLocation(defaultPosition);
		setSize(Frame.getWidth() > defaultWidht + defaultPosition.getX() ? defaultWidht
				: Frame.getWidth() - (int) defaultPosition.getX(),
				Frame.getHeight() > defaultHeight + defaultPosition.getY() ? defaultHeight
						: Frame.getHeight() - (int) defaultPosition.getY());
		resizetodefaults();
		setTitle(title);
		close = new CloseButton(this);

		Frame.addWindow(this);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFocused(Boolean focused) {
		this.focused = focused;
	}

	public Boolean isFocused() {
		return focused;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(new BasicStroke(6));
		g2.setColor(Color.DARK_GRAY);
		g2.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, roundcurves, roundcurves);
		g2.fillRect(5, 5, getWidth() - 10, topbarwhidht - 5 + cornerwidht);

		BufferedImage draw = draw();
		if (draw == null)
			draw = getnullimage();

		// get visible part of the image
		BufferedImage subimage = draw.getSubimage(0, 0,
				draw.getWidth() > getImageborders().width ? (getImageborders().width) : draw.getWidth(),
				draw.getHeight() > getImageborders().height ? (getImageborders().height) : draw.getHeight());

		g2.drawImage(subimage, sidebarwhidht, topbarwhidht, null);

		// title
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Consolas", Font.BOLD, 25));
		String newtitle = "";
		for (int i = 0; i < this.title.length() * 14; i += 14) {
			if (getWidth() - (cornerwidht + cornerwidht / 2) - (getWidth() - close.getX()) > i)
				newtitle += this.title.charAt(i / 14);
			else
				break;

		}
		g2.drawString(newtitle, cornerwidht + cornerwidht / 2, 25);

		close.paintButton(g2);

		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, roundcurves, roundcurves);
		g2.drawRoundRect(sidebarwhidht, topbarwhidht, getWidth() - sidebarwhidht * 2,
				getHeight() - topbarwhidht - sidebarwhidht, roundcurves, roundcurves);

		if (!isFocused()) {
			g2.setColor(new Color(200, 200, 200, 30));
			g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, roundcurves, roundcurves);
		}

	}

	protected BufferedImage getEmptyImage() {
		BufferedImage image = new BufferedImage(getImageborders().width, getImageborders().height,
				BufferedImage.TYPE_INT_RGB);
		return image;
	}

	public Rectangle getImageborders() {
		return new Rectangle(sidebarwhidht, topbarwhidht, getWidth() - sidebarwhidht * 2,
				getHeight() - topbarwhidht - sidebarwhidht);
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
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

	Point topleft = new Point(), bottomright = new Point();

	public void drag(MouseEvent e) {
		new Thread() {
			@Override
			public void run() {
				switch (Frame.getFrame().getCursor().getType()) {
				case Cursor.MOVE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topleft.x,
							e.getLocationOnScreen().y - Frame.getFrame().getY() - topleft.y);
					break;
				case Cursor.E_RESIZE_CURSOR:
					setSize(e.getPoint().x - getX(), getHeight());
					break;
				case Cursor.S_RESIZE_CURSOR:
					setSize(getWidth(), e.getPoint().y - getY());
					break;
				case Cursor.N_RESIZE_CURSOR:
					setLocation(getX(), e.getLocationOnScreen().y - Frame.getFrame().getY() - topleft.y);
					setSize(getWidth(), bottomright.y - getY());
					break;
				case Cursor.W_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topleft.x, getY());
					setSize(bottomright.x - getX(), getHeight());
					break;
				case Cursor.SE_RESIZE_CURSOR:
					setSize(e.getPoint().x - getX(), e.getPoint().y - getY());
					break;
				case Cursor.SW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topleft.x, getY());
					setSize(bottomright.x - getX(), e.getPoint().y - getY());
					break;
				case Cursor.NW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topleft.x,
							e.getLocationOnScreen().y - Frame.getFrame().getY() - topleft.y);
					setSize(bottomright.x - getX(), bottomright.y - getY());
					break;
				case Cursor.NE_RESIZE_CURSOR:
					setLocation(getX(), e.getLocationOnScreen().y - Frame.getFrame().getY() - topleft.y);
					setSize(e.getPoint().x - getX(), bottomright.y - getY());
					break;
				}

				resizetodefaults();

				close.setBounds(getWidth() - 27, 6, 20, 20);

				Frame.repaint();
			}
		}.start();
	}

	public void mouseClicked(Point point) {
		topleft = point;
		bottomright = new Point(getX() + getWidth(), getY() + getHeight());
	}

	private void resizetodefaults() {
		if (getWidth() <= defaultMinWidht)
			setSize(defaultMinWidht, getHeight());
		if (getHeight() <= defaultMinHeight)
			setSize(getWidth(), defaultMinHeight);
		if (getWidth() >= defaultMaxWidht)
			setSize(defaultMaxWidht, getHeight());
		if (getHeight() >= defaultMaxHeight)
			setSize(getWidth(), defaultMaxHeight);
	}

	/**
	 * override to process click events in windowimage
	 * 
	 * @param point
	 */
	public void clicked(Point point) {
	}

	/**
	 * override to process mousemove events in windowimage
	 * 
	 * @param point
	 */
	public void Mousemoved(Point point) {
	}

	/**
	 * override to process mouseWheelmove events in windowimage
	 * 
	 * @param direction
	 */
	public void mouseWheelMoved(Integer direction) {
	}

	public void changeCursor(Point mousePoint) {
		new Thread() {
			@Override
			public void run() {
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
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth
						&& mousePoint.getY() <= cornerwidht && mousePoint.getY() >= 0)
					Frame.getFrame().setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top left
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= cornerwidht
						&& mousePoint.getY() <= scrollbarwidth && mousePoint.getY() >= 0)
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
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= scrollbarwidth
						&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - scrollbarwidth)
					Frame.getFrame().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom left
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= cornerwidht && mousePoint.getY() <= getHeight()
						&& mousePoint.getY() >= getHeight() - cornerwidht)
					Frame.getFrame().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom bottom
				// Move
				else if (mousePoint.getX() > scrollbarwidth && mousePoint.getX() < getWidth() - scrollbarwidth
						&& mousePoint.getY() < topbarwhidht - scrollbarwidth && mousePoint.getY() > scrollbarwidth)
					Frame.getFrame().setCursor(new Cursor(Cursor.MOVE_CURSOR)); // move
				else
					Frame.getFrame().setCursor(Cursor.getDefaultCursor());
			}
		}.start();
	}

	/**
	 * the returned image is cut to windowWidht - 20 and windowHeight - 32
	 * 
	 * @return must return a buffered image
	 */
	public abstract BufferedImage draw();

	@Override
	public int compareTo(CustomWindow o) {
		return (int) Math.signum(this.getLayer() - o.getLayer());
	}

}

class CloseButton extends JButton implements ActionListener {

	final CustomWindow window;

	public CloseButton(CustomWindow window) {
		this.window = window;
		setBounds(window.getWidth() - 27, 6, 20, 20);
		addActionListener(this);
		setOpaque(false);
		new ChangeButtonBrightnessTask(4, this);
		window.add(this);
	}

	public void paintButton(Graphics2D g2) {
		g2.setColor(Color.DARK_GRAY);
		g2.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 4, 4);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(getForeground());
		g2.drawLine(4 + getX(), 2 + getY(), getWidth() - 4 + getX(), getHeight() - 3 + getY());
		g2.drawLine(getWidth() - 4 + getX(), 2 + getY(), 4 + getX(), getHeight() - 3 + getY());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Frame.removeWindow(window);
	}

}