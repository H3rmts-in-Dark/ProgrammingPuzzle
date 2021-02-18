package Programming;

import java.util.ArrayList;
import java.util.Arrays;

abstract class Method implements Executable {

	String name;
	Datatypes returntype;
	Datatypes[] parameters;

	public Method(Datatypes returntype, Datatypes... parameters) {
		this.name = getClass().getSimpleName();
		this.returntype = returntype;
		if (parameters != null) {
			this.parameters = parameters;
		} else
			this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Name:" + name + " returntype:" + returntype + " parameters:"
				+ new ArrayList<>(Arrays.asList(parameters));
	}

	public String getName() {
		return name;
	}

}

interface Executable {

	Object runCode(Object... objects);

}

class print extends Method {

	public print() {
		super(Datatypes.notype, Datatypes.all);
	}

	@Override
	public Object runCode(Object... objects) {
		System.out.println(objects);
		return null;
	}

}

class move extends Method {

	public move() {
		super(Datatypes.MY_boolean, Datatypes.MY_int, Datatypes.MY_int);
	}

	@Override
	public Object runCode(Object... objects) {
		System.out.println("moving " + objects[0] + " steps in " + objects[1] + "direction");
		return true;
	}

}
