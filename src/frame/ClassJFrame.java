package frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import logic.Statemanager.States;

public class ClassJFrame extends JFrame{

	public static int height = 400, width = 600;
	
	private JLayeredPane mainmenu; //mainpane
	
	public JButton mainmenustartButton;
	
	private JLayeredPane level; //mainpane

	private WorldLabel worldlabel;
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
		loadlevelPane();
		loadMainmenuPane();
		
		repaint();
		setVisible(true);
	}	
	
	void loadMainmenuPane() {
		mainmenu = new JLayeredPane();
		mainmenu.setBounds(0,0,width,height);
		mainmenu.setVisible(false);
		
			mainmenustartButton = new JButton("start");
			mainmenustartButton.setFocusPainted(false);
			mainmenustartButton.setBounds((mainmenu.getWidth() / 2) - 50,(mainmenu.getHeight() / 2) - 25,100,50);
			mainmenu.add(mainmenustartButton,JLayeredPane.PALETTE_LAYER);
			
	}
	
	void loadlevelPane() {
		
		level = new JLayeredPane();
		level.setBounds(0,0,width,height);
		level.setVisible(false);
		
			worldlabel = new WorldLabel();
			worldlabel.setBounds(50,50,level.getWidth()-100,level.getHeight()-100);
			worldlabel.setVisible(true);
			level.add(worldlabel,JLayeredPane.DEFAULT_LAYER);
		
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
	
	public void setState(States newstate) {
		System.out.println("frame switched to " + newstate);
		switch (newstate) {
		case mainmenu:
			level.setVisible(false);
			mainmenu.setVisible(true);
			break;
		case pause:
			mainmenu.setVisible(false);
			running.setVisible(false);
			programming.setVisible(false);
			level.setVisible(true);
			pause.setVisible(true);
			break;
		case programming:
			mainmenu.setVisible(false);
			running.setVisible(false);
			pause.setVisible(false);
			level.setVisible(true);
			programming.setVisible(true);
			break;
		case running:
			mainmenu.setVisible(false);
			pause.setVisible(false);
			programming.setVisible(false);
			level.setVisible(true);
			running.setVisible(true);
			break;
		}
	}
}