package frame;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import logic.Constants;


public class Frame implements Constants {

	private static JFrame frame;
	private static CustomWindowManager Windowmanager;

	private Frame() {
	}

	public static void init() {
		frame = new JFrame("ProgrammingPuzzle");
		frame.setVisible(false);
		frame.setLocation(0,0);
		frame.setSize(getMaxDimension());
		frame.setFocusable(true);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.addKeyListener(new UserInputInterpreter());

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Windowmanager = new CustomWindowManager();
		Windowmanager.setBounds(0,0,frame.getWidth(),frame.getHeight());
		Windowmanager.setVisible(false);

		frame.getContentPane().add(Windowmanager);

		Windowmanager.setVisible(true);

	}

	public static CustomWindowManager getWindowManager() {
		return Windowmanager;
	}

	public static JFrame getFrame() {
		return frame;
	}
	
	public static Dimension getMaxDimension() {
		return new Dimension(800,800);// frame.getGraphicsConfiguration().getBounds().getSize();
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

}
