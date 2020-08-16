package frame;

public class ClassJFrame {

	private static javax.swing.JFrame frame;
	public static int height = 400, width = 600;

	public static void start() {
		frame = new javax.swing.JFrame("ProgrammingPuzzle");
		frame.setSize(width, height);
		frame.setResizable(false); // TODO Muss noch eingefügt werden
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(kl);
		frame.addMouseListener(ml);
	}

	private static java.awt.event.KeyListener kl = new java.awt.event.KeyListener() {
		// TODO Fertig machen
		@Override
		public void keyPressed(java.awt.event.KeyEvent arg0) {
		}

		@Override
		public void keyReleased(java.awt.event.KeyEvent arg0) {
		}

		@Override
		public void keyTyped(java.awt.event.KeyEvent arg0) {
		}
	};

	private static java.awt.event.MouseListener ml = new java.awt.event.MouseListener() {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent arg0) {
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent arg0) {
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent arg0) {
		}

	};
}
