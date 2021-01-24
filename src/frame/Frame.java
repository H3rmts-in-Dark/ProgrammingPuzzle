package frame;


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
		frame.setSize(FRAMEWIDTH,FRAMEHEIGHT);
		frame.setFocusable(true);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.addKeyListener(new UserInputInterpreter());

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// frame.setLocationRelativeTo(null);

		Windowmanager = new CustomWindowManager();
		Windowmanager.setBounds(0,0,FRAMEWIDTH,FRAMEHEIGHT);
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