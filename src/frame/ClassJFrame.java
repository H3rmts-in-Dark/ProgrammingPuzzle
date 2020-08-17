package frame;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class ClassJFrame extends JFrame{

	public static int height = 400, width = 600;
	
	private JLayeredPane mainmenu;
	private JLayeredPane pause;
	private JLayeredPane running;
	private JLayeredPane programming;

	public ClassJFrame() {
		super("Programming Puzzle");
		setSize(width, height);
		setResizable(false);  // TODO Muss noch eingefügt werden
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());
		
		// TODO init other labels
		loadMainmenu();
		
		setVisible(true);
	}	
	
	void loadMainmenu() {
		mainmenu = new JLayeredPane();
		mainmenu.setBounds(0,0,width,height);
		mainmenu.setVisible(false);
	}
	
	void loadpause() {
		pause = new JLayeredPane();
		pause.setBounds(0,0,width,height);
		pause.setVisible(false);
	}
	
	void loadrunning() {
		running = new JLayeredPane();
		running.setBounds(0,0,width,height);
		running.setVisible(false);
	}
	
	void loadprogramming() {
		programming = new JLayeredPane();
		programming.setBounds(0,0,width,height);
		programming.setVisible(false);
	}
	
	
	public JLayeredPane getMainmenu() {
		return mainmenu;
	}
	public JLayeredPane getPause() {
		return pause;
	}
	public JLayeredPane getRunning() {
		return running;
	}
	public JLayeredPane getProgramming() {
		return programming;
	}
}