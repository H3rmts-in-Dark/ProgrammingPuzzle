package frame;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class ClassJFrame extends JFrame{

	public static int height = 400, width = 600;
	
	private JLayeredPane mainmenu; //mainpane
	
	private JLayeredPane level; //mainpane
	
	private JLayeredPane pause; //all on level
	private JLayeredPane running; //all on level
	private JLayeredPane programming; //all on level

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
	
	void loadMainmenuPane() {
		mainmenu = new JLayeredPane();
		mainmenu.setBounds(0,0,width,height);
		mainmenu.setVisible(false);
	}
	
	void loadlevelPane() {
		
		level = new JLayeredPane();
		level.setBounds(0,0,width,height);
		level.setVisible(false);
		
			pause = new JLayeredPane();
			pause.setBounds(0,0,level.getWidth(),level.getHeight());
			pause.setVisible(false);
			level.add(pause,JLayeredPane.PALETTE_LAYER);
			
			running = new JLayeredPane();
			running.setBounds(0,0,width,height);
			running.setVisible(false);
			level.add(running,JLayeredPane.PALETTE_LAYER);
			
			programming = new JLayeredPane();
			programming.setBounds(0,0,width,height);
			programming.setVisible(false);
			level.add(programming,JLayeredPane.PALETTE_LAYER);
			
	}
}