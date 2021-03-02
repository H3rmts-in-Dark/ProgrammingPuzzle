package world;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
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

	/**
	 * Namen hier hinzufügen, damit der Ordner mit den Welten in "rsc/worlds/" ausgelesen wird
	 */
	private final String[] tabs = {"Testing","Factory","Laboratory"};

	JTabbedPane TabbedPane;

	private ArrayList<Tab> Tabs;

	public WorldSelectionWindow() {
		super(600,500,new Point(20,20),"WorldSelectionWindow",0,false);

		setResizable(false);

		TabbedPane = new JTabbedPane();
		TabbedPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
		TabbedPane.setToolTipText("");

		Tabs = new ArrayList<>();
		for (String tabname : tabs) {
			Tab tab = new Tab(tabname,this);
			Tabs.add(tab);
			TabbedPane.addTab(tab.name,tab);
		}

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
	JButton[] worldbuttons = new JButton[8];
	JTextArea[] worlddescriptions = new JTextArea[8];
	ActionListener worldHandler;

	public Tab(String name,WorldSelectionWindow window) {

		this.name = name;

		worldHandler = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						MainControl.deleteWorld();
					} catch (NullPointerException e2) {
					}
					MainControl.setWorld(WorldLoader.getWorld(name + "/" + ((AbstractButton) e.getSource()).getText()));
					window.setIcon(true);
				} catch (FileNotFoundException | PropertyVetoException e1) {
					System.err.println(e1.getMessage());
				}
			}

		};

		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("Tahoma",0,18));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 1; i <= worldbuttons.length; i++) {
			worlddescriptions[i - 1] = new JTextArea(WorldLoader.getDescription(name + "/" + i));
			worldbuttons[i - 1] = new JButton(i + "");
			worldbuttons[i - 1].setIcon(WorldLoader.getIcon(name + "/" + i));
			worldbuttons[i - 1].addActionListener(worldHandler);
		}

		GroupLayout jPanel5Layout = new GroupLayout(this);
		setLayout(jPanel5Layout);
		jPanel5Layout
			.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
					.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
						.addGroup(jPanel5Layout.createSequentialGroup()
							.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(jPanel5Layout.createSequentialGroup().addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worldbuttons[0],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worlddescriptions[0],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worldbuttons[1],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worlddescriptions[1],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worldbuttons[2],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worlddescriptions[2],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worldbuttons[3],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worlddescriptions[3],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18))
								.addGroup(jPanel5Layout.createSequentialGroup().addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worldbuttons[4],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worlddescriptions[4],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worldbuttons[5],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worlddescriptions[5],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worlddescriptions[6],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worldbuttons[6],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))
									.addGap(18,18,18)
									.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
										.addComponent(worlddescriptions[7],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(worldbuttons[7],GroupLayout.PREFERRED_SIZE,120,
											GroupLayout.PREFERRED_SIZE))))
							.addContainerGap()))));

		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
				.addComponent(nameLabel,GroupLayout.PREFERRED_SIZE,30,GroupLayout.PREFERRED_SIZE)
				.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(worldbuttons[0],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
					.addComponent(worldbuttons[1],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
					.addComponent(worldbuttons[2],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
					.addComponent(worldbuttons[3],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING,false)
					.addComponent(worlddescriptions[0],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
					.addComponent(worlddescriptions[1],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
					.addComponent(worlddescriptions[2],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
					.addComponent(worlddescriptions[3],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE))
				.addGap(18,18,18)
				.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(worldbuttons[4],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
					.addComponent(worldbuttons[5],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
					.addComponent(worldbuttons[6],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE)
					.addComponent(worldbuttons[7],GroupLayout.PREFERRED_SIZE,120,GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE,false)
					.addComponent(worlddescriptions[4],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
					.addComponent(worlddescriptions[5],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
					.addComponent(worlddescriptions[6],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE)
					.addComponent(worlddescriptions[7],GroupLayout.PREFERRED_SIZE,60,Short.MAX_VALUE))
				.addContainerGap(15,Short.MAX_VALUE)));
	}

}
