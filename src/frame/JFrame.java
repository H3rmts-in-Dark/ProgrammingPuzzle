package frame;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

import listenersAndHandlers.MainmenuListener;
import logic.Statemanager.States;

public class JFrame extends javax.swing.JFrame {

	public static int height = 400, width = 600;

	private JLayeredPane mainMenuPane;
	private MainMenuBackground mainMenuBackground;
	public JButton mainMenuStartButton;

	private WorldLabel worldlabel;
	private JLayeredPane levelpane, pause, running, programming;

	public JFrame() {
		super("ProgrammingPuzzle");
		setSize(width, height);
		setLayout(null);
		setResizable(false);
		// TODO Die Funktion, dass das Fenster die größe ändern kann, sollte noch
		// eingefügt werden
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());

		loadlevelPane();
		loadMainmenuPane();

		setSize(width + 22, height + 45);

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
		levelpane.setBounds(0, 0, width, height);
		levelpane.setVisible(false);

		worldlabel = new WorldLabel();
		worldlabel.setBounds(50, 50, levelpane.getWidth() - 100, levelpane.getHeight() - 100);
		levelpane.add(worldlabel, JLayeredPane.DEFAULT_LAYER);

		pause = new JLayeredPane();
		pause.setBounds(0, 0, levelpane.getWidth(), levelpane.getHeight());
		pause.setVisible(false);
		levelpane.add(pause, JLayeredPane.PALETTE_LAYER);

		running = new JLayeredPane();
		running.setBounds(0, 0, width, height);
		running.setVisible(false);
		levelpane.add(running, JLayeredPane.PALETTE_LAYER);

		programming = new JLayeredPane();
		programming.setBounds(0, 0, width, height);
		programming.setVisible(false);
		levelpane.add(programming, JLayeredPane.PALETTE_LAYER);

		getContentPane().add(levelpane);
	}

	public void setState(States newstate) {
		System.out.println("frame switched to " + newstate);
		mainMenuPane.setVisible(false);
		levelpane.setVisible(false);
		pause.setVisible(false);
		running.setVisible(false);
		programming.setVisible(false);
		switch (newstate) {
		case mainmenu:
			mainMenuPane.setVisible(true);
			break;
		case pause:
			levelpane.setVisible(true);
			pause.setVisible(true);
			break;
		case programming:
			levelpane.setVisible(true);
			programming.setVisible(true);
			break;
		case running:
			levelpane.setVisible(true);
			running.setVisible(true);
			break;
		}
	}
}