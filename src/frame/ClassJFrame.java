package frame;

import javax.swing.JFrame;

public class ClassJFrame extends JFrame{

	public static int height = 400, width = 600;

	public ClassJFrame() {
		super("Progtamming Puzzle");
		setSize(width, height);
		setResizable(false);  // TODO Muss noch eingefügt werden
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());
		
		// TODO init other labels
		
		setVisible(true);
	}
}