package Programming;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;

import abstractclasses.CustomWindow;
import world.World;


public class ProgrammingWindow extends CustomWindow {

	private World world;

	private JButton Run;
	private JButton Stop;
	private JCheckBoxMenuItem MenuBarPreferencesItem2;
	private JCheckBoxMenuItem MenuBarPreferencesItem3;
	private JLabel jLabel1;

	private JMenuBar MenuBar;
	private JMenu MenuBarOther;
	private JMenu MenuBarPreferences;
	private JMenu MenuBarFile;
	private JMenu MenuBarEdit;
	private JMenuItem MenuBarFileItem2;
	private JMenuItem MenuBarOtherItem2;
	private JMenuItem MenuBarOtherItem3;
	private JMenuItem MenuBarPreferencesItem1;
	private JMenuItem MenuBarOtherItem1;
	private JMenuItem MenuBarFileItem1;
	private JMenuItem MenuBarFileItem3;
	private JMenuItem MenuBarFileItem4;
	private JMenuItem MenuBarEditItem1;
	private JMenuItem MenuBarEditItem2;
	private JMenuItem MenuBarEditItem3;
	private JMenuItem MenuBarEditItem4;
	private JMenuItem MenuBarEditItem5;

	private JPanel Tab1;
	private JPanel ProgrammingPanel;
	private JPanel OutputPanel;
	private JScrollPane ProgrammingPanelScrollPane;
	private JScrollPane jScrollPane2;
	private Separator Separator1;
	private Separator Separator2;
	private Separator Separator3;
	private JTabbedPane TabbedPane;
	private JTextPane ProgrammingPane;
	private JTextPane OutputTextPane;
	private JTextPane LinecounterPane;

	public ProgrammingWindow(World world) {
		super(700,700,new Point(40,40),"Programming Window",0,false);
		this.world = world;

		GenerateProgrammingPane();

		GenerateOutputPanel();

		GenerateMenuBar();

		GroupLayout jInternalFrame1Layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(jInternalFrame1Layout);
		jInternalFrame1Layout.setHorizontalGroup(
				jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(TabbedPane)
						.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE));
		jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jInternalFrame1Layout.createSequentialGroup()
						.addComponent(TabbedPane,GroupLayout.PREFERRED_SIZE,457,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)));

		pack();

	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	public World getWorld() {
		return world;
	}

	private void GenerateProgrammingPane() {
		TabbedPane = new JTabbedPane();
		TabbedPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
		TabbedPane.setToolTipText("");

		ProgrammingPane = new JTextPane();
		ProgrammingPane.setEditable(true);

		LinecounterPane = new JTextPane();
		LinecounterPane.setEditable(false);

		ProgrammingPanel = new JPanel();
		GroupLayout ProgrammingPanelLayout = new GroupLayout(ProgrammingPanel);
		ProgrammingPanel.setLayout(ProgrammingPanelLayout);

		ProgrammingPanelLayout.setHorizontalGroup(ProgrammingPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
						ProgrammingPanelLayout.createSequentialGroup().addGap(2,2,2)
								.addComponent(LinecounterPane,GroupLayout.PREFERRED_SIZE,43,GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(ProgrammingPane,GroupLayout.DEFAULT_SIZE,500,Short.MAX_VALUE)));

		ProgrammingPanelLayout.setVerticalGroup(ProgrammingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(ProgrammingPane,GroupLayout.DEFAULT_SIZE,100,Short.MAX_VALUE).addComponent(LinecounterPane));

		ProgrammingPanelScrollPane = new JScrollPane(ProgrammingPanel);
		ProgrammingPanelScrollPane.setToolTipText("");
		ProgrammingPanelScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
		ProgrammingPanelScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ProgrammingPanelScrollPane.setHorizontalScrollBar(null);

		Tab1 = new JPanel();
		GroupLayout Tab1Layout = new GroupLayout(Tab1);
		Tab1.setLayout(Tab1Layout);

		Tab1Layout.setHorizontalGroup(Tab1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(Tab1Layout.createSequentialGroup().addComponent(ProgrammingPanelScrollPane)));

		Tab1Layout.setVerticalGroup(Tab1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(Tab1Layout.createSequentialGroup()
						.addComponent(ProgrammingPanelScrollPane,GroupLayout.DEFAULT_SIZE,427,Short.MAX_VALUE)
						.addGap(0,0,0)));

		TabbedPane.addTab("tab",Tab1);
	}

	private void GenerateOutputPanel() {
		Run = new JButton("Run");

		Stop = new JButton("Stop");

		OutputTextPane = new JTextPane();
		OutputTextPane.setEditable(false);
		OutputTextPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(OutputTextPane);

		jLabel1 = new JLabel();
		jLabel1.setFont(new Font("Arial",1,16));
		jLabel1.setText("Output");

		OutputPanel = new JPanel();
		OutputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,false));

		GroupLayout OutputPanelLayout = new GroupLayout(OutputPanel);
		OutputPanel.setLayout(OutputPanelLayout);

		OutputPanelLayout
				.setHorizontalGroup(
						OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(OutputPanelLayout.createSequentialGroup().addContainerGap()
										.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(jLabel1,GroupLayout.PREFERRED_SIZE,82,GroupLayout.PREFERRED_SIZE)
												.addGroup(OutputPanelLayout.createSequentialGroup()
														.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addComponent(Run,GroupLayout.PREFERRED_SIZE,53,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(Stop))
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jScrollPane2)))
										.addContainerGap()));
		OutputPanelLayout.setVerticalGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(OutputPanelLayout.createSequentialGroup()
						.addComponent(jLabel1,GroupLayout.PREFERRED_SIZE,22,GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(OutputPanelLayout.createSequentialGroup().addComponent(Run)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(Stop))
								.addComponent(jScrollPane2,GroupLayout.PREFERRED_SIZE,151,GroupLayout.PREFERRED_SIZE))
						.addContainerGap(24,Short.MAX_VALUE)));
	}

	private void GenerateMenuBar() {
		MenuBar = new JMenuBar();

		MenuBarFile = new JMenu();
		MenuBarFile.setText("File");

		MenuBarFileItem1 = new JMenuItem();
		MenuBarFileItem1.setText("new File");
		MenuBarFile.add(MenuBarFileItem1);

		MenuBarFileItem2 = new JMenuItem();
		MenuBarFileItem2.setText("open File");
		MenuBarFile.add(MenuBarFileItem2);

		MenuBarFileItem3 = new JMenuItem();
		MenuBarFileItem3.setText("save File");
		MenuBarFile.add(MenuBarFileItem3);

		MenuBarFileItem4 = new JMenuItem();
		MenuBarFileItem4.setText("save File as");
		MenuBarFile.add(MenuBarFileItem4);

		MenuBar.add(MenuBarFile);

		MenuBarEdit = new JMenu();
		MenuBarEdit.setText("Edit");

		MenuBarEditItem1 = new JMenuItem();
		MenuBarEditItem1.setText("Undo");
		MenuBarEdit.add(MenuBarEditItem1);

		MenuBarEditItem2 = new JMenuItem();
		MenuBarEditItem2.setText("Redo");
		MenuBarEdit.add(MenuBarEditItem2);

		Separator1 = new JPopupMenu.Separator();
		MenuBarEdit.add(Separator1);

		MenuBarEditItem3 = new JMenuItem();
		MenuBarEditItem3.setText("cut");
		MenuBarEdit.add(MenuBarEditItem3);

		MenuBarEditItem4 = new JMenuItem();
		MenuBarEditItem4.setText("copy");
		MenuBarEdit.add(MenuBarEditItem4);

		MenuBarEditItem5 = new JMenuItem();
		MenuBarEditItem5.setText("paste");
		MenuBarEdit.add(MenuBarEditItem5);

		MenuBar.add(MenuBarEdit);

		MenuBarPreferences = new JMenu();
		MenuBarPreferences.setText("Preferences");

		MenuBarPreferencesItem1 = new JMenuItem();
		MenuBarPreferencesItem1.setText("Theme");
		MenuBarPreferences.add(MenuBarPreferencesItem1);

		Separator2 = new JPopupMenu.Separator();
		MenuBarPreferences.add(Separator2);

		MenuBarPreferencesItem2 = new JCheckBoxMenuItem();
		MenuBarPreferencesItem2.setSelected(true);
		MenuBarPreferencesItem2.setText("auto complettion");
		MenuBarPreferences.add(MenuBarPreferencesItem2);

		MenuBarPreferencesItem3 = new JCheckBoxMenuItem();
		MenuBarPreferencesItem3.setSelected(true);
		MenuBarPreferencesItem3.setText("show errors");
		MenuBarPreferences.add(MenuBarPreferencesItem3);

		MenuBar.add(MenuBarPreferences);

		MenuBarOther = new JMenu();
		MenuBarOther.setText("Other");

		MenuBarOtherItem1 = new JMenuItem();
		MenuBarOtherItem1.setText("Run");
		MenuBarOther.add(MenuBarOtherItem1);

		Separator3 = new Separator();
		MenuBarOther.add(Separator3);

		MenuBarOtherItem2 = new JMenuItem();
		MenuBarOtherItem2.setText("go to");
		MenuBarOther.add(MenuBarOtherItem2);

		MenuBarOtherItem3 = new JMenuItem();
		MenuBarOtherItem3.setText("find");
		MenuBarOther.add(MenuBarOtherItem3);

		MenuBar.add(MenuBarOther);

		setJMenuBar(MenuBar);
	}

}
