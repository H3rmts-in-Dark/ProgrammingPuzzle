package Programming;


import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
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
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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
	private JMenuItem MenuBarOtherItem1;
	private JMenuItem MenuBarFileItem1;
	private JMenuItem MenuBarFileItem3;
	private JMenuItem MenuBarFileItem4;
	/* ================================================================= */
	private JTabbedPane TabbedPane;

	private ArrayList<Tab> Tabs;

	private JPanel OutputPanel;
	private JLabel OutputLabel;
	private JButton Run;
	private JButton Stop;
	private JScrollPane OutputTextPaneScrollPane;
	private JTextPane OutputTextPane;
	/* ================================================================= */

	public ProgrammingWindow(World world) {
		super(700,700,new Point(40,40),"Programming Window",0,false);
		this.world = world;

		GenerateProgrammingPane();

		GenerateOutputPanel();

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
				.addComponent(OutputPanel,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)));

		try {
			Interpreter.initmethods(world.getPlayer(),OutputTextPane);
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

	private void GenerateProgrammingPane() {
		TabbedPane = new JTabbedPane();
		TabbedPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
		TabbedPane.setToolTipText("");

		Tabs = new ArrayList<>();

		Tab firstTab = new Tab(this);
		Tabs.add(firstTab);

		TabbedPane.addTab("Tab",firstTab);
	}

	private void GenerateOutputPanel() {
		Run = new JButton("Run");
		Run.setFont(new Font("Arial",1,10));
		addListener(Run);

		Stop = new JButton("Stop");
		Stop.setFont(new Font("Arial",1,10));
		addListener(Stop);

		OutputTextPane = new JTextPane();
		OutputTextPane.setEditable(false);
		OutputTextPane.setMargin(new Insets(5,5,5,5));
		OutputTextPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		OutputTextPaneScrollPane = new JScrollPane(OutputTextPane);

		OutputLabel = new JLabel();
		OutputLabel.setFont(new Font("Arial",1,16));
		OutputLabel.setText("Output");

		OutputPanel = new JPanel();
		OutputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,false));

		GroupLayout OutputPanelLayout = new GroupLayout(OutputPanel);
		OutputPanel.setLayout(OutputPanelLayout);

		OutputPanelLayout.setHorizontalGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(OutputPanelLayout.createSequentialGroup().addContainerGap().addGroup(OutputPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(OutputLabel,GroupLayout.PREFERRED_SIZE,82,GroupLayout.PREFERRED_SIZE)
				.addGroup(OutputPanelLayout.createSequentialGroup()
					.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(Run,GroupLayout.PREFERRED_SIZE,53,GroupLayout.PREFERRED_SIZE)
						.addComponent(Stop,GroupLayout.PREFERRED_SIZE,53,GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(OutputTextPaneScrollPane)))
				.addContainerGap()));

		OutputPanelLayout.setVerticalGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(OutputPanelLayout.createSequentialGroup()
				.addComponent(OutputLabel,GroupLayout.PREFERRED_SIZE,22,GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(OutputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(OutputPanelLayout.createSequentialGroup().addComponent(Run)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(Stop)
						.addGap(0,123,Short.MAX_VALUE))
					.addComponent(OutputTextPaneScrollPane))
				.addContainerGap()));
	}

	public void GenerateMenuBar(Actionlist actions) {
		MenuBar = new JMenuBar();

		MenuBarFile = new JMenu();
		MenuBarFile.setText("File");

		MenuBarFileItem1 = new JMenuItem();
		MenuBarFileItem1.setText("New File");
		addListener(MenuBarFileItem1);
		MenuBarFile.add(MenuBarFileItem1);

		MenuBarFileItem2 = new JMenuItem();
		MenuBarFileItem2.setText("Open File");
		addListener(MenuBarFileItem2);
		MenuBarFile.add(MenuBarFileItem2);

		MenuBarFileItem3 = new JMenuItem();
		MenuBarFileItem3.setText("Save File");
		addListener(MenuBarFileItem3);
		MenuBarFile.add(MenuBarFileItem3);

		MenuBarFileItem4 = new JMenuItem();
		MenuBarFileItem4.setText("Save File as");
		addListener(MenuBarFileItem4);
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
		addListener(MenuBarPreferencesItem1);
		MenuBarPreferences.add(MenuBarPreferencesItem1);

		MenuBarPreferences.add(new JPopupMenu.Separator());

		MenuBarPreferencesItem2 = new JCheckBoxMenuItem();
		MenuBarPreferencesItem2.setSelected(true);
		MenuBarPreferencesItem2.setText("Auto complettion");
		addListener(MenuBarPreferencesItem2);
		MenuBarPreferences.add(MenuBarPreferencesItem2);

		MenuBarPreferencesItem3 = new JCheckBoxMenuItem();
		MenuBarPreferencesItem3.setSelected(true);
		MenuBarPreferencesItem3.setText("Show errors");
		addListener(MenuBarPreferencesItem3);
		MenuBarPreferences.add(MenuBarPreferencesItem3);

		MenuBar.add(MenuBarPreferences);

		MenuBarOther = new JMenu();
		MenuBarOther.setText("Other");

		MenuBarOtherItem1 = new JMenuItem("Run");
		addListener(MenuBarOtherItem1);
		MenuBarOther.add(MenuBarOtherItem1);

		MenuBarOtherItem2 = new JMenuItem("Stop");
		addListener(MenuBarOtherItem2);
		MenuBarOther.add(MenuBarOtherItem2);

		actions.add("Interpret",MenuBarOther);

		MenuBarOther.add(new Separator());

		MenuBarOtherItem3 = new JMenuItem("Go to");
		addListener(MenuBarOtherItem3);
		MenuBarOther.add(MenuBarOtherItem3);

		MenuBarOtherItem4 = new JMenuItem("Find");
		addListener(MenuBarOtherItem4);
		MenuBarOther.add(MenuBarOtherItem4);

		MenuBar.add(MenuBarOther);

		setJMenuBar(MenuBar);
	}

	public static void addListener(AbstractButton button) {
		button.addActionListener(new Listener());
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

	public Tab(ProgrammingWindow window) {

		ProgrammingPane = new JTextPane();
		ProgrammingPane.setEditable(true);
		ProgrammingPane.setMargin(new Insets(5,5,5,5));

		LinecounterPane = new JTextPane();
		LinecounterPane.setEditable(false);
		LinecounterPane.setMargin(new Insets(5,5,5,5));

		setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		ProgrammingPaneScrollPane = new JScrollPane(ProgrammingPane);
		ProgrammingPaneScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ProgrammingPaneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		ProgrammingPaneScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		LinecounterPaneScrollPane = new JScrollPane(LinecounterPane);
		LinecounterPaneScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		LinecounterPaneScrollPane.setViewportView(LinecounterPane);
		LinecounterPaneScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

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
		InterpretAction interpretAction = new InterpretAction(document);

		redoAction.setUndoAction(undoAction);
		undoAction.setRedoAction(redoAction);

		actions.append(undoAction);

		actions.append(redoAction);

		actions.append(interpretAction);

		addBindings();
		window.GenerateMenuBar(actions);

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
				super.insertString(fb,offset,text.replace("\t","   "),attrs);
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
				doc.insertString(doc.getLength(),i + "\n",null);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
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
		System.out.println(Name + " not found");
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
			putValue(Action.NAME,manager.getUndoPresentationName());
		} else {
			setEnabled(false);
			putValue(Action.NAME,"Undo");
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
			putValue(Action.NAME,manager.getRedoPresentationName());
		} else {
			setEnabled(false);
			putValue(Action.NAME,"Redo");
		}
	}

}



class InterpretAction extends AbstractAction {

	AbstractDocument document;

	public InterpretAction(AbstractDocument doc) {
		super("Interpret");
		this.document = doc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread() {

			@Override
			public void run() {
				try {
					Interpreter.clear();
					Interpreter.interpret(document);
				} catch (UnsupportetVariableNameExeption | InvalidValueException | WrongTypeException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

}



class Listener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((AbstractButton) e.getSource()).getText()) {
			case "Run":
				System.out.println("hi");
			break;
		}
	}

}
