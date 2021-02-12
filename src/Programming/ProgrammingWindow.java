package Programming;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;

import abstractclasses.CustomWindow;
import world.World;

public class ProgrammingWindow extends CustomWindow {
	
	@SuppressWarnings("unused")
	private World world;
	
	private JLayeredPane label;
	
	public ProgrammingWindow(World world) {
		super(700,700,new Point(40,40),"Programming Window",0);
		this.world = world;
		
		label = new JLayeredPane();
		setComponentBounds(0,0,getImageborders().width,getImageborders().height,label);
		add(label,JLayeredPane.MODAL_LAYER);
		
		JComponent bg = new JComponent() {
			@Override
			public void paint(Graphics g) {
				g.setColor(Color.BLACK);
				g.fillRect(0,0,getWidth(),getHeight());
			}
		};
		bg.setBounds(0,0,label.getWidth(),label.getHeight());
		
		label.add(bg,JLayeredPane.DEFAULT_LAYER);
		
		JInternalFrame frame;
		
		JButton but = new JButton("txt");
		but.setBounds(2,2,100,100);
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("test");
			}
		});
		but.setFocusPainted(false);
		but.setRolloverEnabled(false);
		
		label.add(but,JLayeredPane.MODAL_LAYER);
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

}
