package frame;

import java.awt.BasicStroke;
import java.awt.BufferCapabilities;
import java.awt.BufferCapabilities.FlipContents;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import abstractclasses.CustomWindow;
import logic.Constants;
import logic.CustomWindowManager;
import logic.Debugger;
import logic.MainControl;
import logic.StateManager.States;

public class Frame implements Constants {

	private static JFrame frame;
	private static JLayeredPane mainMenuPane;
	private static JButton mainMenuStartButton;
	private static CustomWindowManager manager;
	private static Long lastRepaint = System.currentTimeMillis();

	private Frame() {
	}

	public static void init() {
		frame = new JFrame("ProgrammingPuzzle") {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Debugger.repaint();
			}
		};
		frame.setVisible(false);
		frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
		frame.setLayout(null);
		frame.setResizable(false);

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// frame.setLocationRelativeTo(null);

		loadLevelPane();
		loadMainMenuPane();
		new CustomFrameMouseAdapter(frame);
		setState(MainControl.getStatemanager().getState());

		frame.repaint();
	}

	private static void loadMainMenuPane() {
		mainMenuPane = new JLayeredPane();
		mainMenuPane.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
		mainMenuPane.setVisible(false);

		mainMenuStartButton = new JButton("start");
		mainMenuStartButton.setBackground(Color.DARK_GRAY);
		mainMenuStartButton.setForeground(Color.BLACK);
		mainMenuStartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainControl.getStatemanager().setState(States.programming);
			}
		});
		mainMenuStartButton.setFocusPainted(false);
		mainMenuStartButton.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4)));
		mainMenuStartButton.setBounds((mainMenuPane.getWidth() / 2) - 50, (mainMenuPane.getHeight() / 2) - 25, 100, 50);
		mainMenuPane.add(mainMenuStartButton, JLayeredPane.PALETTE_LAYER);

		frame.getContentPane().add(mainMenuPane);
	}

	private static void loadLevelPane() {
		manager = new CustomWindowManager();
		manager.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
		manager.setVisible(false);

		frame.getContentPane().add(manager);
	}

	public static void setState(States newstate) {
		System.out.println("frame switched to " + newstate);
		mainMenuPane.setVisible(false);
		manager.setVisible(false);
		GraphicsDevice gd = frame.getGraphicsConfiguration().getDevice();
		switch (newstate) {
		case mainmenu:
			frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
			mainMenuPane.setVisible(true);
			break;
		case programming:
		case running:
		case pause:
		case Levelselecting:
			// frame.setSize(gd.getDisplayMode().getWidth(),
			// gd.getDisplayMode().getHeight());
			// manager.setSize(gd.getDisplayMode().getWidth(),
			// gd.getDisplayMode().getHeight());
			manager.setVisible(true);
			// test();
			break;
		}
	}

	public static void addWindow(CustomWindow newWindow) {
		manager.addWindow(newWindow);
		repaint();
	}

	public static void removeWindow(CustomWindow window) {
		manager.removeWindow(window);
		repaint();
	}

	public static void windowToFront(CustomWindow window) {
		manager.windowToFront(window);
		repaint();
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

	public static void repaint() {
		if (lastRepaint + (1000 / FPS) < System.currentTimeMillis()) {
			frame.repaint();
			// System.out.println("Repainted at: " + System.currentTimeMillis());
			lastRepaint = System.currentTimeMillis();
		} else {
			// System.out.println("Didn't repaint");
		}
	}

	// TODO Funktion? Wenn nicht mehr benötigt, entfernen
	public static void test() {
		BufferStrategy strategy = frame.getBufferStrategy();
		BufferCapabilities bufCap = strategy.getCapabilities();
		FlipContents flipContents = bufCap.getFlipContents();

		if (flipContents.equals(BufferCapabilities.FlipContents.UNDEFINED))
			System.out.println("The contents is unknown after a flip");
		if (flipContents.equals(BufferCapabilities.FlipContents.BACKGROUND))
			System.out.println("The contents cleared to the components background color after a flop");
		if (flipContents.equals(BufferCapabilities.FlipContents.PRIOR))
			System.out.println("The contents is the contents of the front buffer just before the flip");
		if (flipContents.equals(BufferCapabilities.FlipContents.COPIED))
			System.out
					.println("The contents is identical to the contents just pushed to the front buffer after a flip");
	}
}

class CustomFrameMouseAdapter extends MouseAdapter {
	JFrame frame;

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
			Point componentPoint = convertPoint(e.getPoint(), component);

			dragwindow = component;
			component.processMousePressedEvent(componentPoint);
			component.processMouseMovedEvent(componentPoint);
		} catch (GetWindowException | PointConvertExeption e1) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			CustomWindow component = dragwindow;
			Point componentPoint = convertPoint(e.getPoint(), component);

			component.drag(e);
			component.processMouseMovedEvent(componentPoint);
			component.setrepaintfull();
		} catch (PointConvertExeption | NullPointerException e2) {
		}
	}

	CustomWindow dragwindow;

	@Override
	public void mouseMoved(MouseEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			Point componentPoint = convertPoint(e.getPoint(), component);
			component.changeCursor(componentPoint);
			component.processMouseMovedEvent(componentPoint);
		} catch (GetWindowException | PointConvertExeption e1) {
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			Point componentPoint = convertPoint(e.getPoint(), component);
			component.processMouseWheelMovedEvent(componentPoint, e);
		} catch (GetWindowException | PointConvertExeption e2) {
		}
	}

	private CustomWindow getWindow(Point p) throws GetWindowException {
		try {
			return (CustomWindow) SwingUtilities.getDeepestComponentAt(frame, (int) p.getX(), (int) p.getY());
		} catch (ClassCastException e) {
			throw new GetWindowException();
		}
	}

	private Point convertPoint(Point point, CustomWindow window) throws PointConvertExeption {
		try {
			return SwingUtilities.convertPoint(frame, point, window);
		} catch (Error e) {
			throw new PointConvertExeption();
		}
	}

	class PointConvertExeption extends Exception {
	}

	class GetWindowException extends Exception {
	}
}