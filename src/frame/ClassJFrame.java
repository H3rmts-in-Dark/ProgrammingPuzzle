package frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import logic.Statemanager.States;

@SuppressWarnings("serial")
public class ClassJFrame extends JFrame {

	public static int height = 400, width = 600;

	private JLayeredPane mainMenu; // mainpane

	public JButton mainMenuStartButton;

	private JLayeredPane level; // mainpane

	private WorldLabel worldlabel;
	private JLayeredPane pause; // all on level
	private JLayeredPane running; // all on level
	private JLayeredPane programming; // all on level
	// Bitte Kommentiere deinen Code klarer, was meinst du mit "all on level"? -Jan

	// Warum sind alle JLayerdPanes' auf der JLayeredPane level? Sollten die nicht
	// auf this sein? -Jan

	public ClassJFrame() {
		super("Programming Puzzle");
		setSize(width, height);
		setResizable(false); // TODO Muss noch eingefügt werden
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());

		// TODO init other labels
		loadlevelPane();
		loadMainmenuPane();

		repaint();
		setVisible(true);
	}

	private void loadMainmenuPane() {
		mainMenu = new JLayeredPane();
		mainMenu.setBounds(0, 0, width, height);
		mainMenu.setVisible(false);

		mainMenuStartButton = new JButton("start");
		mainMenuStartButton.setFocusPainted(false);
		mainMenuStartButton.setBounds((mainMenu.getWidth() / 2) - 50, (mainMenu.getHeight() / 2) - 25, 100, 50);
		// TODO die übrigen Koordinaten (-50, -25, 100, 50) noch relativ zur größe
		// machen, da die sonst bei zu kleiner Fenstergröße das gesamte Fenster
		// einnehmen könnten bzw. Außerhalb des Fensters sein könnten -Jan
		mainMenu.add(mainMenuStartButton, JLayeredPane.PALETTE_LAYER);
	}

	private void loadlevelPane() {
		level = new JLayeredPane();
		level.setBounds(0, 0, width, height);
		level.setVisible(false);

		worldlabel = new WorldLabel();
		worldlabel.setBounds(50, 50, level.getWidth() - 100, level.getHeight() - 100);
		worldlabel.setVisible(true);
		level.add(worldlabel, JLayeredPane.DEFAULT_LAYER);

		pause = new JLayeredPane();
		pause.setBounds(0, 0, level.getWidth(), level.getHeight());
		pause.setVisible(false);
		level.add(pause, JLayeredPane.PALETTE_LAYER);

		running = new JLayeredPane();
		running.setBounds(0, 0, width, height);
		running.setVisible(false);
		level.add(running, JLayeredPane.PALETTE_LAYER);

		programming = new JLayeredPane();
		programming.setBounds(0, 0, width, height);
		programming.setVisible(false);
		level.add(programming, JLayeredPane.PALETTE_LAYER);
	}

	public void setState(States newstate) {
		System.out.println("gameFrame switched State to: " + newstate);
		level.setVisible(false);
		mainMenu.setVisible(false);
		running.setVisible(false);
		programming.setVisible(false);
		pause.setVisible(false);
		switch (newstate) {
		case mainmenu:
			mainMenu.setVisible(true);
			break;
		case pause:
			level.setVisible(true);
			pause.setVisible(true);
			break;
		case programming:
			level.setVisible(true);
			programming.setVisible(true);
			break;
		case running:
			level.setVisible(true);
			running.setVisible(true);
			break;
		}
	}
}