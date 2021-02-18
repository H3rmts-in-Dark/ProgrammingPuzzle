package frame;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
		frame.setLocation(0, 0);
		frame.setSize(getMaxDimension());
		frame.setFocusable(true);
		frame.setLayout(null);
		frame.setResizable(false);

		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Windowmanager = new CustomWindowManager();
		Windowmanager.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		Windowmanager.setVisible(false);

		frame.getContentPane().add(Windowmanager);

		Windowmanager.setVisible(true);
		/*
		 * Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
		 * 
		 * @Override public void eventDispatched(AWTEvent e) { if (e instanceof KeyEvent
		 * && e.getID() == KeyEvent.KEY_RELEASED) {
		 * UserInputInterpreter.keyPressed((KeyEvent) e); } }
		 * 
		 * },AWTEvent.KEY_EVENT_MASK);
		 */
		// Metal j, Nimbus, Windows,

		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}

	}

	public static CustomWindowManager getWindowManager() {
		return Windowmanager;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static Dimension getMaxDimension() {
		return new Dimension(1400, 1400); // frame.getGraphicsConfiguration().getBounds().getSize();
	}

	public static int getWidth() {
		return frame.getWidth();
	}

	public static int getHeight() {
		return frame.getHeight();
	}

}
