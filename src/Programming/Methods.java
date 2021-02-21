package Programming;


import java.util.ArrayList;
import java.util.Arrays;


abstract class Method {

	String name;
	Datatypes returntype;
	Datatypes[] parameters;

	public Method(Datatypes returntype,Datatypes...parameters) {
		this.name = getClass().getSimpleName();
		this.returntype = returntype;
		if (parameters != null) {
			this.parameters = parameters;
		} else
			this.parameters = parameters;
	}

	public int getParametersize() {
		return parameters.length;
	}

	@SuppressWarnings("incomplete-switch")
	public Variable<?> execute(ArrayList<String> parameters) throws WrongParameterTypeExeption {
		ArrayList<Variable<?>> parameterlist = new ArrayList<>();
		for (String paramter : parameters) {
			try {
				switch (this.parameters[parameters.indexOf(paramter)]) {
					case MY_String:
					case alltypes:
						parameterlist.add(new MY_String(paramter,name + "paramter" + parameters.indexOf(paramter)));
					break;
					case MY_boolean:
						parameterlist.add(new MY_boolean(paramter,name + "paramter" + parameters.indexOf(paramter)));
					break;
					case MY_double:
						parameterlist.add(new MY_double(paramter,name + "paramter" + parameters.indexOf(paramter)));
					break;
					case MY_int:
						parameterlist.add(new MY_int(paramter,name + "paramter" + parameters.indexOf(paramter)));
					break;
					case MY_long:
						parameterlist.add(new MY_long(paramter,name + "paramter" + parameters.indexOf(paramter)));
					break;
				}
			} catch (WrongTypeException | InvalidValueException e) {
				throw new WrongParameterTypeExeption(paramter,this.parameters[parameters.indexOf(paramter)],
					parameters.indexOf(paramter),-1);
			}
		}
		System.out.println("running " + getClass().getSimpleName() + " with parameters " + parameterlist);
		return runCode(parameterlist.toArray(new Variable<?>[0]));
	}

	@Override
	public String toString() {
		return "Name:" + name + " returntype:" + returntype + " parameters:"
			+ new ArrayList<>(Arrays.asList(parameters));
	}

	public String getName() {
		return name;
	}

	abstract Variable<?> runCode(Variable<?>...parameters);

}



class print extends Method {

	public print() {
		super(Datatypes.notype,Datatypes.alltypes);
	}

	@Override
	Variable<?> runCode(Variable<?>...parameters) {
		System.out.println("print to console:" + parameters[0].getValue());
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
