package Programming;


import java.util.ArrayList;
import java.util.Arrays;


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
				if (((Variable<?>) paramter).getType() != this.parametertypes[parameters.indexOf(paramter)])
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
						case MY_long:
							parameterlist
								.add(new MY_long(paramter.toString(),name + "paramter" + parameters.indexOf(paramter)));
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

}



class exit extends Method {

	public exit() {
		super(Datatypes.notype,Datatypes.notype);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) throws CustomExeption {
		throw new ExitProgramm(Interpreter.line);
	}

}



class move extends Method {

	public move() {
		super(Datatypes.notype,Datatypes.MY_int,Datatypes.MY_int);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		System.out
			.println("moving " + parameters[0].getValue() + " steps in " + parameters[1].getValue() + " direction");
		return null;
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
