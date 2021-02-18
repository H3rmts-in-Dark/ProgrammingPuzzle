package start;


import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import Programming.Interpreter;
import abstractclasses.CustomWindow;
import frame.Frame;
import logic.MainControl;
import world.Images;


public class start {

	/**
	 * Die Tatsächliche main Methode
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Interpreter.interpret();
		if (1 == 1)
			return;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainControl.initialize();
				Frame.init();

				MainControl.start();
				Frame.getFrame().setVisible(true);

				MainControl.createProgrammingWindow();

				MainControl.createWorld();

				new CustomWindow(300,300,new Point(30,30),"Jan Kaufer",0) {

					@Override
					public BufferedImage getImage() {
						return Images.getImage("jan.jpg");
					}

				};
				new CustomWindow(300,300,new Point(30,30),"Fynn Stroot",0) {

					@Override
					public BufferedImage getImage() {
						return Images.getImage("fynn.JPG");
					}

				};
				new CustomWindow(300,300,new Point(30,30),"Enrico Stemmer",0) {

					@Override
					public BufferedImage getImage() {
						return Images.getImage("enrico.jpeg");
					}

				};
			}

		});

	}

}
