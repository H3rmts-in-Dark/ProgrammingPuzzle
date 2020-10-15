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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import abstractclasses.CustomWindow;
import logic.Constants;
import logic.Customwindowmanger;
import logic.Debuger;
import logic.MainControll;
import logic.Statemanager.States;

public class Frame implements Constants {

	private static JFrame frame;

	private static JLayeredPane mainMenuPane;
	private static JButton mainMenuStartButton;
	private static Customwindowmanger manager;

	private static Long lastrepaint = System.currentTimeMillis();

	private Frame() {
	}

	public static void init() {
		frame = new JFrame("ProgrammingPuzzle") {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Debuger.repaint();
			}
		};
		frame.setVisible(false);
		frame.setSize(FrameWidht, FrameHeight);
		frame.setLayout(null);
		frame.setResizable(false);

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// frame.setLocationRelativeTo(null);

		loadlevelPane();
		loadMainmenuPane();

		new CustomFrameMouseAdapter(frame);

		setState(MainControll.getStatemanager().getState());

		frame.repaint();
	}

	private static void loadMainmenuPane() {
		mainMenuPane = new JLayeredPane();
		mainMenuPane.setBounds(0, 0, FrameWidht, FrameHeight);
		mainMenuPane.setVisible(false);

		mainMenuStartButton = new JButton("start");
		mainMenuStartButton.setBackground(Color.DARK_GRAY);
		mainMenuStartButton.setForeground(Color.BLACK);
		mainMenuStartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainControll.getStatemanager().setState(States.programming);
			}
		});
		mainMenuStartButton.setFocusPainted(false);
		mainMenuStartButton.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4)));
		mainMenuStartButton.setBounds((mainMenuPane.getWidth() / 2) - 50, (mainMenuPane.getHeight() / 2) - 25, 100, 50);
		mainMenuPane.add(mainMenuStartButton, JLayeredPane.PALETTE_LAYER);

		frame.getContentPane().add(mainMenuPane);
	}

	private static void loadlevelPane() {
		manager = new Customwindowmanger();
		manager.setBounds(0, 0, FrameWidht, FrameHeight);
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
			frame.setSize(FrameWidht,FrameHeight);
			mainMenuPane.setVisible(true);
			break;
		case programming:
		case running:
		case pause:
		case Levelselecting:
			//frame.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
			//manager.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
			manager.setVisible(true);
			//test();
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

	public static void WindowtoFront(CustomWindow window) {
		manager.Windowtofront(window);
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
		if (lastrepaint + (1000 / fps) < System.currentTimeMillis()) {
			frame.repaint();
			// System.out.println("rep" + System.currentTimeMillis());
			lastrepaint = System.currentTimeMillis();
		} else {
			// System.out.println("no repaint");
		}
	}
	
	public static void test() {
		BufferStrategy strategy = frame.getBufferStrategy();
		BufferCapabilities bufCap  = strategy.getCapabilities();
		FlipContents flipContents = bufCap.getFlipContents();
		
		if (flipContents.equals(BufferCapabilities.FlipContents.UNDEFINED)) 
			System.out.println("The contents is unknown after a flip");
		if (flipContents.equals(BufferCapabilities.FlipContents.BACKGROUND)) 
			System.out.println("The contents cleared to the components background color after a flop");
		if (flipContents.equals(BufferCapabilities.FlipContents.PRIOR)) 
			System.out.println("The contents is the contents of the front buffer just before the flip");
		if (flipContents.equals(BufferCapabilities.FlipContents.COPIED)) 
			System.out.println("The contents is identical to the contents just pushed to the front buffer after a flip");
	}
}

class CustomFrameMouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {

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
			
			component.processMousepressed(componentPoint);
			component.processMousemoved(componentPoint);
		} catch (getWindowExeption | PointconvertExeption e1) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			CustomWindow component = dragwindow;
			Point componentPoint = convertPoint(e.getPoint(), component);
			
			component.drag(e);
			component.processMousemoved(componentPoint);
			component.setrepaintfull();
		} catch (PointconvertExeption | NullPointerException e2) {
		}
	}

	CustomWindow dragwindow;

	@Override
	public void mouseMoved(MouseEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			Point componentPoint = convertPoint(e.getPoint(), component);
			
			component.changeCursor(componentPoint);
			component.processMousemoved(componentPoint);
		} catch (getWindowExeption | PointconvertExeption e1) {
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			Point componentPoint = convertPoint(e.getPoint(), component);

			component.processMouseWheelmoved(componentPoint, e);
		} catch (getWindowExeption | PointconvertExeption e2) {
		}
	}

	private CustomWindow getWindow(Point p) throws getWindowExeption {
		try {
			return (CustomWindow) SwingUtilities.getDeepestComponentAt(frame, (int) p.getX(), (int) p.getY());
		} catch (ClassCastException e) {
			throw new getWindowExeption();
		}
	}

	private Point convertPoint(Point point, CustomWindow window) throws PointconvertExeption {
		try {
			return SwingUtilities.convertPoint(frame, point, window);
		} catch (Error e) {
			throw new PointconvertExeption();
		}
	}
	
	class PointconvertExeption extends Exception {
	}
	
	class getWindowExeption extends Exception {
	}
	
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
}