package abstractclasses;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import frame.Frame;
import logic.Constants;
import logic.Debugger;

public abstract class CustomWindow extends JInternalFrame implements Comparable<CustomWindow>, Constants {

	private int layer = 10000;

	private Canvas drawarea;

	public CustomWindow(String title) {
		this(DEFAULTWITH, DEFAULTHEIGHT, title);
	}

	public CustomWindow(int defaultWidht, int defaultHeight, String title) {
		this(defaultWidht, defaultHeight, new Point(DEFAULTX, DEFAULTY), title, 1);
	}

	public CustomWindow(int Widht, int Height, Point defaultPosition, String title, int level) {
		this(Widht, Height, defaultPosition, title, level, true);
	}

	public CustomWindow(int Widht, int Height, Point defaultPosition, String title, int level, boolean def) {
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setMaximizable(true);
		setResizable(true);
		setVisible(true);
		setTitle(title);
		setLocation(defaultPosition.x, defaultPosition.y);
		setSize(Widht, Height);
		setTitle(title + "  " + new Random().nextInt(999));
		setFocusable(true);
		setIgnoreRepaint(true);

		if (def) {
			setLayout(null);

			drawarea = new Canvas();
			drawarea.setLocation(0, 0);

			add(drawarea, JLayeredPane.DEFAULT_LAYER);

			drawarea.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					Frame.getWindowManager().windowToFront(getThis());
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					getThis().mousePressed(e.getPoint());
				}

			});

			drawarea.addMouseMotionListener(new MouseAdapter() {

				@Override
				public void mouseMoved(MouseEvent e) {
					getThis().mouseMoved(e.getPoint());
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					getThis().mouseMoved(e.getPoint());
				}

			});

			drawarea.addMouseWheelListener(new MouseAdapter() {

				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					getThis().mouseWheelMoved(e.getWheelRotation());
				}

			});
		}

		addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				Frame.getWindowManager().removeWindow(getThis());
			}

		});

		Frame.getWindowManager().addWindow(level, this);
		if (def)
			drawarea.createBufferStrategy(3);
	}

	public CustomWindow getThis() {
		return this;
	}

	public void Render() {
		Debugger.startDraw(this);

		if (drawarea == null)
			return;

		drawarea.setSize(getSize());

		BufferStrategy bs = drawarea.getBufferStrategy();
		if (bs == null)
			return;

		do {
			do {
				Graphics g2 = bs.getDrawGraphics();
				BufferedImage drawimage = null;
				try {
					drawimage = getImage();
				} catch (Exception e) {
					e.printStackTrace();
					drawimage = getErrorImage();
				}
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, drawarea.getWidth(), drawarea.getHeight());
				g2.drawImage(drawimage, 0, 0, null);
				g2.dispose();

			} while (bs.contentsRestored());
			bs.show();
		} while (bs.contentsLost());

		Debugger.endDraw(this);
	}

	/**
	 * Gibt ein BufferedImage zur�ck, das die Ausma�e des Fensters hat.
	 */
	protected BufferedImage getEmptyImage() {
		return new BufferedImage(getImageborders().width, getImageborders().height, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Gibt die Ausma�e des Fensters zur�ck.
	 * 
	 * @return
	 */
	public Rectangle getImageborders() {
		return new Rectangle(0, 0, getWidth(), getHeight());
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setComponentLocation(int x, int y, Component component) {
		component.setLocation(x + getX(), y + getY());
	}

	/**
	 * Gibt ein Fehlerbild zur�ck, das leicht zu erkennen l�sst, das etwas nicht
	 * stimmt.
	 * 
	 * @return
	 */
	private BufferedImage getErrorImage() {
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
	public void mouseWheelMoved(int direction) {
	}

	/**
	 * @return must return a buffered image
	 */
	public abstract BufferedImage getImage();

	@Override
	public int compareTo(CustomWindow o) {
		return (int) Math.signum(this.getLayer() - o.getLayer());
	}

}
