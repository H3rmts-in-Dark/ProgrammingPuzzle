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
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;

import frame.Frame;
import logic.Constants;
import tasks.ChangeButtonBrightnessTask;

public abstract class CustomWindow extends JComponent implements Comparable<CustomWindow>, Constants {

	private Boolean focused = false, fullyRepaint;
	private Integer layer = 0;
	private String title = "Default";
	private Point mouse;
	private CloseButton close;
	private BufferedImage innerImage, surroundingImage;
	private Point topLeft = new Point(), bottomRight = new Point();

	public CustomWindow(String title) {
		this(DEFAULTWITH, DEFAULTHEIGHT, title);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, String title) {
		this(defaultWidht, defaultHeight, new Point(DEFAULTX, DEFAULTY), title);
	}

	public CustomWindow(Integer defaultWidht, Integer defaultHeight, Point defaultPosition, String title) {
		setLocation(defaultPosition);
		setSize(Frame.getWidth() > defaultWidht + defaultPosition.getX() ? defaultWidht
				: Frame.getWidth() - (int) defaultPosition.getX(),
				Frame.getHeight() > defaultHeight + defaultPosition.getY() ? defaultHeight
						: Frame.getHeight() - (int) defaultPosition.getY());
		resizeToDefaults();
		setTitle(title);
		close = new CloseButton(this);
		fullyRepaint = true;
		mouse = new Point(0, 0);

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
		if (fullyRepaint) {
			repaintAll();
		}

		// inner image
		g.drawImage(innerImage, getImageborders().x, getImageborders().y, null);

		// draw cursor
		drawCursor((Graphics2D) g, mouse);

		// surrounding image
		g.drawImage(surroundingImage, 0, 0, null);

		// closebutton
		close.paintButton((Graphics2D) g);

		// gray out unfocused
		if (!isFocused()) {
			g.setColor(new Color(100, 200, 200, 30));
			g.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, ROUNDCURVES, ROUNDCURVES);
		}
	}

	/**
	 * Zeichnet das BufferedImage neu, das für die Zeichnung auf den Component
	 * benutzt wird. Diese Methode sollte nicht manuell aufgerufen werden, da sie
	 * automatisch gestartet wird wenn triggerFullRepaint() aufgerufen wurde.
	 */
	private void repaintAll() {
		innerImage = getEmptyImage();
		BufferedImage image = draw();
		if (image == null)
			image = getNullImage();

		Graphics2D g = innerImage.createGraphics();

		// Zeichnet das Bild soweit nötig auf das Fenster und löscht es danach sofort
		g.drawImage(
				image.getSubimage(0, 0,
						image.getWidth() > getImageborders().width ? (getImageborders().width) : image.getWidth(),
						image.getHeight() > getImageborders().height ? (getImageborders().height) : image.getHeight()),
				0, 0, null);
		g.dispose();

		surroundingImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = surroundingImage.createGraphics();

		// top + middle surrounding
		g2.setStroke(new BasicStroke(CORNERWIDTH + CORNERWIDTH / 4));
		g2.setColor(Color.DARK_GRAY);
		g2.drawRoundRect(SCROLLBARWIDTH, SCROLLBARWIDTH, getWidth() - CORNERWIDTH - SCROLLBARWIDTH,
				getHeight() - CORNERWIDTH - SCROLLBARWIDTH, ROUNDCURVES, ROUNDCURVES);
		g2.fillRect(SCROLLBARWIDTH, SCROLLBARWIDTH, getWidth() - 10, TOPBARWIDTH - SCROLLBARWIDTH);

		// Zeichnet den Titel des Fensters
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Consolas", Font.BOLD, 25));
		String newtitle = "";
		for (int i = 0; i < this.title.length() * 14; i += 14) {
			if (getWidth() - (CORNERWIDTH + CORNERWIDTH / 2) - (getWidth() - close.getX()) > i)
				newtitle += this.title.charAt(i / 14);
			else
				break;
		}
		g2.drawString(newtitle, CORNERWIDTH + CORNERWIDTH / 2, 25);

		// surrounding
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, ROUNDCURVES, ROUNDCURVES);
		g2.drawRoundRect(SIDEBARWIDTH, TOPBARWIDTH, getWidth() - SIDEBARWIDTH * 2,
				getHeight() - TOPBARWIDTH - SIDEBARWIDTH, ROUNDCURVES, ROUNDCURVES);

		// Löscht das Bild aus dem Speicher, um Platz zu sparen und setzt danach die
		// Nötigkeit zum Repainten zurück
		g2.dispose();
		fullyRepaint = false;
	}

	/**
	 * Wenn diese Methode aufgerufen wurde, wird repaintAll() bei der nächsten
	 * Zeichnung automatisch aufgerufen.
	 */
	public void triggerFullRepaint() {
		fullyRepaint = true;
	}

	/**
	 * Gibt ein BufferedImage zurück, das die Ausmaße des Fensters hat.
	 */
	protected BufferedImage getEmptyImage() {
		return new BufferedImage(getImageborders().width, getImageborders().height, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Gibt die Ausmaße des Fensters zurück.
	 * 
	 * @return
	 */
	public Rectangle getImageborders() {
		return new Rectangle(SIDEBARWIDTH, TOPBARWIDTH, getWidth() - SIDEBARWIDTH * 2,
				getHeight() - TOPBARWIDTH - SIDEBARWIDTH);
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	/**
	 * Gibt ein Fehlerbild zurück, das leicht zu erkennen lässt, das etwas nicht
	 * stimmt.
	 * 
	 * @return
	 */
	private BufferedImage getNullImage() {
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

	public void drag(MouseEvent e) {
		new Thread() {
			@Override
			public void run() {
				switch (Frame.getFrame().getCursor().getType()) {
				case Cursor.MOVE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topLeft.x,
							e.getLocationOnScreen().y - Frame.getFrame().getY() - topLeft.y);
					break;
				case Cursor.E_RESIZE_CURSOR:
					setSize(e.getPoint().x - getX(), getHeight());
					break;
				case Cursor.S_RESIZE_CURSOR:
					setSize(getWidth(), e.getPoint().y - getY());
					break;
				case Cursor.N_RESIZE_CURSOR:
					setLocation(getX(), e.getLocationOnScreen().y - Frame.getFrame().getY() - topLeft.y);
					setSize(getWidth(), bottomRight.y - getY());
					break;
				case Cursor.W_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topLeft.x, getY());
					setSize(bottomRight.x - getX(), getHeight());
					break;
				case Cursor.SE_RESIZE_CURSOR:
					setSize(e.getPoint().x - getX(), e.getPoint().y - getY());
					break;
				case Cursor.SW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topLeft.x, getY());
					setSize(bottomRight.x - getX(), e.getPoint().y - getY());
					break;
				case Cursor.NW_RESIZE_CURSOR:
					setLocation(e.getLocationOnScreen().x - Frame.getFrame().getX() - topLeft.x,
							e.getLocationOnScreen().y - Frame.getFrame().getY() - topLeft.y);
					setSize(bottomRight.x - getX(), bottomRight.y - getY());
					break;
				case Cursor.NE_RESIZE_CURSOR:
					setLocation(getX(), e.getLocationOnScreen().y - Frame.getFrame().getY() - topLeft.y);
					setSize(e.getPoint().x - getX(), bottomRight.y - getY());
					break;
				}

				resizeToDefaults();

				close.setBounds(getWidth() - 27, 6, 20, 20);

				Frame.repaint();
			}
		}.start();
	}

	public void saveMouse(Point point) {
		topLeft = point;
		bottomRight = new Point(getX() + getWidth(), getY() + getHeight());
	}

	private void resizeToDefaults() {
		if (getWidth() <= DEFAULTMINWIDTH)
			setSize(DEFAULTMINWIDTH, getHeight());
		if (getHeight() <= DEFAULTMINHEIGHT)
			setSize(getWidth(), DEFAULTMINHEIGHT);
		if (getWidth() >= DEFAULTMAXWIDTH)
			setSize(DEFAULTMAXWIDTH, getHeight());
		if (getHeight() >= DEFAULTMAXHEIGHT)
			setSize(getWidth(), DEFAULTMAXHEIGHT);
	}

	/**
	 * override to process click events in windowimage
	 * 
	 * @param point
	 */
	public void mousePressed(Point point) {
	}

	/**
	 * override to process mousemove events in windowimage leave
	 * super.Mousemoved(point);
	 * 
	 * @param point
	 */
	public void mouseMoved(Point point) {
	}

	/**
	 * override to process mouseWheelmove events in windowimage
	 * 
	 * @param direction
	 */
	public void mouseWheelMoved(Integer direction) {
	}

	public void processMousePressedEvent(Point point) {
		saveMouse(point);
		if (point.getX() >= getImageborders().getX()
				&& point.getX() <= getImageborders().getWidth() + getImageborders().getX()
				&& point.getY() >= getImageborders().getY()
				&& point.getY() <= getImageborders().getHeight() + getImageborders().getY()) {
			mouse = point;
			if (isFocused())
				mousePressed(new Point((int) (point.getX() - getImageborders().getX()),
						(int) (point.getY() - getImageborders().getY())));
			else
				Frame.windowToFront(this);
		} else
			Frame.windowToFront(this);
	}

	public void processMouseMovedEvent(Point point) {
		if (point.getX() >= getImageborders().getX()
				&& point.getX() <= getImageborders().getWidth() + getImageborders().getX()
				&& point.getY() >= getImageborders().getY()
				&& point.getY() <= getImageborders().getHeight() + getImageborders().getY()) {
			mouse = point;
			if (isFocused())
				mouseMoved(new Point((int) (point.getX() - getImageborders().getX()),
						(int) (point.getY() - getImageborders().getY())));
		}
	}

	public void processMouseWheelMovedEvent(Point point, MouseWheelEvent e) {
		if (point.getX() >= getImageborders().getX()
				&& point.getX() <= getImageborders().getWidth() + getImageborders().getX()
				&& point.getY() >= getImageborders().getY()
				&& point.getY() <= getImageborders().getHeight() + getImageborders().getY()) {
			if (isFocused())
				mouseWheelMoved(e.getWheelRotation());
		}
	}

	public void changeCursor(Point mousePoint) {
		new Thread() {
			@Override
			public void run() {
				// edges
				if (mousePoint.getX() >= 0 && mousePoint.getX() <= SCROLLBARWIDTH
						&& mousePoint.getY() < getHeight() - CORNERWIDTH && mousePoint.getY() > CORNERWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.W_RESIZE_CURSOR)); // left
				else if (mousePoint.getX() >= getWidth() - SCROLLBARWIDTH && mousePoint.getX() <= getWidth()
						&& mousePoint.getY() < getHeight() - CORNERWIDTH && mousePoint.getY() > CORNERWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); // right
				else if (mousePoint.getX() > CORNERWIDTH && mousePoint.getX() < getWidth() - CORNERWIDTH
						&& mousePoint.getY() <= SCROLLBARWIDTH && mousePoint.getY() >= 0)
					Frame.getFrame().setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); // top
				else if (mousePoint.getX() > CORNERWIDTH && mousePoint.getX() < getWidth() - CORNERWIDTH
						&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - SCROLLBARWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); // bottom
				// corners
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= SCROLLBARWIDTH
						&& mousePoint.getY() <= CORNERWIDTH && mousePoint.getY() >= 0)
					Frame.getFrame().setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top left
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= CORNERWIDTH
						&& mousePoint.getY() <= SCROLLBARWIDTH && mousePoint.getY() >= 0)
					Frame.getFrame().setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR)); // left top top
				else if (mousePoint.getX() >= getWidth() - CORNERWIDTH && mousePoint.getX() <= getWidth()
						&& mousePoint.getY() <= SCROLLBARWIDTH && mousePoint.getY() >= 0)
					Frame.getFrame().setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); // right top top
				else if (mousePoint.getX() >= getWidth() - SCROLLBARWIDTH && mousePoint.getX() <= getWidth()
						&& mousePoint.getY() <= CORNERWIDTH && mousePoint.getY() >= 0)
					Frame.getFrame().setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR)); // right top right
				else if (mousePoint.getX() >= getWidth() - CORNERWIDTH && mousePoint.getX() <= getWidth()
						&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - SCROLLBARWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // right bottom bottom
				else if (mousePoint.getX() >= getWidth() - SCROLLBARWIDTH && mousePoint.getX() <= getWidth()
						&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - CORNERWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // right bottom right
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= SCROLLBARWIDTH
						&& mousePoint.getY() <= getHeight() && mousePoint.getY() >= getHeight() - SCROLLBARWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom left
				else if (mousePoint.getX() >= 0 && mousePoint.getX() <= CORNERWIDTH && mousePoint.getY() <= getHeight()
						&& mousePoint.getY() >= getHeight() - CORNERWIDTH)
					Frame.getFrame().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR)); // left bottom bottom
				// move
				else if (mousePoint.getX() > SCROLLBARWIDTH && mousePoint.getX() < getWidth() - SCROLLBARWIDTH
						&& mousePoint.getY() < TOPBARWIDTH - SCROLLBARWIDTH && mousePoint.getY() > SCROLLBARWIDTH)
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

	/**
	 * 
	 * @param g2    graphics objekt
	 * @param point top left point of cursor
	 */
	public abstract void drawCursor(Graphics2D g2, Point point);

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
		new ChangeButtonBrightnessTask(this);
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