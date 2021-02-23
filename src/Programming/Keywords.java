package Programming;


import java.util.ArrayList;
import java.util.Arrays;


public enum Keywords {

	MY_if,MY_while,MY_function;

	static Keywords contains(String type) {
		for (Keywords datatype : Keywords.values()) {
			if (datatype == convert(type)) {
				return datatype;
			}
		}
		return null;
	}

	static Keywords convert(String str) {
		switch (str) {
			case "if":
			case "MY_if":
				return MY_if;
			case "function":
			case "MY_function":
				return MY_function;
			case "while":
			case "MY_while":
				return MY_while;
		}
		return null;
	}

	static String convert(Keywords keyword) {
		switch (keyword) {
			case MY_if:
				return "if";
			case MY_function:
				return "function";
			case MY_while:
				return "while";
		}
		return null;
	}

}



abstract class Keyword {

	String inner;

	String name;

	Datatypes[] parametertypes;

	public Keyword(String inner,Datatypes...parameters) {
		this.name = Keywords.convert(Keywords.convert(getClass().getSimpleName()));
		this.inner = inner;
		this.parametertypes = parameters;
	}

	public int getParametersize() {
		return parametertypes.length;
	}

	public boolean execute(ArrayList<Object> parameters) throws CustomExeption {
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
					}
				} catch (WrongTypeException | InvalidValueException e) {
					throw new WrongParameterTypeExeption(paramter.toString(),
						this.parametertypes[parameters.indexOf(paramter)],parameters.indexOf(paramter),
						Interpreter.line);
				}
		}
		System.out.println(Interpreter.sysoutin + "running " + name + " with parameters" + parameters);
		return run(parameterlist.toArray(new Variable<?>[0]));
	}

	@Override
	public String toString() {
		return "Name:";
	}

	public String getName() {
		return name;
	}

	abstract boolean run(Variable<?>...parameters) throws CustomExeption;

}



class MY_if extends Keyword {

	public MY_if(String text) {
		super(text,Datatypes.MY_boolean);
	}

	@Override
	boolean run(Variable<?>...parameters) throws CustomExeption {
		String ind = Interpreter.sysoutin + Interpreter.tabwith;
		if ((boolean) parameters[0].getValue()) {
			CustStr str = new CustStr(inner.strip());
			System.out.println();
			while (str.val.length() > 0) {
				Interpreter.interpretblock(str,ind);
			}
		}
		return false;
	}

}



class MY_while extends Keyword {

	public MY_while(String text) {
		super(text,Datatypes.MY_boolean);
	}

	@Override
	boolean run(Variable<?>...parameters) throws CustomExeption {
		if ((boolean) parameters[0].getValue()) {
			CustStr str = new CustStr(inner.strip());
			System.out.println();
			while (str.val.length() > 0) {
				try {
					Interpreter.interpretblock(str,Interpreter.sysoutin);
				} catch (BreakException e) {
		//			System.out.println(ind + Interpreter.tabwith + "breaking out of current Loop\n" + ind
		//				+ Interpreter.tabwith + "##################");
		//			System.out.println("\n" + ind + "##################\n");
					return false;
				}
			}
			return true;
		}
		return false;
	}

}



class MY_function extends Keyword {

	public MY_function(String text) {
		super(text,Datatypes.alltypes);
	}

	@Override
	boolean run(Variable<?>...parameters) throws CustomExeption {
		return false;
	}

}
