package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import ListenersandHandlers.MainmenuListener;
import logic.Main;
import logic.Statemanager.States;

public class JFrame extends javax.swing.JFrame {

	public static int height = 800, width = 900;  // 600 700

	private JLayeredPane mainMenuPane;
	private MainMenuBackground mainMenuBackground;
	public JButton mainMenuStartButton;

	private JLayeredPane levelpane;
	private ArrayList<CustomWindow> windows;
	
	public JFrame() {
		super("ProgrammingPuzzle");
		setSize(width, height);
		setLayout(null);
		setResizable(false);
		
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		loadlevelPane();
		loadMainmenuPane();
		
		new CustomFrameMouseAdapter(this);

		repaint();
		setVisible(true);
	}

	void loadMainmenuPane() {
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

		getContentPane().add(mainMenuPane);
	}

	void loadlevelPane() {
		levelpane = new JLayeredPane();
		levelpane.setBounds(0,0,width,height);
		levelpane.setVisible(false);
		
		windows = new ArrayList<>();

		getContentPane().add(levelpane);
	}

	public void setState(States newstate) {
		System.out.println("frame switched to " + newstate);
		mainMenuPane.setVisible(false);
		levelpane.setVisible(false);
		switch (newstate) {
		case mainmenu:
			getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
			mainMenuPane.setVisible(true);
			break;
		case programming: case running: case pause:
			//getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
			levelpane.setSize(getGraphicsConfiguration().getDevice().getDisplayMode().getWidth(),getGraphicsConfiguration().getDevice().getDisplayMode().getHeight());
			levelpane.setVisible(true);
			break;
		}
	}
	
	public void addWindow(CustomWindow newWindow) {
		windows.add(newWindow);
		levelpane.add(newWindow);
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
			Main.frame.setCursor(Cursor.getDefaultCursor());
		}
	}
}