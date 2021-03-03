package Programming;


import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import abstractclasses.CustomWindow;
import world.World;


public class ProgrammingWindow extends CustomWindow {

	private World world;

	/* ================================================================= */
	private JMenuBar MenuBar;
	private JMenu MenuBarOther;
	private JMenu MenuBarPreferences;
	private JMenu MenuBarFile;
	private JMenu MenuBarEdit;
	private JMenuItem MenuBarFileItem2;
	private JMenuItem MenuBarOtherItem2;
	private JMenuItem MenuBarOtherItem3;
	private JMenuItem MenuBarOtherItem4;
	private JMenuItem MenuBarPreferencesItem1;
	private JCheckBoxMenuItem MenuBarPreferencesItem2;
	private JCheckBoxMenuItem MenuBarPreferencesItem3;
	private JMenuItem MenuBarFileItem1;
	private JMenuItem MenuBarFileItem3;
	private JMenuItem MenuBarFileItem4;
	/* ================================================================= */
	private JTabbedPane TabbedPane;

	private ArrayList<Tab> Tabs;

	private JPanel OutputPanel;
	private JLabel OutputLabel;
	private JLabel RunningLabel;
	private JButton Run;
	private JButton Stop;
	private JTabbedPane OutputTabbedPane;
	private JScrollPane OutputTextfPaneScrollPane;
	private JTextPane OutputTextPane;
	private JScrollPane ConsoleTextfPaneScrollPane;
	private JTextPane ConsoleTextPane;
	/* ================================================================= */

	public ProgrammingWindow(World world) {
		super(700,700,new Point(400,40),"Programming Window",1,false);
		this.world = world;

		GenerateProgrammingPane(this);

		GroupLayout jInternalFrame1Layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(jInternalFrame1Layout);
		jInternalFrame1Layout
			.setHorizontalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(TabbedPane,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE));
		jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(jInternalFrame1Layout.createSequentialGroup()
				.addComponent(TabbedPane,GroupLayout.PREFERRED_SIZE,457,GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,300,GroupLayout.PREFERRED_SIZE)));

		try {
			Interpreter.initmethods(world.getPlayer(),OutputTextPane,ConsoleTextPane);
			Interpreter.clear();
		} catch (UnsupportetVariableNameExeption | UnsupportetMethodNameExeption | InvalidValueException
			| WrongTypeException e) {
			System.err.println("init gone wrong (some idiot added duplicate metods or attributes):" + e.getMessage());
			return;
		}

	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	public World getWorld() {
		return world;
	}

	public JTextPane getOutputTextPane() {
		return OutputTextPane;
	}

	public JTextPane getConsoleTextPane() {
		return ConsoleTextPane;
	}

	private void GenerateProgrammingPane(ProgrammingWindow window) {
		TabbedPane = new JTabbedPane();
		TabbedPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));

		TabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Tabs.get(TabbedPane.getSelectedIndex()).updateBars(window);
			}

		});

		Tabs = new ArrayList<>();

		addTab("clear");
	}

	public Tab addTab(String name) {
		Tab addTab = new Tab(this);
		Tabs.add(addTab);

		TabbedPane.addTab(name,addTab);
		TabbedPane.setSelectedComponent(addTab);
		return addTab;
	}

	public void deleteTab(Tab tab) {
		Tabs.remove(tab);
		TabbedPane.remove(tab);
	}

	public void GenerateMenuBar(Actionlist actions,Tab tab) {
		MenuBar = new JMenuBar();

		MenuBarFile = new JMenu();
		MenuBarFile.setText("File");

		MenuBarFileItem1 = new JMenuItem();
		MenuBarFileItem1.setText("New File");
		addListener(MenuBarFileItem1,this,tab);
		MenuBarFile.add(MenuBarFileItem1);

		MenuBarFileItem2 = new JMenuItem();
		MenuBarFileItem2.setText("Open File");
		addListener(MenuBarFileItem2,this,tab);
		MenuBarFile.add(MenuBarFileItem2);

		MenuBarFileItem3 = new JMenuItem();
		MenuBarFileItem3.setText("Close Tab");
		addListener(MenuBarFileItem3,this,tab);
		MenuBarFile.add(MenuBarFileItem3);

		MenuBarFileItem4 = new JMenuItem();
		MenuBarFileItem4.setText("Save File as");
		addListener(MenuBarFileItem4,this,tab);
		MenuBarFile.add(MenuBarFileItem4);

		MenuBar.add(MenuBarFile);

		MenuBarEdit = new JMenu("Edit");

		actions.add("Undo",MenuBarEdit);
		actions.add("Redo",MenuBarEdit);

		MenuBarEdit.addSeparator();

		actions.add(DefaultEditorKit.cutAction,MenuBarEdit);
		actions.add(DefaultEditorKit.copyAction,MenuBarEdit);
		actions.add(DefaultEditorKit.pasteAction,MenuBarEdit);

		MenuBarEdit.addSeparator();

		actions.add(DefaultEditorKit.selectAllAction,MenuBarEdit);

		MenuBar.add(MenuBarEdit);

		MenuBarPreferences = new JMenu();
		MenuBarPreferences.setText("Preferences");

		MenuBarPreferencesItem1 = new JMenuItem();
		MenuBarPreferencesItem1.setText("Theme");
		addListener(MenuBarPreferencesItem1,this,tab);
		MenuBarPreferences.add(MenuBarPreferencesItem1);

		MenuBarPreferences.add(new JPopupMenu.Separator());

		MenuBarPreferencesItem2 = new JCheckBoxMenuItem();
		MenuBarPreferencesItem2.setSelected(true);
		MenuBarPreferencesItem2.setText("Auto complettion");
		addListener(MenuBarPreferencesItem2,this,tab);
		MenuBarPreferences.add(MenuBarPreferencesItem2);

		MenuBarPreferencesItem3 = new JCheckBoxMenuItem();
		MenuBarPreferencesItem3.setSelected(true);
		MenuBarPreferencesItem3.setText("Show errors");
		addListener(MenuBarPreferencesItem3,this,tab);
		MenuBarPreferences.add(MenuBarPreferencesItem3);

		MenuBar.add(MenuBarPreferences);

		MenuBarOther = new JMenu();
		MenuBarOther.setText("Other");

		MenuBarOtherItem2 = new JMenuItem("Stop");
		addListener(MenuBarOtherItem2,this,tab);
		MenuBarOther.add(MenuBarOtherItem2);

		actions.add("Run",MenuBarOther);

		actions.add("Color",MenuBarOther);

		MenuBarOther.add(new Separator());

		MenuBarOtherItem3 = new JMenuItem("Go to");
		addListener(MenuBarOtherItem3,this,tab);
		MenuBarOther.add(MenuBarOtherItem3);

		MenuBarOtherItem4 = new JMenuItem("Find");
		addListener(MenuBarOtherItem4,this,tab);
		MenuBarOther.add(MenuBarOtherItem4);

		MenuBar.add(MenuBarOther);

		setJMenuBar(MenuBar);
	}

	public JTextPane[] GenerateOutputPanel(Actionlist actions,Tab tab) {
		getContentPane().setLayout(null);

		Run = new JButton(actions.get("Run"));
		Run.setFont(new Font("Arial",1,10));

		Stop = new JButton("Stop");
		Stop.setFont(new Font("Arial",1,10));
		addListener(Stop,this,tab);

		OutputTabbedPane = new JTabbedPane();
		OutputTabbedPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		OutputTextPane = new JTextPane();
		OutputTextPane.setEditable(false);
		OutputTextPane.setMargin(new Insets(5,5,5,5));
		OutputTextPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		ConsoleTextPane = new JTextPane();
		ConsoleTextPane.setEditable(false);
		ConsoleTextPane.setMargin(new Insets(5,5,5,5));
		ConsoleTextPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		OutputTextfPaneScrollPane = new JScrollPane(OutputTextPane);
		OutputTabbedPane.addTab("Output",OutputTextfPaneScrollPane);

		ConsoleTextfPaneScrollPane = new JScrollPane(ConsoleTextPane);
		OutputTabbedPane.addTab("Compiler",ConsoleTextfPaneScrollPane);

		OutputLabel = new JLabel("Output");
		OutputLabel.setFont(new Font("Arial",1,16));

		RunningLabel = new JLabel("Stopped");
		getRunningLabel().setForeground(Color.RED);
		getRunningLabel().setFont(new Font("Arial",1,12));

		OutputPanel = new JPanel();
		OutputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,false));

		GroupLayout OutputPanelLayout = new GroupLayout(OutputPanel);
		OutputPanel.setLayout(OutputPanelLayout);

		OutputPanelLayout.setHorizontalGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(OutputPanelLayout.createSequentialGroup().addContainerGap()
				.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(OutputLabel,GroupLayout.PREFERRED_SIZE,82,GroupLayout.PREFERRED_SIZE)
					.addGroup(OutputPanelLayout.createSequentialGroup()
						.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(getRunningLabel(),GroupLayout.PREFERRED_SIZE,53,GroupLayout.PREFERRED_SIZE)
							.addComponent(Run,GroupLayout.PREFERRED_SIZE,53,GroupLayout.PREFERRED_SIZE)
							.addComponent(Stop,GroupLayout.PREFERRED_SIZE,53,GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(OutputTabbedPane)))
				.addContainerGap()));

		OutputPanelLayout.setVerticalGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(OutputPanelLayout.createParallelGroup()
				.addGroup(OutputPanelLayout.createSequentialGroup()
					.addComponent(OutputLabel,GroupLayout.PREFERRED_SIZE,22,GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(OutputPanelLayout.createSequentialGroup().addGap(15,15,15)
							.addComponent(getRunningLabel()).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(Run).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(Stop).addGap(0,123,Short.MAX_VALUE))))
				.addGroup(
					OutputPanelLayout.createSequentialGroup().addGap(5).addComponent(OutputTabbedPane).addGap(5))));

		GroupLayout jInternalFrame1Layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(jInternalFrame1Layout);
		jInternalFrame1Layout
			.setHorizontalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(TabbedPane,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
				.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE));
		jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(jInternalFrame1Layout.createSequentialGroup()
				.addComponent(TabbedPane,GroupLayout.PREFERRED_SIZE,457,GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,300,GroupLayout.PREFERRED_SIZE)));

		repaint();

		JTextPane[] ret = new JTextPane[2];
		ret[0] = OutputTextPane;
		ret[1] = ConsoleTextPane;
		return ret;

	}

	public static void addListener(AbstractButton button,ProgrammingWindow window,Tab tab) {
		button.addActionListener(new Listener(window,tab));
	}

	public JLabel getRunningLabel() {
		return RunningLabel;
	}

	public void close() {
		System.out.println("closing programming window");
		for (Tab tab : Tabs) {
			try {
				tab.getInterpretter().interrupt();
			} catch (NullPointerException e) {
			}
		}
	}

}

/*
 * 
 * int nxt = sec + 7;
 * 
 * print(nxt); print(sec);
 * 
 * while(nxt >= sec) { delay(1); print(nxt - sec); print(left); }
 * 
 * print(finished)
 * 
 */



class Tab extends JPanel {

	private JScrollPane ProgrammingPaneScrollPane;
	private JTextPane ProgrammingPane;

	private AbstractDocument document;
	private Actionlist actions;

	private UndoManager manager;

	private JScrollPane LinecounterPaneScrollPane;
	private JTextPane LinecounterPane;

	Synchronizer synchronizer;

	private Thread interpretter;

	public Tab(ProgrammingWindow window) {

		ProgrammingPane = new JTextPane();
		ProgrammingPane.setEditable(true);
		ProgrammingPane.setFont(new Font("sansserif",0,17));
		ProgrammingPane.setMargin(new Insets(5,5,5,5));

		LinecounterPane = new JTextPane();
		LinecounterPane.setEditable(false);
		LinecounterPane.setFont(new Font("sansserif",0,17));
		LinecounterPane.setMargin(new Insets(5,5,5,5));

		setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		ProgrammingPaneScrollPane = new JScrollPane(ProgrammingPane);
		LinecounterPaneScrollPane = new JScrollPane(LinecounterPane);

		synchronizer = new Synchronizer(LinecounterPaneScrollPane);

		ProgrammingPaneScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ProgrammingPaneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		ProgrammingPaneScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
		ProgrammingPaneScrollPane.getVerticalScrollBar().addAdjustmentListener(synchronizer);

		LinecounterPaneScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		LinecounterPaneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		LinecounterPaneScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
		// LinecounterPaneScrollPane.getVerticalScrollBar().addAdjustmentListener(synchronizer);
		// LinecounterPaneScrollPane.getVerticalScrollBar().setModel(ProgrammingPaneScrollPane.getVerticalScrollBar().getModel());

		GroupLayout ProgrammingPanelLayout = new GroupLayout(this);
		ProgrammingPanelLayout.setHorizontalGroup(ProgrammingPanelLayout
			.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
				ProgrammingPanelLayout.createSequentialGroup().addGap(2,2,2)
					.addComponent(LinecounterPaneScrollPane,GroupLayout.PREFERRED_SIZE,43,GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(ProgrammingPaneScrollPane,GroupLayout.DEFAULT_SIZE,500,Short.MAX_VALUE)));

		ProgrammingPanelLayout
			.setVerticalGroup(ProgrammingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(ProgrammingPaneScrollPane,GroupLayout.DEFAULT_SIZE,100,Short.MAX_VALUE)
				.addComponent(LinecounterPaneScrollPane));

		setLayout(ProgrammingPanelLayout);

		document = (AbstractDocument) ProgrammingPane.getStyledDocument();

		actions = new Actionlist(ProgrammingPane.getActions());

		manager = new UndoManager();

		UndoAction undoAction = new UndoAction(manager);
		RedoAction redoAction = new RedoAction(manager);
		InterpretAction interpretAction = new InterpretAction(document,window,this);
		ColorAction colorAction = new ColorAction(document,window);

		redoAction.setUndoAction(undoAction);
		undoAction.setRedoAction(redoAction);

		actions.append(undoAction);

		actions.append(redoAction);

		actions.append(colorAction);

		actions.append(interpretAction);

		addBindings();

		updateBars(window);

		document.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				lineNumbers();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				lineNumbers();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				lineNumbers();
			}

		});
		lineNumbers();

		document.setDocumentFilter(new DocumentFilter() {

			@Override
			public void replace(FilterBypass fb,int offset,int length,String text,AttributeSet attrs)
				throws BadLocationException {
				super.insertString(fb,offset,text.replace("\t","   "),Interpreter.context.getStyle("code"));
			}

		});

		document.addUndoableEditListener(new UndoableEditListener() {

			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				manager.addEdit(e.getEdit());
				undoAction.updateUndoState();
				redoAction.updateRedoState();
			}

		});

	}

	@Override
	public String getName() {
		try {
			return ProgrammingPane.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public Thread getInterpretter() {
		return interpretter;
	}

	public void setInterpretter(Thread interpretter) {
		this.interpretter = interpretter;
	}

	public JTextPane getProgrammingPane() {
		return ProgrammingPane;
	}

	public void updateBars(ProgrammingWindow window) {
		window.GenerateMenuBar(actions,this);
		JTextPane[] panes = window.GenerateOutputPanel(actions,this);
		try {
			System.out.println(Arrays.asList(panes));
			Interpreter.initmethods(window.getWorld().getPlayer(),panes[0],panes[1]);
		} catch (UnsupportetMethodNameExeption e) {
			System.err.println(e.getMessage());
		}
	}

	private void addBindings() {
		InputMap inputMap = ProgrammingPane.getInputMap(JComponent.WHEN_FOCUSED);

		KeyStroke key;

		key = KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,DefaultEditorKit.copyAction);

		key = KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,DefaultEditorKit.cutAction);

		key = KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,DefaultEditorKit.pasteAction);

		key = KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,DefaultEditorKit.selectLineAction);

		key = KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,DefaultEditorKit.endLineAction);

		key = KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,DefaultEditorKit.beginLineAction);

		key = KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,actions.get("Undo"));

		key = KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK);
		inputMap.put(key,actions.get("Redo"));
	}

	private void lineNumbers() {
		try {
			Document doc = LinecounterPane.getDocument();

			doc.remove(0,doc.getLength());

			for (int i = 1; i <= ProgrammingPane.getText().length()
				- ProgrammingPane.getText().replaceAll("\n","").length() + 1; i++) {
				doc.insertString(doc.getLength(),i + "\n",Interpreter.context.getStyle("clean"));
			}
			synchronizer.adjustmentValueChanged(new AdjustmentEvent(ProgrammingPaneScrollPane.getVerticalScrollBar(),2,
				12,ProgrammingPaneScrollPane.getVerticalScrollBar().getValue()));

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}



class Synchronizer implements AdjustmentListener {

	JScrollBar v2;

	public Synchronizer(JScrollPane sp2) {
		v2 = sp2.getVerticalScrollBar();
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		JScrollBar scrollBar = (JScrollBar) e.getSource();

		int value = scrollBar.getValue();

		// System.out.println(value + " old:" + v2.getValue());

		v2.setValue(value);

		// System.out.println("new:" + v2.getValue());
	}

}



class Actionlist extends ArrayList<Action> {

	public Actionlist(Action[] actions) {
		super(Arrays.asList(actions));
	}

	public void append(Action action) {
		Action old = get((String) action.getValue(Action.NAME));
		if (old == null) {
			add(action);
		} else {
			remove(old);
			add(action);
		}
	}

	public void add(String Name,JMenu bar) {
		for (Action action : this) {
			if ((String) action.getValue(Action.NAME) == Name) {
				bar.add(action);
				return;
			}
		}
		System.err.println(Name + " not found");
	}

	public Action get(String Name) {
		for (Action action : this) {
			if ((String) action.getValue(Action.NAME) == Name)
				return action;
		}
		return null;
	}

}



class UndoAction extends AbstractAction {

	RedoAction redoAction;
	UndoManager manager;

	public UndoAction(UndoManager manager) {
		super("Undo");
		setEnabled(false);
		this.manager = manager;
	}

	public void setRedoAction(RedoAction redoAction) {
		this.redoAction = redoAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			manager.undo();
		} catch (CannotUndoException ex) {
			System.out.println("Unable to undo: " + ex);
			ex.printStackTrace();
		}
		updateUndoState();
		redoAction.updateRedoState();
	}

	protected void updateUndoState() {
		if (manager.canUndo()) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

}



class RedoAction extends AbstractAction {

	UndoAction undoAction;
	UndoManager manager;

	public RedoAction(UndoManager manager) {
		super("Redo");
		setEnabled(false);
		this.manager = manager;
	}

	public void setUndoAction(UndoAction undoAction) {
		this.undoAction = undoAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			manager.redo();
		} catch (CannotRedoException ex) {
			ex.printStackTrace();
		}
		updateRedoState();
		undoAction.updateUndoState();
	}

	protected void updateRedoState() {
		if (manager.canRedo()) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

}



class InterpretAction extends AbstractAction {

	AbstractDocument document;
	Tab tab;
	ProgrammingWindow window;

	public InterpretAction(AbstractDocument doc,ProgrammingWindow window,Tab tab) {
		super("Run");
		this.document = doc;
		this.window = window;
		this.tab = tab;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.getWorld().reset();
		try {
			tab.getInterpretter().interrupt();
		} catch (NullPointerException e2) {
		}

		// window.getConsoleTextPane().setText("");
		// window.getOutputTextPane().setText("");

		window.getRunningLabel().setText("Running");
		window.getRunningLabel().setForeground(Color.GREEN);

		window.repaint();

		tab.setInterpretter(new Thread() {

			@Override
			public void run() {
				try {
					Interpreter.clear();
					Interpreter.interpret(document);
				} catch (java.lang.Error | UnsupportetVariableNameExeption | InvalidValueException
					| WrongTypeException e) {
					System.err.println(e.getMessage());
				}
			}

		});
		tab.getInterpretter().start();

		window.getRunningLabel().setText("Stopped");
		window.getRunningLabel().setForeground(Color.RED);
	}

}



class ColorAction extends AbstractAction {

	AbstractDocument document;
	ProgrammingWindow window;

	public ColorAction(AbstractDocument doc,ProgrammingWindow window) {
		super("Color");
		this.document = doc;
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread() {

			@Override
			public void run() {
				Interpreter.color(document);
			}

		}.start();
	}

}



class Listener implements ActionListener {

	Tab tab;
	ProgrammingWindow window;

	public Listener(ProgrammingWindow window,Tab tab) {
		this.window = window;
		this.tab = tab;
	}

	@SuppressWarnings("resource")
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(((AbstractButton) e.getSource()).getText());
		switch (((AbstractButton) e.getSource()).getText()) {
			case "New File":
				window.addTab("unnamed");
			break;
			case "Open File":
				JFileChooser fc = new JFileChooser(new File("rsc/saves/"));
				fc.setDialogTitle("Select a File");

				fc.showSaveDialog(null);

				File file = fc.getSelectedFile();
				if (!new FileNameExtensionFilter("programming files","epl").accept(file)) {
					System.err.println("invalid file");
					break;
				}
				Tab ntab = window.addTab(file.getName());

				Scanner sc;
				try {
					sc = new Scanner(file);
					while (sc.hasNextLine())
						ntab.getProgrammingPane().getDocument().insertString(
							ntab.getProgrammingPane().getDocument().getLength(),sc.nextLine() + "\n",
							Interpreter.context.getStyle("clean"));
					sc.close();
				} catch (FileNotFoundException | BadLocationException e1) {
					e1.printStackTrace();
				}
			break;
			case "Close Tab":
				window.deleteTab(tab);
			break;
			case "Save File as":
				final JFileChooser fc1 = new JFileChooser(new File("rsc/saves/"));
				fc1.setDialogTitle("Save File");
				fc1.setDialogType(JFileChooser.SAVE_DIALOG);
				fc1.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fc1.showSaveDialog(null);

				File file1 = fc1.getSelectedFile();
				if (!new FileNameExtensionFilter("programming files","epl").accept(file1)) {
					file1 = new File(file1.getPath() + ".epl");
				}

				if (!file1.exists() || (file1.exists() && JOptionPane.showConfirmDialog(null,"File exists, overwrite?",
					"Overwrite",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
					FileWriter wr;
					try {
						wr = new FileWriter(file1);
						wr.write(tab.getProgrammingPane().getText());
						wr.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			break;
			case "Stop":
				try {
					tab.getInterpretter().interrupt();
				} catch (NullPointerException e2) {
				}
				window.getRunningLabel().setText("Stopped");
				window.getRunningLabel().setForeground(Color.RED);
			break;
		}
	}

}
