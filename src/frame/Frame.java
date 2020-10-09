package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.event.*;
import java.util.UUID;

import javax.swing.*;

import abstractclasses.CustomWindow;
import logic.Constants;
import logic.Customwindowmanger;
import logic.MainControll;
import logic.Statemanager.States;

public class Frame implements Constants {

	private static JFrame frame;

	private static JLayeredPane mainMenuPane;
	private static MainMenuBackground mainMenuBackground;
	private static JButton mainMenuStartButton;
	private static Customwindowmanger manager;
	
	private static Long lastrepaint = System.currentTimeMillis();

	private Frame() {
	}

	public static void init() {
		frame = new JFrame("ProgrammingPuzzle" + UUID.randomUUID());
		frame.setVisible(false);
		frame.setSize(FrameWidht,FrameHeight);
		frame.setLayout(null);
		frame.setResizable(false);

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);

		loadlevelPane();
		loadMainmenuPane();

		new CustomFrameMouseAdapter(frame);

		setState(MainControll.getStatemanager().getState());

		frame.repaint();
	}

	private static void loadMainmenuPane() {
		mainMenuPane = new JLayeredPane();
		mainMenuPane.setBounds(0, 0,FrameWidht,FrameHeight);
		mainMenuPane.setVisible(false);

		mainMenuBackground = new MainMenuBackground();
		mainMenuBackground.setBounds(0, 0, mainMenuPane.getWidth(), mainMenuPane.getHeight());
		mainMenuPane.add(mainMenuBackground, JLayeredPane.DEFAULT_LAYER);

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
			gd.setFullScreenWindow(null);
			mainMenuPane.setVisible(true);
			break;
		case programming:
		case running:
		case pause:
		case Levelselecting:
			//frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);
			manager.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
			manager.setVisible(true);
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
		if(lastrepaint + repaintdelay < System.currentTimeMillis()) {
			frame.repaint();
			//System.out.println("rep" + System.currentTimeMillis());
			lastrepaint = System.currentTimeMillis();
		} else {
			//System.out.println("no repaint");
		}
	}
}

class CustomFrameMouseAdapter implements MouseListener,MouseMotionListener,MouseWheelListener {

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
			Point componentPoint = SwingUtilities.convertPoint(frame, e.getPoint(), component);
			component.mouseClicked(componentPoint);
			
			dragwindow = component;
			
			if (componentPoint.getX() >= component.getImageborders().getX()
					&& componentPoint.getX() <= component.getImageborders().getWidth()
							+ component.getImageborders().getX()
					&& componentPoint.getY() >= component.getImageborders().getY() && componentPoint
							.getY() <= component.getImageborders().getHeight() + component.getImageborders().getY()) {
				if(component.isFocused())
					component.clicked(new Point((int) (componentPoint.getX() - component.getImageborders().getX()),
							(int) (componentPoint.getY() - component.getImageborders().getY())));
				else
					Frame.WindowtoFront(component);
			}
			else 
				Frame.WindowtoFront(component);
			component.Mousemoved(new Point((int) (componentPoint.getX() - component.getImageborders().getX()),
					(int) (componentPoint.getY() - component.getImageborders().getY())));
		} catch (ClassCastException e1) {
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			CustomWindow component = dragwindow;
			component.drag(e);
			Point componentPoint = SwingUtilities.convertPoint(frame, e.getPoint(), component);
			if (componentPoint.getX() >= component.getImageborders().getX()
					&& componentPoint.getX() <= component.getImageborders().getWidth()
							+ component.getImageborders().getX()
					&& componentPoint.getY() >= component.getImageborders().getY() && componentPoint
							.getY() <= component.getImageborders().getHeight() + component.getImageborders().getY()) {
				component.Mousemoved(new Point((int) (componentPoint.getX() - component.getImageborders().getX()),
						(int) (componentPoint.getY() - component.getImageborders().getY())));
			}
		} catch (ClassCastException | NullPointerException e2) {
		}
	}

	CustomWindow dragwindow;

	@Override
	public void mouseMoved(MouseEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());

			Point componentPoint = SwingUtilities.convertPoint(frame, e.getPoint(), component);
			component.changeCursor(componentPoint);

			if (componentPoint.getX() >= component.getImageborders().getX()
					&& componentPoint.getX() <= component.getImageborders().getWidth()
							+ component.getImageborders().getX()
					&& componentPoint.getY() >= component.getImageborders().getY() && componentPoint
							.getY() <= component.getImageborders().getHeight() + component.getImageborders().getY()) {
				if (component.isFocused())
					component.Mousemoved(new Point((int) (componentPoint.getX() - component.getImageborders().getX()),
							(int) (componentPoint.getY() - component.getImageborders().getY())));
			}
		} catch (ClassCastException e1) {
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		try {
			CustomWindow component = getWindow(e.getPoint());
			if (component.isFocused())
				component.mouseWheelMoved(e.getWheelRotation());
		} catch (ClassCastException e2) {
		}

	}

	private CustomWindow getWindow(Point p) throws ClassCastException {
		try {
			return (CustomWindow) SwingUtilities.getDeepestComponentAt(frame, (int) p.getX(), (int) p.getY());
		} catch (ClassCastException e) {
			throw new ClassCastException("not a customwindow");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
}