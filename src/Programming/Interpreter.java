package Programming;


import java.util.ArrayList;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;


public class Interpreter {

	private static MethodList methods;

	private static DatatypeList datatypes;

	public Interpreter() {

	}

	public static void interpret(AbstractDocument document) {
		try {
			interpret(document.getText(0,document.getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public static void interpret(String str) {
		init();

		Str text = new Str(str);

		// System.out.println("interpreting:\n" + text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		interpretblock(text);
		// while (text.length() > 0) {

		// }
	}

	private static void interpretblock(Str text) {
		if (text.isBlank())
			return;
		if (text.startswith(Keywords.values())) {
			System.out.println("keyword");
			return;
		}
		String part = text.textuntil(";").strip();
		System.out.println("\nprocess: " + part);
		if (part.contains("(")) {
			String firs = Str.first(part,'(');
			Method method = methods.get(firs);
			if (method != null) {
				System.out.println("method: " + method);
			}
			String parameters = Str.fromto(part,'(',')');
			System.out.println("parameters = " + parameters);
		} else {
			String typestr = Str.first(part,' ');
			Datatypes type = Datatypes.contains(typestr);
			if (type != null) {
				System.out.println("datatype: " + type);
				String name = Str.part(part,' ',1);
				System.out.println("name: " + name);
				if (checkname(name)) {
					String value = Str.part(part,'=',1);
					System.out.println("value: " + value);

					datatypes.append(new Datatype<Integer>(value,type) {

						@Override
						public Integer getValue() {
							return (int) (System.currentTimeMillis() / 0.001);
						}

						@Override
						boolean checkvalue(Object test) {
							return true;
						}

					});
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

		datatypes = new DatatypeList();

		datatypes.append(new MY_boolean(true,"testing"));

		datatypes.append(new MY_int("sec") {

			@Override
			public Integer getValue() {
				return (int) (System.currentTimeMillis() / 0.001);
			}

		});

		System.out.println(methods);

		System.out.println(datatypes);
	}

}



class Str {

	private String data;

	public Str(AbstractDocument document) {
		try {
			data = document.getText(0,document.getLength());
		} catch (BadLocationException e) {
			return;
		}
	}

	public Str(String str) {
		data = str;
	}

	public void setString(String data) {
		this.data = data;
	}

	public int length() {
		return data.length();
	}

	public boolean isBlank() {
		return data.isBlank();
	}

	public boolean startswith(Keywords...keywords) {
		for (Keywords string : keywords) {
			if (data.startsWith(string.toString()))
				return true;
		}
		return false;
	}

	public String textuntil(String ch) {
		String ret = data.split(ch)[0];
		removetext(ret,ch);
		return ret;
	}

	private void removetext(String ret,String ch) {
		data = data.substring(Math.min((ret + ch).length(),data.length()));
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

	public static String part(String text,char split,int ind) {
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
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
	 */
	public void append(Method method) {
		Method old = get(method.name);
		if (old == null) {
			add(method);
		} else {
			throw new RuntimeException("duplicate Method");
		}
	}

}



class DatatypeList extends ArrayList<Datatype<?>> {

	public DatatypeList() {
		super();
	}

	public Datatype<?> get(String name) {
		for (Datatype<?> datatype : this) {
			if (datatype.getName().equals(name))
				return datatype;
		}
		return null;
	}

	/**
	 * tests if method with same name alread exists
	 * 
	 * @param method
	 */
	public void append(Datatype<?> datatype) {
		Datatype<?> old = get(datatype.getName());
		if (old == null) {
			add(datatype);
		} else {
			throw new RuntimeException("duplicate Datatype:" + datatype);
		}
	}

}
