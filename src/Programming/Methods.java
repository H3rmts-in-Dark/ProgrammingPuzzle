package Programming;


import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import entitys.Player;


abstract class Method {

	String name;
	Datatypes returntype;
	Datatypes[] parametertypes;

	public Method(Datatypes returntype,Datatypes...parameters) {
		this.name = getClass().getSimpleName();
		this.returntype = returntype;
		this.parametertypes = parameters;
	}

	public int getParametersize() {
		return parametertypes.length;
	}

	@SuppressWarnings("incomplete-switch")
	public Variable<?> execute(ArrayList<Object> parameters) throws CustomExeption,InterruptedException {
		ArrayList<Variable<?>> parameterlist = new ArrayList<>();
		for (Object paramter : parameters) {
			if (paramter instanceof Variable<?>) {
				if (((Variable<?>) paramter).getType() != this.parametertypes[parameters.indexOf(paramter)]
					&& (this.parametertypes[parameters.indexOf(paramter)] != Datatypes.alltypes))
					throw new WrongParameterTypeExeption(paramter.toString(),
						parametertypes[parameters.indexOf(paramter)],parameters.indexOf(paramter),Interpreter.line);
				parameterlist.add((Variable<?>) paramter);
			} else
				try {
					switch (parametertypes[parameters.indexOf(paramter)]) {
						case MY_String:
						case alltypes:
							parameterlist.add(
								new MY_String(paramter.toString(),name + "paramter" + parameters.indexOf(paramter)));
						break;
						case MY_boolean:
							parameterlist.add(
								new MY_boolean(paramter.toString(),name + "paramter" + parameters.indexOf(paramter)));
						break;
						case MY_double:
							parameterlist.add(
								new MY_double(paramter.toString(),name + "paramter" + parameters.indexOf(paramter)));
						break;
						case MY_int:
							parameterlist
								.add(new MY_int(paramter.toString(),name + "paramter" + parameters.indexOf(paramter)));
						break;
					}
				} catch (WrongTypeException | InvalidValueException e) {
					throw new WrongParameterTypeExeption(paramter.toString(),
						this.parametertypes[parameters.indexOf(paramter)],parameters.indexOf(paramter),
						Interpreter.line);
				}
		}
		Interpreter.log(Interpreter.sysoutin + "running " + name + " with parameters " + parameterlist);
		return runCode(parameterlist.toArray(new Variable<?>[0]));
	}

	@Override
	public String toString() {
		return "Name:" + name + " returntype:" + returntype + " parameters:"
			+ new ArrayList<>(Arrays.asList(parametertypes));
	}

	abstract String getDescription();

	public String getName() {
		return name;
	}

	abstract Variable<?> runCode(Variable<?>...parameters) throws CustomExeption,InterruptedException;

}



class print extends Method {

	JTextPane textPane;

	public print(JTextPane textPane) {
		super(Datatypes.notype,Datatypes.alltypes);
		this.textPane = textPane;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		try {
			textPane.getDocument().insertString(textPane.getDocument().getLength(),parameters[0].getValue() + "\n",
				Interpreter.context.getStyle("normal"));
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	String getDescription() {
		return " prints any information to the console";
	}

}



class printerr extends Method {

	JTextPane textPane;

	public printerr(JTextPane textPane) {
		super(Datatypes.notype,Datatypes.MY_String);
		this.textPane = textPane;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		try {
			textPane.getDocument().insertString(textPane.getDocument().getLength(),parameters[0].getValue() + "\n",
				Interpreter.context.getStyle("error"));
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	String getDescription() {
		return " prints any error to the console";
	}

}



class toString extends Method {

	public toString() {
		super(Datatypes.MY_String,Datatypes.alltypes);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		try {
			return new MY_String(parameters[0].getValue().toString(),name + "returnValue");
		} catch (InvalidValueException | WrongTypeException e) {
			return null;
		}
	}

	@Override
	String getDescription() {
		return "converts any type into a String";
	}

}



class delay extends Method {

	public delay() {
		super(Datatypes.notype,Datatypes.MY_double);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws InterruptedException {
		Thread.sleep((long) ((double) (parameters[0].getValue()) * 1000));
		return null;
	}

	@Override
	String getDescription() {
		return "delays the programm for x * 1000 milliseconds (delays shorter than 1 sec are also possible)";
	}

}



class exit extends Method {

	public exit() {
		super(Datatypes.notype,Datatypes.notype);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		throw new ExitProgramm(Interpreter.line);
	}

	@Override
	String getDescription() {
		return " exits the programm";
	}

}



class loopbreak extends Method {

	public loopbreak() {
		super(Datatypes.notype,Datatypes.notype);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws BreakException {
		throw new BreakException(Interpreter.line);
	}

	@Override
	String getDescription() {
		return " exits the current loop";
	}

}



class getplayerrotation extends Method {

	Player player;

	public getplayerrotation(Player player) {
		super(Datatypes.MY_String,Datatypes.notype);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		return new MY_String(player.getStrRotation(),name + "returnValue");
	}

	@Override
	String getDescription() {
		return " returns the current Rotation of the player (\"up\",\"down\",\"left\",\"right\")";
	}

}



class getplayerx extends Method {

	Player player;

	public getplayerx(Player player) {
		super(Datatypes.MY_int,Datatypes.notype);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		return new MY_int(player.getX(),name + "returnValue");
	}

	@Override
	String getDescription() {
		return "returns the players X coordinate";
	}

}



class getplayery extends Method {

	Player player;

	public getplayery(Player player) {
		super(Datatypes.MY_int,Datatypes.notype);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		return new MY_int(player.getY(),name + "returnValue");
	}

	@Override
	String getDescription() {
		return "returns the players Y coordinate";
	}

}



class getblockactivated extends Method {

	Player player;

	public getblockactivated(Player player) {
		super(Datatypes.MY_boolean,Datatypes.notype);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		return new MY_int(player.getBlockActivated(),name + "returnValue");
	}

	@Override
	String getDescription() {
		return "returns if the block the player is looking at is activated";
	}

}



class getblocksolid extends Method {

	Player player;

	public getblocksolid(Player player) {
		super(Datatypes.MY_boolean,Datatypes.notype);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		return new MY_boolean(player.getBlockSolid(),name + "returnValue");
	}

	@Override
	String getDescription() {
		return "returns if the player can walk through the block he is looking at";
	}

}



class playermove extends Method {

	Player player;

	public playermove(Player player) {
		super(Datatypes.notype,Datatypes.MY_int);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		player.move((int) parameters[0].getValue());
		return null;
	}

	@Override
	String getDescription() {
		return "moves the player x Blocks in his direction";
	}

}



class changeplayerrotation extends Method {

	Player player;

	public changeplayerrotation(Player player) {
		super(Datatypes.notype,Datatypes.MY_String);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		player.changeRotation((String) parameters[0].getValue());
		return null;
	}

	@Override
	String getDescription() {
		return "changes the Rotation of the player to x";
	}

}



class playerinteract extends Method {

	Player player;

	public playerinteract(Player player) {
		super(Datatypes.notype,Datatypes.notype);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		player.interact();
		return null;
	}

	@Override
	String getDescription() {
		return "interacts with the plock the player is looking at";
	}

}



class MethodList extends ArrayList<Method> {

	public MethodList() {
		super();
	}

	public Method get(String name) {
		for (Method method : this) {
			if (method.name.equals(name))
				return method;
		}
		return null;
	}

	/**
	 * tests if method with same name alread exists
	 * 
	 * @param method
	 * @return
	 * @throws DuplicateMethodExeption
	 */
	public boolean addMethod(Method method) throws UnsupportetMethodNameExeption {
		Method old = get(method.name);
		if (old == null) {
			return super.add(method);
		}
		throw new UnsupportetMethodNameExeption(method,"duplicate name ",Interpreter.line);
	}

	@Override
	public boolean add(Method e) {
		throw new RuntimeException();
	}

	@Override
	public String toString() {
		String ret = "MethodList:\n";
		for (Method method : this) {
			ret += "  " + method.toString() + "\n";
		}
		return ret;
	}

}
