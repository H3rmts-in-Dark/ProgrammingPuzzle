package Programming;


import java.util.ArrayList;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;


public class Interpreter {

	private static MethodList methods;

	private static VariableList variables;

	public Interpreter() {

	}

	public static void interpret() {
		String programm = "int dir;int stepps = 12 * 2;move(stepps - 3,dir+1);String test = toString(stepps / 13); print(test+\"lustig\")";
		interpret(programm);
	}

	public static void interpret(AbstractDocument document) {
		try {
			interpret(document.getText(0,document.getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public static void interpret(String str) {
		try {
			init();
		} catch (UnsupportetVariableNameExeption | UnsupportetMethodNameExeption | InvalidValueException
			| WrongTypeException e) {
			System.err.println("init gone wrong (some idiot added duplicate metods or attributes):" + e.getMessage());
			return;
		}

		CustStr text = new CustStr(str);

		System.out.println("interpreting:\n" + text + "\n\n");

		while (text.length() > 0) {
			try {
				interpretblock(text);
			} catch (MethodParametersExeption | MethodNotFoundExeption | WrongParameterTypeExeption
				| UnsupportetVariableNameExeption | InvalidValueException | WrongTypeException | CalculationExeption
				| VariableDeclarationExeption e) {
				e.printStackTrace();
				// System.err.println(e.getMessage());
				return;
			}
		}

		System.out.println("\n" + variables);

		System.out.println("\n" + methods);
	}

	/**
	 * @param text
	 * @throws MethodParametersExeption
	 * @throws MethodNotFoundExeption
	 * @throws WrongParameterTypeExeption
	 * @throws UnsupportetVariableNameExeption
	 * @throws WrongTypeException
	 * @throws InvalidValueException
	 * @throws CalculationExeption
	 * @throws VariableDeclarationExeption
	 */
	@SuppressWarnings("incomplete-switch")
	private static void interpretblock(CustStr text) throws MethodParametersExeption,MethodNotFoundExeption,
		WrongParameterTypeExeption,UnsupportetVariableNameExeption,InvalidValueException,WrongTypeException,
		CalculationExeption,VariableDeclarationExeption {
		if (text.val.isBlank()) {
			text.val = "";
			return;
		}
		System.out.println("##################");
		if (StrUtils.startswith(text.val.strip(),Keywords.values())) {
			System.err.println("keyword");
			String part = StrUtils.textuntil(text.val,'}').strip();
			text.val = StrUtils.removetext(text.val,part,"}");
			return;
		}
		String part = StrUtils.textuntil(text.val,';');
		text.val = StrUtils.removetext(text.val,part,";");
		part = part.strip();
		System.out.println("process: " + part);
		if (!part.contains("=") && part.contains("(")) {
			String firs = StrUtils.first(part,'(');
			Method method = methods.get(firs);
			if (method != null) {
				System.out.println("method: " + method);
				String parameters = StrUtils.fromto(part,'(',')');
				ArrayList<String> params = StrUtils.parts(parameters,',');
				if (params.size() != method.getParametersize())
					throw new MethodParametersExeption(params.size(),method.getParametersize(),-1);
				for (String param : params) {
					if (param.contains("+") || param.contains("-") || param.contains("*") || param.contains("/")) {
						params.set(params.indexOf(param),calculate(param));
					} else if (variables.get(param) != null) {
						params.set(params.indexOf(param),variables.get(param).getValue().toString());
					}
				}
				method.execute(params);
			} else {
				throw new MethodNotFoundExeption(firs,-1);
			}
		} else {
			String typestr = StrUtils.first(part,' ');
			Datatypes type = Datatypes.contains(typestr);
			if (type != null) {
				System.out.println("datatype: " + type);
				if (StrUtils.parts(StrUtils.part(part,'=',0),' ').size() > 2)
					throw new VariableDeclarationExeption(part,-1);
				String name = StrUtils.part(part,' ',1);
				System.out.println("name: " + name);
				if (checkname(name))
					throw new UnsupportetVariableNameExeption(name,-1);
				if (part.contains("=")) {
					String value = StrUtils.part(part,'=',1);
					Variable<?> set = variables.get(value);
					Method met = methods.get(StrUtils.first(value,'('));
					if (set != null) {
						value = set.getValue().toString();
						System.out.println("value from variable: " + set);
					} else if (met != null) {
						System.out.println("method: " + met);
						String parameters = StrUtils.fromto(value,'(',')');
						ArrayList<String> params = StrUtils.parts(parameters,',');
						if (params.size() != met.getParametersize())
							throw new MethodParametersExeption(params.size(),met.getParametersize(),-1);
						for (String param : params) {
							if (param.contains("+") || param.contains("-") || param.contains("*")
								|| param.contains("/")) {
								params.set(params.indexOf(param),calculate(param));
							} else if (variables.get(param) != null) {
								params.set(params.indexOf(param),variables.get(param).getValue().toString());
							}
						}
						value = met.execute(params).getValue().toString();
					} else if (part.contains("+") || part.contains("-") || part.contains("*") || part.contains("/")) {
						value = calculate(value);
						System.out.println("value: " + value);
					} else
						System.out.println("value: " + value);
					Variable<?> variable = null;
					switch (type) {
						case MY_int:
							variable = new MY_int(value,name);
						break;
						case MY_boolean:
							variable = new MY_boolean(value,name);
						break;
						case MY_String:
							variable = new MY_String(value,name);
						break;
						case MY_long:
							variable = new MY_long(value,name);
						break;
						case MY_double:
							variable = new MY_double(value,name);
						break;
					}
					variables.add(variable);
				} else {
					System.out.println("value: " + "no value");
					Variable<?> variable = null;
					switch (type) {
						case MY_int:
							variable = new MY_int(name);
						break;
						case MY_boolean:
							variable = new MY_boolean(name);
						break;
						case MY_String:
							variable = new MY_String(name);
						break;
						case MY_long:
							variable = new MY_long(name);
						break;
						case MY_double:
							variable = new MY_double(name);
						break;
					}
					variables.add(variable);
				}

			}
		}
		System.out.println("##################\n");
	}

	private static String calculate(String inp) throws CalculationExeption {
		inp = inp.replace(" ","");
		String builder = "";
		String operator = "+";
		String solution = "";
		for (int i = 0; i < inp.length(); i++) {
			char c = inp.charAt(i);
			if (c == '+' || c == '-' || c == '*' || c == '/' || i == inp.length() - 1) {
				if (i == inp.length() - 1)
					builder += c;
				if (builder.isBlank()) {
					builder += c;
					continue;
				}
				Variable<?> variable = variables.get(builder);
				if (variable != null) {
					builder = variable.getValue().toString();
				}
				// System.out.print(solution + operator + builder + "=");
				if (operator == "") {
					solution = builder;
				} else {
					if (operator.equals("+")) {
						solution = add(solution,builder);
					} else if (operator.equals("*")) {
						solution = mulitiply(solution,builder);
					} else if (operator.equals("-")) {
						solution = subract(solution,builder);
					} else if (operator.equals("/")) {
						solution = divide(solution,builder);
					} else {
						throw new CalculationExeption(inp + " op:" + operator,-1);
					}
				}
				// System.out.println(solution);
				builder = "";
				operator = "" + c;
			} else {
				builder += c;
			}
		}
		return solution.toString();
	}

	private static String add(String f,String l) {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi + li);
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		if (f.startsWith("\"") && f.endsWith("\""))
			f = f.substring(1,f.length() - 1);
		if (l.startsWith("\"") && l.endsWith("\""))
			l = l.substring(1,l.length() - 1);
		return f + l;
	}

	private static String subract(String f,String l) throws CalculationExeption {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi - li);
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		throw new CalculationExeption(f + "-" + l,-1);
	}

	private static String mulitiply(String f,String l) throws CalculationExeption {
		try {
			double fi = Double.parseDouble(f); // f int
			try {
				double li = Integer.parseInt(l); // l int
				return String.valueOf(fi * li);
			} catch (Exception e) { // f int l not
				if (l.startsWith("\"") && l.endsWith("\""))
					l = f.substring(1,l.length() - 1);
				String build = "";
				for (int i = 0; i < fi; i++) {
					build += l;
				}
				return build;
			}
		} catch (Exception e) { // f no int
			try {
				double li = Double.parseDouble(l); // l int f not
				if (f.startsWith("\"") && f.endsWith("\""))
					f = f.substring(1,f.length() - 1);
				String build = "";
				for (int i = 0; i < li; i++) {
					build += f;
				}
				return build;
			} catch (Exception e1) { // l and f no int
				throw new CalculationExeption(f + "*" + l,-1);
			}
		}

	}

	private static String divide(String f,String l) throws CalculationExeption {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi / li);
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		throw new CalculationExeption(f + "/" + l,-1);
	}

	private static boolean checkname(String name) {
		return Keywords.contains(name) != null || Datatypes.contains(name) != null || name.contains("+")
			|| name.contains("-") || name.contains("*") || name.contains("/");
	}

	public static void init()
		throws UnsupportetVariableNameExeption,UnsupportetMethodNameExeption,InvalidValueException,WrongTypeException {
		methods = new MethodList();
		variables = new VariableList();

		methods.addMethod(new print());

		methods.addMethod(new move());

		methods.addMethod(new toString());

		variables.addVariable(new MY_long("sec") {

			@Override
			public Long getValue() {
				return (long) (System.currentTimeMillis() * 0.001);
			}

		});

	}

}



class CustStr {

	public String val;

	public CustStr(String text) {
		val = text;
	}

	public int length() {
		return val.length();
	}

	@Override
	public String toString() {
		return val;
	}

}



class StrUtils {

	public static String getText(AbstractDocument document) {
		try {
			return document.getText(0,document.getLength());
		} catch (BadLocationException e) {
			return "";
		}
	}

	public static boolean startswith(String text,Keywords...keywords) {
		for (Keywords keyword : keywords) {
			if (text.startsWith(Keywords.convert(keyword)))
				return true;
		}
		return false;
	}

	public static String textuntil(String text,char ch) {
		return part(text,ch,0);
	}

	public static String removetext(String text,String ret,String ch) {
		return text.substring(Math.min((ret + ch).length(),text.length()));
	}

	public static String first(String text,char split) {
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		String out = "";
		for (char ch : chars) {
			if (ch != split)
				out += ch;
			else
				return out.strip();
		}
		return text.strip();
	}

	public static ArrayList<String> parts(String text,char split) {
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		String build = "";
		ArrayList<String> parts = new ArrayList<>();
		for (char ch : chars) {
			if (ch != split)
				build += ch;
			else {
				parts.add(build.strip());
				build = "";
			}
		}
		parts.add(build.strip());
		build = "";
		return parts;
	}

	public static String part(String text,char split,int ind) {
		return parts(text,split).get(ind);
	}

	public static String fromto(String text,char start,char end) {
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		String out = "";
		boolean started = false;
		for (char ch : chars) {
			if (started)
				if (ch != end)
					out += ch;
				else
					return out;
			else if (ch == start)
				started = true;
		}
		return text;
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
		throw new UnsupportetMethodNameExeption(method,"duplicate name ",-1);
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



class VariableList extends ArrayList<Variable<?>> {

	public VariableList() {
		super();
	}

	public Variable<?> get(String name) {
		name = name.strip();
		for (Variable<?> variable : this) {
			if (variable.getName().equals(name))
				return variable;
		}
		return null;
	}

	/**
	 * tests if method with same name alread exists
	 * 
	 * @param method
	 * @throws UnsupportetVariableNameExeption
	 */
	public boolean addVariable(Variable<?> variable) throws UnsupportetVariableNameExeption {
		Variable<?> old = get(variable.getName());
		if (old == null) {
			return super.add(variable);
		}
		throw new UnsupportetVariableNameExeption(variable,"duplicate name ",-1);
	}

	@Override
	public String toString() {
		String ret = "VariableList:\n";
		for (Variable<?> var : this) {
			ret += "  " + var.toString() + "\n";
		}
		return ret;
	}

}
