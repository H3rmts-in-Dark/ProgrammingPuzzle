package frame;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import ListenersandHandlers.MainmenuListener;
import logic.Statemanager.States;

public class ClassJFrame extends JFrame{

	public static int height = 400, width = 600;
	
	private JLayeredPane mainmenupane; //mainpane
	private Mainmenubackground mainmenubackground;
	public JButton mainmenustartButton;
	
	
	private JLayeredPane levelpane; //mainpane
	private WorldLabel worldlabel;
	private JLayeredPane pause; //all on level
	private JLayeredPane running; //all on level
	private JLayeredPane programming; //all on level


	public ClassJFrame() {
		super("Programming Puzzle");
		setSize(width,height);
		setLayout(null);
		setResizable(false);  // TODO Muss noch eingefügt werden
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());
		
		loadlevelPane();
		loadMainmenuPane();
		
		setSize(width + 22,height + 45);
		repaint();
		setVisible(true);
	}	
	
	void loadMainmenuPane() {
		mainmenupane = new JLayeredPane();
		mainmenupane.setBounds(0,0,width,height);
		mainmenupane.setVisible(false);
		
			mainmenubackground = new Mainmenubackground();
			mainmenubackground.setBounds(0,0,mainmenupane.getWidth(),mainmenupane.getHeight());
			mainmenupane.add(mainmenubackground,JLayeredPane.DEFAULT_LAYER);
			
			mainmenustartButton = new JButton("start");
			mainmenustartButton.setBackground(Color.DARK_GRAY);
			mainmenustartButton.setForeground(Color.BLACK);
			mainmenustartButton.addActionListener(new MainmenuListener());
			mainmenustartButton.setFocusPainted(false);
			mainmenustartButton.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(4)));
			mainmenustartButton.setUI(CustomUIs.getBasicToggleButtonUI());
			mainmenustartButton.setBounds((mainmenupane.getWidth() / 2) - 50,(mainmenupane.getHeight() / 2) - 25,100,50);
			mainmenupane.add(mainmenustartButton,JLayeredPane.PALETTE_LAYER);
		
		getContentPane().add(mainmenupane);
	}
	
	void loadlevelPane() {
		
		levelpane = new JLayeredPane();
		levelpane.setBounds(0,0,width,height);
		levelpane.setVisible(false);
		
			worldlabel = new WorldLabel();
			worldlabel.setBounds(50,50,levelpane.getWidth()-100,levelpane.getHeight()-100);
			levelpane.add(worldlabel,JLayeredPane.DEFAULT_LAYER);
		
			pause = new JLayeredPane();
			pause.setBounds(0,0,levelpane.getWidth(),levelpane.getHeight());
			pause.setVisible(false);
			levelpane.add(pause,JLayeredPane.PALETTE_LAYER);
			
			running = new JLayeredPane();
			running.setBounds(0,0,width,height);
			running.setVisible(false);
			levelpane.add(running,JLayeredPane.PALETTE_LAYER);
			
			programming = new JLayeredPane();
			programming.setBounds(0,0,width,height);
			programming.setVisible(false);
			levelpane.add(programming,JLayeredPane.PALETTE_LAYER);
			
		
		getContentPane().add(levelpane);
	}
	
	public void setState(States newstate) {
		System.out.println("frame switched to " + newstate);
		switch (newstate) {
		case mainmenu:
			levelpane.setVisible(false);
			mainmenupane.setVisible(true);
			break;
		case pause:
			mainmenupane.setVisible(false);
			running.setVisible(false);
			programming.setVisible(false);
			levelpane.setVisible(true);
			pause.setVisible(true);
			break;
		case programming:
			mainmenupane.setVisible(false);
			running.setVisible(false);
			pause.setVisible(false);
			levelpane.setVisible(true);
			programming.setVisible(true);
			break;
		case running:
			mainmenupane.setVisible(false);
			pause.setVisible(false);
			programming.setVisible(false);
			levelpane.setVisible(true);
			running.setVisible(true);
			break;
		}
	}
}