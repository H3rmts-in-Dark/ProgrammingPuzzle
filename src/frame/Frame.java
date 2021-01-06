package frame;


import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import abstractclasses.CustomWindow;
import logic.Constants;


public class Frame implements Constants {

	private static JFrame frame;
	private static CustomWindowManager Windowmanager;

	private Frame() {
	}

	public static void init() {
		frame = new JFrame("ProgrammingPuzzle");
		frame.setVisible(false);
		frame.setSize(FRAMEWIDTH,FRAMEHEIGHT);
		frame.setFocusable(true);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.addKeyListener(new KeyHandler());

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// frame.setLocationRelativeTo(null);

		Windowmanager = new CustomWindowManager();
		Windowmanager.setBounds(0,0,FRAMEWIDTH,FRAMEHEIGHT);
		Windowmanager.setVisible(false);

		frame.getContentPane().add(Windowmanager);

		new CustomFrameMouseAdapter(frame);
		Windowmanager.setVisible(true);

		frame.repaint();
	}

	public static CustomWindowManager getWindowManager() {
		return Windowmanager;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static int getWidth() {
		return frame.getWidth();
	}

	public static int getHeight() {
		return frame.getHeight();
	}

	public static void setVisible() {
		frame.setVisible(true);
	}

	/*
	 * public static void setState() { manager.setVisible(true); GraphicsDevice gd =
	 * frame.getGraphicsConfiguration().getDevice(); frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
	 * // frame.setSize(gd.getDisplayMode().getWidth(), // gd.getDisplayMode().getHeight());
	 * // manager.setSize(gd.getDisplayMode().getWidth(), // gd.getDisplayMode().getHeight());
	 * // test(); }
	 */

}



class CustomFrameMouseAdapter extends MouseAdapter implements Constants {

	JFrame frame;
	Long movetimer = (long) 0;
	Long dragtimer = (long) 0;

	public CustomFrameMouseAdapter(JFrame frame) {
		this.frame = frame;
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			Point componentPoint = convertPoint(e.getPoint(),component);
			dragwindow = component;
			component.processMousePressedEvent(componentPoint);
			component.processMouseMovedEvent(componentPoint);
		} catch (GetWindowException | PointConvertExeption e1) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (System.currentTimeMillis() > dragtimer) {
			dragtimer = System.currentTimeMillis() + dragdelay;
			try {
				CustomWindow component = dragwindow;
				Point componentPoint = convertPoint(e.getPoint(),component);
				component.drag(e,componentPoint);
				component.processMouseMovedEvent(componentPoint);
			} catch (PointConvertExeption | NullPointerException e2) {
			}
		}
	}

	CustomWindow dragwindow;

	@Override
	public void mouseMoved(MouseEvent e) {
		if (System.currentTimeMillis() > movetimer) {
			movetimer = System.currentTimeMillis() + movedelay;
			try {
				CustomWindow component = getWindow(e.getPoint());
				Point componentPoint = convertPoint(e.getPoint(),component);
				component.changeCursor(componentPoint);
				component.processMouseMovedEvent(componentPoint);
			} catch (GetWindowException | PointConvertExeption e1) {
				frame.setCursor(Cursor.getDefaultCursor());
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			Point componentPoint = convertPoint(e.getPoint(),component);
			component.processMouseWheelMovedEvent(componentPoint,e);
		} catch (GetWindowException | PointConvertExeption e2) {
		}
	}

	private CustomWindow getWindow(Point p) throws GetWindowException {
		try {
			return (CustomWindow) SwingUtilities.getDeepestComponentAt(frame,(int) p.getX(),(int) p.getY());
		} catch (ClassCastException e) {
			throw new GetWindowException();
		}
	}

	private Point convertPoint(Point point,CustomWindow window) throws PointConvertExeption {
		try {
			return SwingUtilities.convertPoint(frame,point,window);
		} catch (Error e) {
			throw new PointConvertExeption();
		}
	}

	class PointConvertExeption extends Exception {
	}

	class GetWindowException extends Exception {
	}

}
