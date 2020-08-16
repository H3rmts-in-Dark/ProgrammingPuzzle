package frame;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class ClassJFrame extends JFrame{

	public static int height = 400, width = 600;
	
	private JLayeredPane mainmenu;

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

	
	public JLayeredPane getMainmenu() {
		return mainmenu;
	}
}