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
		String programm = "String str = \"hi\"; if(); long lng; ";
		interpret(programm);
	}

	public static void interpret(AbstractDocument document) {
		try {
			interpret(document.getText(0, document.getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public static void interpret(String str) {
		init();

		CustStr text = new CustStr(str);

		System.out.println(text);

		// System.out.println("interpreting:\n" + text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);

		System.out.println("\n" + variables);
		// while (text.length() > 0) {

		// }
	}

	/**
	 * @param text
	 */
	@SuppressWarnings("incomplete-switch")
	private static void interpretblock(CustStr text) {
		if (text.val.isBlank())
			return;
		System.out.println("testing: " + text);
		if (StrUtils.startswith(text.val.strip(), Keywords.values())) {
			System.err.println("keyword");
			String part = StrUtils.textuntil(text.val, "}").strip();
			text.val = StrUtils.removetext(text.val, part, "}");
			return;
		}
		String part = StrUtils.textuntil(text.val, ";").strip();
		text.val = StrUtils.removetext(text.val, part, ";");
		System.out.println("\nprocess spl: " + part);
		if (part.contains("(")) {
			String firs = StrUtils.first(part, '(');
			Method method = methods.get(firs);
			if (method != null) {
				System.out.println("method: " + method);
				String parameters = StrUtils.fromto(part, '(', ')');
				System.out.println("parameters = " + parameters);
			} else {
				System.err.println("method exeption");
			}
		} else {
			String typestr = StrUtils.first(part, ' ');
			Datatypes type = Datatypes.contains(typestr);
			if (type != null) {
				System.out.println("datatype: " + type);
				String name = StrUtils.part(part, ' ', 1);
				System.out.println("name: " + name);
				if (checkname(name)) {
					if (part.contains("=")) {
						String value = StrUtils.part(part, '=', 1);
						System.out.println("value: " + value);
						Variable<?> variable;
						switch (type) {
						case MY_int:
							variable = new MY_int(value, name);
							variables.add(variable);
							break;
						case MY_boolean:
							variable = new MY_boolean(value, name);
							variables.add(variable);
							break;
						case MY_String:
							variable = new MY_String(value, name);
							variables.add(variable);
							break;
						case MY_long:
							variable = new MY_long(value, name);
							variables.add(variable);
							break;
						}
					} else {
						Variable<?> variable;
						switch (type) {
						case MY_int:
							variable = new MY_int(name);
							variables.add(variable);
							break;
						case MY_boolean:
							variable = new MY_boolean(name);
							variables.add(variable);
							break;
						case MY_String:
							variable = new MY_String(name);
							variables.add(variable);
							break;
						case MY_long:
							variable = new MY_long(name);
							variables.add(variable);
							break;
						}
					}
				} else {
					System.err.println("naming exeption");
				}
			}
		}
	}

	private static boolean checkname(String name) {
		return true;
	}

	public static void init() {
		methods = new MethodList();

		methods.append(new print());

		methods.append(new move());

		variables = new VariableList();

		variables.append(new MY_long("sec") {

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

	@Override
	public String toString() {
		return val;
	}

}

class StrUtils {

	public static String getText(AbstractDocument document) {
		try {
			return document.getText(0, document.getLength());
		} catch (BadLocationException e) {
			return "";
		}
	}

	public static boolean startswith(String text, Keywords... keywords) {
		for (Keywords keyword : keywords) {
			if (text.startsWith(Keywords.convert(keyword)))
				return true;
		}
		return false;
	}

	public static String textuntil(String text, String ch) {
		String ret = text.split(ch)[0];
		return ret;
	}

	public static String removetext(String text, String ret, String ch) {
		return text.substring(Math.min((ret + ch).length(), text.length()));
	}

	public static String first(String text, char split) {
		char[] chars = new char[text.length()];
		text.getChars(0, text.length(), chars, 0);
		String out = "";
		for (char ch : chars) {
			if (ch != split)
				out += ch;
			else
				return out.strip();
		}
		return text.strip();
	}

	public static String part(String text, char split, int ind) {
		char[] chars = new char[text.length()];
		text.getChars(0, text.length(), chars, 0);
		String out = "";
		int counter = 0;
		for (char ch : chars) {
			if (ch != split)
				out += ch;
			else if (counter != ind) {
				counter++;
				out = "";
			} else
				return out.strip();
		}
		return out.strip();
	}

	public static String fromto(String text, char start, char end) {
		char[] chars = new char[text.length()];
		text.getChars(0, text.length(), chars, 0);
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
	 */
	public void append(Method method) {
		Method old = get(method.name);
		if (old == null) {
			add(method);
		} else {
			throw new RuntimeException("duplicate Method");
		}
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
	 */
	public void append(Variable<?> variable) {
		Variable<?> old = get(variable.getName());
		if (old == null) {
			add(variable);
		} else {
			throw new RuntimeException("duplicate Variable:" + variable);
		}
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
