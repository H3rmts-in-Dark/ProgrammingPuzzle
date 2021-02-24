package Programming;


import java.util.ArrayList;
import java.util.Arrays;

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
	public Variable<?> execute(ArrayList<Object> parameters) throws CustomExeption {
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
		System.out.println(Interpreter.sysoutin + "running " + name + " with parameters " + parameterlist);
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

	abstract Variable<?> runCode(Variable<?>...parameters) throws CustomExeption;

}



class print extends Method {

	public print() {
		super(Datatypes.notype,Datatypes.alltypes);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		System.out.println(Interpreter.sysoutin + "print to console:" + parameters[0].getValue());
		return null;
	}

	@Override
	String getDescription() {
		return " prints any information to the console";
	}

}



class printerr extends Method {

	public printerr() {
		super(Datatypes.notype,Datatypes.MY_String);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		System.out.println("error: " + Interpreter.sysoutin + "print to console:" + parameters[0].getValue());
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
	Variable<?> runCode(Variable<?>...parameters) {
		try {
			Thread.sleep((long) ((double) (parameters[0].getValue()) * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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



class getPlayerRotation extends Method {

	Player player;

	public getPlayerRotation(Player player) {
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



class getPlayerX extends Method {

	Player player;

	public getPlayerX(Player player) {
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



class getPlayerY extends Method {

	Player player;

	public getPlayerY(Player player) {
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



class getBlockActivated extends Method {

	Player player;

	public getBlockActivated(Player player) {
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



class getBlockSolid extends Method {

	Player player;

	public getBlockSolid(Player player) {
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



class Playermove extends Method {

	Player player;

	public Playermove(Player player) {
		super(Datatypes.notype,Datatypes.MY_int);
		this.player = player;
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		player.move((int) parameters[0].getValue());
		return null;
	}

	@Override
	String getDescription() {
		return "moves the player x Blocks in his direction";
	}

}



class changePlayerRotation extends Method {

	Player player;

	public changePlayerRotation(Player player) {
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



class Playerinteract extends Method {

	Player player;

	public Playerinteract(Player player) {
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
	public String toString() {
		String ret = "MethodList:\n";
		for (Method method : this) {
			ret += "  " + method.toString() + "\n";
		}
		return ret;
	}

}
