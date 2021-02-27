package world;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import abstractclasses.CustomWindow;
import logic.MainControl;


public class WorldSelectionWindow extends CustomWindow {

	JTabbedPane TabbedPane;

	private ArrayList<Tab> Tabs;

	public WorldSelectionWindow() {
		super(600,500,new Point(20,20),"WorldSelectionWindow",1,false);

		setResizable(false);

		TabbedPane = new JTabbedPane();
		TabbedPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
		TabbedPane.setToolTipText("");

		Tabs = new ArrayList<>();

		Tab tab = new Tab("Factory");
		Tabs.add(tab);
		TabbedPane.addTab(tab.name,tab);

		tab = new Tab("Laboratory");
		Tabs.add(tab);
		TabbedPane.addTab(tab.name,tab);

		GroupLayout jInternalFrame1Layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(jInternalFrame1Layout);
		jInternalFrame1Layout
			.setHorizontalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(TabbedPane,GroupLayout.DEFAULT_SIZE,566,Short.MAX_VALUE));

		jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(TabbedPane,GroupLayout.DEFAULT_SIZE,566,Short.MAX_VALUE));
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

}



class Tab extends JPanel {

	String name;

	JLabel nameLabel;
	JButton World1;
	JButton World2;
	JButton World3;
	JButton World4;
	JButton World5;
	JButton World6;
	JButton World7;
	JButton World8;
	JTextArea World1decription;
	JTextArea World2decription;
	JTextArea World3decription;
	JTextArea World4decription;
	JTextArea World5decription;
	JTextArea World6decription;
	JTextArea World7decription;
	JTextArea World8decription;
	ActionListener worldHandler;

	public Tab(String name) {

		this.name = name;

		worldHandler = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Click");
				try {
					try {
						MainControl.deleteWorld();
					} catch (NullPointerException e2) {
					}
					MainControl.setWorld(WorldLoader.getWorld(name + "/" + ((AbstractButton) e.getSource()).getText()));
				} catch (FileNotFoundException e1) {
					System.err.println(e1.getMessage());
				}
			}

		};

		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Tahoma",0,18));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		World1decription = new JTextArea("");
		World1 = new JButton("1");
		World1.addActionListener(worldHandler);

		World2decription = new JTextArea("");
		World2 = new JButton("2");
		World2.addActionListener(worldHandler);

		World3decription = new JTextArea("");
		World3 = new JButton("3");
		World3.addActionListener(worldHandler);

		World4decription = new JTextArea("");
		World4 = new JButton("4");
		World4.addActionListener(worldHandler);

		World5decription = new JTextArea("");
		World5 = new JButton("5");
		World5.addActionListener(worldHandler);

		World6decription = new JTextArea("");
		World6 = new JButton("6");
		World6.addActionListener(worldHandler);

		World7decription = new JTextArea("");
		World7 = new JButton("7");
		World7.addActionListener(worldHandler);

		World8decription = new JTextArea("");
		World8 = new JButton("8");
		World8.addActionListener(worldHandler);

		GroupLayout jPanel5Layout = new GroupLayout(this);
		setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
				.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(nameLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
					.addGroup(jPanel5Layout.createSequentialGroup().addGroup(
						jPanel5Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(jPanel5Layout.createSequentialGroup().addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World1,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
									.addComponent(World1decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World2,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
									.addComponent(World2decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World3,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
									.addComponent(World3decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,
									false)
									.addComponent(World4,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(World4decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18))
							.addGroup(jPanel5Layout.createSequentialGroup().addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World5,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
									.addComponent(World5decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World6,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
									.addComponent(World6decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World7decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(World7,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE))
								.addGap(18,18,18)
								.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
									.addComponent(World8decription,GroupLayout.PREFERRED_SIZE,120,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(World8,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE))))
						.addContainerGap()))));

		jPanel5Layout
			.setVerticalGroup(
				jPanel5Layout
					.createParallelGroup(
						GroupLayout.Alignment.LEADING)
					.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
						.addComponent(nameLabel,GroupLayout.PREFERRED_SIZE,30,GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(World1,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
							.addComponent(World2,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
							.addComponent(World3,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
							.addComponent(World4,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING,false)
							.addComponent(World1decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
							.addComponent(World2decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
							.addComponent(World3decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
							.addComponent(World4decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE))
						.addGap(18,18,18)
						.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(World5,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
							.addComponent(World6,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
							.addComponent(World7,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
							.addComponent(World8,GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE,false)
							.addComponent(World5decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
							.addComponent(World6decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
							.addComponent(World7decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
							.addComponent(World8decription,GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE))
						.addContainerGap(15,Short.MAX_VALUE)));
	}

}
