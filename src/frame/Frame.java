package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import ListenersandHandlers.MainmenuListener;
import logic.Statemanager.States;

public class Frame {
	
	private static JFrame frame;	
	
	private static int height = 800, width = 900;  // 600 700

	private static JLayeredPane mainMenuPane;
	private static MainMenuBackground mainMenuBackground;
	private static JButton mainMenuStartButton;

	private static JLayeredPane levelpane;
	private static ArrayList<CustomWindow> windows;
	
	private Frame() {
		
	}
	
	static {
		frame = new JFrame("ProgrammingPuzzle");
		frame.setVisible(false);
		frame.setSize(width, height);
		frame.setLayout(null);
		frame.setResizable(false);
		
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		loadlevelPane();
		loadMainmenuPane();
		
		new CustomFrameMouseAdapter(frame);

		frame.repaint();
	}

	private static void loadMainmenuPane() {
		mainMenuPane = new JLayeredPane();
		mainMenuPane.setBounds(0, 0, width, height);
		mainMenuPane.setVisible(false);

		mainMenuBackground = new MainMenuBackground();
		mainMenuBackground.setBounds(0, 0, mainMenuPane.getWidth(), mainMenuPane.getHeight());
		mainMenuPane.add(mainMenuBackground, JLayeredPane.DEFAULT_LAYER);

		mainMenuStartButton = new JButton("start");
		mainMenuStartButton.setBackground(Color.DARK_GRAY);
		mainMenuStartButton.setForeground(Color.BLACK);
		mainMenuStartButton.addActionListener(new MainmenuListener());
		mainMenuStartButton.setFocusPainted(false);
		mainMenuStartButton.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4)));
		mainMenuStartButton.setUI(CustomUIs.getBasicToggleButtonUI());
		mainMenuStartButton.setBounds((mainMenuPane.getWidth() / 2) - 50, (mainMenuPane.getHeight() / 2) - 25, 100, 50);
		mainMenuPane.add(mainMenuStartButton, JLayeredPane.PALETTE_LAYER);

		frame.getContentPane().add(mainMenuPane);
	}

	private static void loadlevelPane() {
		levelpane = new JLayeredPane();
		levelpane.setBounds(0,0,width,height);
		levelpane.setVisible(false);
		
		windows = new ArrayList<>();

		frame.getContentPane().add(levelpane);
	}

	public static void setState(States newstate) {
		System.out.println("frame switched to " + newstate);
		mainMenuPane.setVisible(false);
		levelpane.setVisible(false);
		GraphicsDevice gd = frame.getGraphicsConfiguration().getDevice();
		switch (newstate) {
		case mainmenu:
			gd.setFullScreenWindow(null);
			mainMenuPane.setVisible(true);
			break;
		case programming: case running: case pause:
			//getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
			levelpane.setSize(gd.getDisplayMode().getWidth(),gd.getDisplayMode().getHeight());
			levelpane.setVisible(true);
			break;
		}
	}
	
	public static void addWindow(CustomWindow newWindow) {
		windows.add(newWindow);
		levelpane.add(newWindow);
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

}


class CustomFrameMouseAdapter extends MouseAdapter {
	
	JFrame frame;
	
	CustomWindow dragcomponent;
	
	public CustomFrameMouseAdapter(JFrame frame) {
		this.frame = frame;
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		try {dragcomponent = (CustomWindow) SwingUtilities.getDeepestComponentAt(frame,e.getX(),e.getY());
			Point componentPoint = SwingUtilities.convertPoint(frame,e.getPoint(),dragcomponent);
			dragcomponent.startmove(componentPoint);
			dragcomponent.startdrag();
		} catch (ClassCastException e1) {dragcomponent = null;}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		try {dragcomponent.drag(e);
		} catch (NullPointerException e2) {}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		try {CustomWindow component = (CustomWindow) SwingUtilities.getDeepestComponentAt(frame,e.getX(),e.getY());
			Point componentPoint = SwingUtilities.convertPoint(frame,e.getPoint(),component);
			component.changeCursor(componentPoint);
		} catch (ClassCastException | Error e1) {
			frame.setCursor(Cursor.getDefaultCursor());
		}
	}
}