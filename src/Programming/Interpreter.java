package Programming;


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import entitys.Player;


public class Interpreter {

	private static MethodList methods;

	private static VariableList variables;

	public static Integer line = 1;

	public static String sysoutin = "";

	public static String tabwith = "|---|  ";

	private static JTextPane log;

	private Interpreter() {

	}

	public static void interpret(AbstractDocument document) {
		interpret(StrUtils.getText(document));
	}

	public static void log(Object str) {
		insertText((AbstractDocument) log.getDocument(),str.toString() + "\n",false,Interpreter.Syles.compliernormal);
		System.out.println(str.toString());
		log.setCaretPosition(log.getDocument().getLength());
	}

	public static void interpret(String str) {
		CustStr text = new CustStr(str);

		Interpreter.log("interpreting:\n" + text + "\n\n");

		String exeption = "";

		while (text.val.length() > 0) {
			try {
				try {
					interpretblock(text);
				} catch (CustomExeption e) {
					// e.printStackTrace();
					exeption = e.getMessage();
					break;
				}
			} catch (InterruptedException e) {
				break;
			}
		}
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
		}
		if (exeption != "") {
			insertText((AbstractDocument) log.getDocument(),exeption,false,Interpreter.Syles.compliererror);
			System.err.println(exeption);
			System.out.println("rest:" + text.val);
		} else {
			Interpreter.log("\n" + variables);
			Interpreter.log("\n" + methods);
		}
	}

	public static void initmethods(Player player,JTextPane output,JTextPane log) throws UnsupportetMethodNameExeption {
		methods = new MethodList();
		variables = new VariableList();

		methods.addMethod(new print(output));
		methods.addMethod(new printerr(output));
		methods.addMethod(new toString());
		methods.addMethod(new delay());
		methods.addMethod(new exit());
		methods.addMethod(new loopbreak());

		methods.addMethod(new playermove(player));
		methods.addMethod(new changeplayerrotation(player));
		methods.addMethod(new playerinteract(player));
		methods.addMethod(new getplayerrotation(player));
		methods.addMethod(new getplayerx(player));
		methods.addMethod(new getplayery(player));
		methods.addMethod(new getblockactivated(player));
		methods.addMethod(new getblocksolid(player));
		methods.addMethod(new playerturn(player));

		System.out.println("Player: " + player);
		Interpreter.log = log;
		System.out.println("log:" + log.getText());
	}

	private static void initvariables()
		throws UnsupportetVariableNameExeption,InvalidValueException,WrongTypeException {
		variables.addVariable(new MY_int("sec") {

			@Override
			public Integer getValue() {
				return (int) (System.currentTimeMillis() * 0.001);
			}

		});
	}

	public static void clear() throws UnsupportetVariableNameExeption,InvalidValueException,WrongTypeException {
		variables.clear();
		initvariables();
	}

	/**
	 * @param text
	 * @throws CustomExeption
	 * @throws InterruptedException
	 */
	@SuppressWarnings({"incomplete-switch","null"})
	public static void interpretblock(CustStr text) throws CustomExeption,InterruptedException {
		if (text.val.isBlank()) {
			text.val = "";
			return;
		}
		Interpreter.log(sysoutin + "##################");

		if (StrUtils.startswith(StrUtils.fullstrip(text.val),Keywords.values())) { // keyword
			String first = StrUtils.first(text.val,'{');

			String inner = StrUtils.getSection(text.val,'{','}');

			String full = first + inner;
			if (StrUtils.count(full,"\n") > 0)
				line += StrUtils.count(full,"\n");

			System.out.println("bevtext.val:" + text.val);
			System.out.println("remove:" + full + "{ }");
			text.val = StrUtils.removetext(text.val,full + "{ }");
			System.out.println("text.val:" + text.val);

			first = StrUtils.fullstrip(first);

			Interpreter.log(sysoutin + "process: " + StrUtils.removespace(full));

			String name = StrUtils.first(first,'(');

			Interpreter.log(sysoutin + "name: " + name);
			Keyword keyword = null;

			inner = inner.substring(1,inner.length() - 1);
			Interpreter.log(sysoutin + "inner block: " + StrUtils.removespace(inner));
			switch (Keywords.convert(name)) {
				case MY_if:
					keyword = new MY_if(inner);
				break;
				case MY_function:
					keyword = new MY_function(inner);
				break;
				case MY_while:
					keyword = new MY_while(inner);
				break;
			}
			String parameters = StrUtils.getSection(first,'(',')');
			ArrayList<Object> newparams;
			boolean rm = false;
			try {
				do {
					if (rm)
						Interpreter.sysoutin = Interpreter.sysoutin.substring(0,
							Interpreter.sysoutin.length() - Interpreter.tabwith.length());
					rm = true;
					ArrayList<String> params = StrUtils.parts(parameters,',',true);
					newparams = new ArrayList<>();
					if (params.size() != keyword.getParametersize())
						throw new MethodParametersExeption(params.size(),keyword.getParametersize(),line);
					for (String param : params) {
						if (param.contains("+") || param.contains("-") || param.contains("*") || param.contains("/")) {
							newparams.add(params.indexOf(param),calculate(param));
						} else if (param.contains("=") || param.contains(">") || param.contains("<")
							|| param.contains("!")) {
							newparams.add(params.indexOf(param),booleanate(param));
						} else if (variables.get(param) != null) {
							newparams.add(params.indexOf(param),variables.get(param));
						} else {
							newparams.add(param);
						}
					}
					Interpreter.log(Interpreter.sysoutin + "running " + name + " with parameters" + newparams);
					Interpreter.sysoutin = Interpreter.sysoutin + Interpreter.tabwith;
				} while (keyword.execute(newparams));
				Interpreter.sysoutin = Interpreter.sysoutin.substring(0,
					Interpreter.sysoutin.length() - Interpreter.tabwith.length());
			} catch (BreakException e) {
				Interpreter.sysoutin = Interpreter.sysoutin.substring(0,
					Interpreter.sysoutin.length() - Interpreter.tabwith.length());
				Interpreter.log(Interpreter.sysoutin + "##################\n");
				throw e;
			}

		} else {
			String part = StrUtils.part(text.val,';',0);
			if (StrUtils.count(part,"\n") > 0)
				line += StrUtils.count(part,"\n");
			text.val = StrUtils.removetext(text.val,part + ";");
			part = StrUtils.fullstrip(part);
			Interpreter.log(sysoutin + "process: " + part);
			if (!part.contains("=") && part.contains("(")) { // Method
				String firs = StrUtils.first(part,'(');
				Method method = methods.get(firs);
				if (method != null) {
					Interpreter.log(sysoutin + "method: " + method);
					String parameters = StrUtils.getSection(part,'(',')');
					ArrayList<String> params = StrUtils.parts(parameters,',',true);
					ArrayList<Object> newparams = new ArrayList<>();
					if (params.size() != method.getParametersize())
						throw new MethodParametersExeption(params.size(),method.getParametersize(),line);
					for (String param : params) {
						Method met = methods.get(StrUtils.first(param,'('));
						if (met != null) {
							Interpreter.log(sysoutin + "method: " + met);
							String parameters2 = StrUtils.getSection(param,'(',')');
							ArrayList<String> params2 = StrUtils.parts(parameters2,',',true);
							ArrayList<Object> newparams2 = new ArrayList<>();
							if (params2.size() != met.getParametersize())
								throw new MethodParametersExeption(params2.size(),met.getParametersize(),line);
							for (String param2 : params2) {
								if (param2.contains("+") || param2.contains("-") || param2.contains("*")
									|| param2.contains("/")) {
									newparams2.set(params.indexOf(param2),calculate(param2));
								} else if (param2.contains("=") || param2.contains(">") || param2.contains("<")
									|| param2.contains("!")) {
									newparams2.add(params2.indexOf(param2),booleanate(param2));
								} else if (variables.get(param2) != null) {
									newparams2.add(params2.indexOf(param2),variables.get(param2));
								} else {
									newparams2.add(params2.indexOf(param2),param2);
								}
							}
							newparams.add(params.indexOf(param),met.execute(newparams2).getValue().toString());
						} else if (param.contains("+") || param.contains("-") || param.contains("*")
							|| param.contains("/")) {
							newparams.add(params.indexOf(param),calculate(param));
						} else if (param.contains("=") || param.contains(">") || param.contains("<")
							|| param.contains("!")) {
							newparams.add(params.indexOf(param),booleanate(param));
						} else if (variables.get(param) != null) {
							newparams.add(params.indexOf(param),variables.get(param));
						} else {
							newparams.add(params.indexOf(param),param);
						}
					}
					method.execute(newparams);
				} else {
					throw new MethodNotFoundExeption(firs,line);
				}
			} else { // Var
				String typestr = StrUtils.first(part,' ');
				Datatypes type = Datatypes.convert(typestr);
				if (type == null) { // var redef
					Variable<?> variable = variables.get(typestr);
					if (variable == null)
						throw new InvalidDatatypeExeption(typestr,line);

					System.out.println(sysoutin + "name: " + variable.name);
					String value = StrUtils.part(part,'=',1);
					Variable<?> set = variables.get(value);
					Method met = methods.get(StrUtils.first(value,'('));
					if (set != null) {
						value = set.getValue().toString();
						Interpreter.log(sysoutin + "value from variable: " + set);
					} else if (met != null) {
						Interpreter.log(sysoutin + "method: " + met);
						String parameters = StrUtils.getSection(part,'(',')');
						ArrayList<String> params = StrUtils.parts(parameters,',',true);
						ArrayList<Object> newparams = new ArrayList<>();
						if (params.size() != met.getParametersize())
							throw new MethodParametersExeption(params.size(),met.getParametersize(),line);
						for (String param : params) {
							if (param.contains("+") || param.contains("-") || param.contains("*")
								|| param.contains("/")) {
								newparams.add(params.indexOf(param),calculate(param));
							} else if (param.contains("=") || param.contains(">") || param.contains("<")
								|| param.contains("!")) {
								newparams.add(params.indexOf(param),booleanate(param));
							} else if (variables.get(param) != null) {
								newparams.add(params.indexOf(param),variables.get(param));
							} else {
								newparams.add(params.indexOf(param),param);
							}
						}
						value = met.execute(newparams).getValue().toString();
					} else if (part.contains("+") || part.contains("-") || part.contains("*") || part.contains("/")) {
						value = calculate(value);
						Interpreter.log(sysoutin + "value: " + value);
					} else if (part.contains("=") || part.contains(">") || part.contains("<") || part.contains("!")) {
						value = booleanate(value);
						Interpreter.log(sysoutin + "value: " + value);
					} else
						Interpreter.log(sysoutin + "value: " + value);
					variable.changeValue(value);
				} else {// var definition
					Interpreter.log(sysoutin + "datatype: " + type);
					if (StrUtils.parts(StrUtils.part(part,'=',0),' ',true).size() > 2)
						throw new VariableDeclarationExeption(part,line);
					String name = StrUtils.part(part,' ',1);
					Interpreter.log(sysoutin + "name: " + name);
					if (checkname(name))
						throw new UnsupportetVariableNameExeption(name,line);
					if (part.contains("=")) {
						String value = StrUtils.part(part,'=',1);
						Variable<?> set = variables.get(value);
						Method met = methods.get(StrUtils.first(value,'('));
						if (set != null) {
							value = set.getValue().toString();
							Interpreter.log(sysoutin + "value from variable: " + set);
						} else if (met != null) {
							Interpreter.log(sysoutin + "method: " + met);
							String parameters = StrUtils.getSection(part,'(',')');
							ArrayList<String> params = StrUtils.parts(parameters,',',true);
							ArrayList<Object> newparams = new ArrayList<>();
							if (params.size() != met.getParametersize())
								throw new MethodParametersExeption(params.size(),met.getParametersize(),line);
							for (String param : params) {
								if (param.contains("+") || param.contains("-") || param.contains("*")
									|| param.contains("/")) {
									newparams.add(params.indexOf(param),calculate(param));
								} else if (param.contains("=") || param.contains(">") || param.contains("<")
									|| param.contains("!")) {
									newparams.add(params.indexOf(param),booleanate(param));
								} else if (variables.get(param) != null) {
									newparams.add(params.indexOf(param),variables.get(param));
								} else {
									newparams.add(params.indexOf(param),param);
								}
							}
							Interpreter.log(newparams);
							value = met.execute(newparams).getValue().toString();
						} else if (part.contains("+") || part.contains("-") || part.contains("*")
							|| part.contains("/")) {
							value = calculate(value);
							Interpreter.log(sysoutin + "value: " + value);
						} else if (part.contains("=") || part.contains(">") || part.contains("<")
							|| part.contains("!")) {
							value = booleanate(value);
							Interpreter.log(sysoutin + "value: " + value);
						} else
							Interpreter.log(sysoutin + "value: " + value);
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
							case MY_double:
								variable = new MY_double(value,name);
							break;
						}
						variables.addVariable(variable);
					} else {
						Interpreter.log(sysoutin + "value: " + "no value");
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
							case MY_double:
								variable = new MY_double(name);
							break;
						}
						variables.addVariable(variable);
					}
				}

			}
		}
		Interpreter.log(sysoutin + "##################\n");
	}

	public static void color(AbstractDocument document) {
		CustStr text = new CustStr(StrUtils.getText(document));
		Interpreter.log("interpreting:\n" + text + "\n\n");

		while (text.val.length() > 0) {
			try {
				colorBlock(text,document);
			} catch (CustomExeption e) {
				System.err.println("coloring line failed");
			}
		}
	}

	public enum Syles {
		datatype,variable,method,parameters,keyword,clear,compliernormal,compliererror
	}

	public static StyleContext context;

	static {
		context = new StyleContext();
		final Style datatype = context.addStyle("datatype",null);
		StyleConstants.setForeground(datatype,Color.BLUE);
		StyleConstants.setFontSize(datatype,15);
		final Style variable = context.addStyle("variable",null);
		StyleConstants.setForeground(variable,Color.YELLOW);
		StyleConstants.setFontSize(variable,15);
		final Style method = context.addStyle("method",null);
		StyleConstants.setForeground(method,Color.GREEN);
		StyleConstants.setFontSize(method,15);
		final Style parameters = context.addStyle("parameters",null);
		StyleConstants.setForeground(parameters,Color.ORANGE);
		StyleConstants.setFontSize(parameters,15);
		final Style keyword = context.addStyle("keyword",null);
		StyleConstants.setForeground(keyword,Color.RED);
		StyleConstants.setFontSize(keyword,15);
		final Style clean = context.addStyle("clean",null);
		StyleConstants.setForeground(clean,Color.BLACK);
		StyleConstants.setFontSize(clean,15);
		final Style compliernormal = context.addStyle("compliernormal",null);
		StyleConstants.setForeground(compliernormal,Color.BLACK);
		StyleConstants.setFontSize(compliernormal,12);
		final Style compliererror = context.addStyle("compliererror",null);
		StyleConstants.setForeground(compliererror,Color.RED);
		StyleConstants.setFontSize(compliererror,12);
	}

	/*
	 * int i = 12; while (i > 0) { print(3); i = i - 2; delay(1); }
	 * 
	 */

	public static void insertText(AbstractDocument doc,String str,boolean indent,Interpreter.Syles style) {
		// System.out.println("inserting " + str + " with " + style);
		try {
			switch (style) {
				case datatype:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("datatype"));
				break;
				case keyword:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("keyword"));
				break;
				case method:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("method"));
				break;
				case parameters:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("parameters"));
				break;
				case variable:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("variable"));
				break;
				case clear:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("clean"));
				break;
				case compliererror:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("compliererror"));
				break;
				case compliernormal:
					doc.insertString(doc.getLength(),(indent ? Interpreter.indent : "") + str,
						context.getStyle("compliernormal"));
				break;
				default:
				break;
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	static String indent = "";

	public static void colorBlock(CustStr text,AbstractDocument doc) throws CustomExeption {
		insertText(doc,"\n",false,Syles.clear);
		if (text.val.isBlank()) {
			text.val = "";
			return;
		}
		Interpreter.log(sysoutin + "##################");

		if (StrUtils.startswith(StrUtils.fullstrip(text.val),Keywords.values())) { // keyword
			String first = StrUtils.first(text.val,'{');
			first = StrUtils.fullstrip(first);
			Interpreter.log(sysoutin + "process: " + StrUtils.removespace(first));

			String name = StrUtils.first(first,'(');
			Interpreter.log(sysoutin + "name: " + name);

			insertText(doc,name,true,Syles.keyword);

			String inner = StrUtils.getSection(text.val,'{','}');

			text.val = StrUtils.removetext(text.val,first + inner);

			inner = inner.substring(1,inner.length() - 1);
			Interpreter.log(sysoutin + "inner block: " + StrUtils.removespace(inner));

			String parameters = StrUtils.getSection(first,'(',')');

			ArrayList<String> params = StrUtils.parts(parameters,',',true);
			for (String param : params) {
				if (variables.get(param) != null) {
					insertText(doc,param,false,Syles.variable);
				} else {
					insertText(doc,param,false,Syles.parameters);
				}
			}
			CustStr newtext = new CustStr(inner);

			indent += "    ";

			while (newtext.val.length() > 0) {
				try {
					colorBlock(newtext,doc);
				} catch (CustomExeption e) {
					break;
				}
			}

			indent = indent.substring(0,indent.length() - 4);

		} else {
			String part = StrUtils.part(text.val,';',0);
			text.val = StrUtils.removetext(text.val,part + ";");
			part = StrUtils.fullstrip(part);
			Interpreter.log(sysoutin + "process: " + part);

			if (!part.contains("=") && part.contains("(")) { // Method
				String name = StrUtils.first(part,'(');
				Method method = methods.get(name);

				if (method != null) {

					insertText(doc,name,true,Syles.method);

					Interpreter.log(sysoutin + "method: " + method);

					String parameters = StrUtils.getSection(part,'(',')');
					ArrayList<String> params = StrUtils.parts(parameters,',',true);

					for (String param : params) {
						Method met = methods.get(StrUtils.first(param,'('));
						if (met != null) {

							insertText(doc,param,false,Syles.method);

							Interpreter.log(sysoutin + "method: " + met);
							String parameters2 = StrUtils.getSection(param,'(',')');
							ArrayList<String> params2 = StrUtils.parts(parameters2,',',true);

							if (params2.size() != met.getParametersize())
								throw new MethodParametersExeption(params2.size(),met.getParametersize(),-1);
							for (String param2 : params2) {
								if (variables.get(param2) != null) {
									insertText(doc,param2,false,Syles.variable);
								} else {
									insertText(doc,param2,false,Syles.parameters);
								}
							}

						} else if (variables.get(param) != null) {
							insertText(doc,param,false,Syles.variable);
						} else {
							insertText(doc,param,false,Syles.parameters);
						}
					}
				} else {
					throw new MethodNotFoundExeption(name,-1);
				}
			} else { // Variable
				System.err.println("var");
				if (true)
					return;
				String typestr = StrUtils.first(part,' ');
				Datatypes type = Datatypes.convert(typestr);
				if (type == null) { // var redef
					Variable<?> variable = variables.get(typestr);
					if (variable == null)
						throw new InvalidDatatypeExeption(typestr,-1);

					System.out.println(sysoutin + "name: " + variable.name);
					String value = StrUtils.part(part,'=',1);
					Variable<?> set = variables.get(value);
					Method met = methods.get(StrUtils.first(value,'('));
					if (set != null) {
						value = set.getValue().toString();
						Interpreter.log(sysoutin + "value from variable: " + set);
					} else if (met != null) {
						Interpreter.log(sysoutin + "method: " + met);
						String parameters = StrUtils.getSection(part,'(',')');
						ArrayList<String> params = StrUtils.parts(parameters,',',true);
						ArrayList<Object> newparams = new ArrayList<>();
						if (params.size() != met.getParametersize())
							throw new MethodParametersExeption(params.size(),met.getParametersize(),-1);
						for (String param : params) {
							if (param.contains("+") || param.contains("-") || param.contains("*")
								|| param.contains("/")) {
								newparams.add(params.indexOf(param),calculate(param));
							} else if (param.contains("=") || param.contains(">") || param.contains("<")
								|| param.contains("!")) {
								newparams.add(params.indexOf(param),booleanate(param));
							} else if (variables.get(param) != null) {
								newparams.add(params.indexOf(param),variables.get(param));
							} else {
								newparams.add(params.indexOf(param),param);
							}
						}
						// value = met.execute(newparams).getValue().toString();
					} else if (part.contains("+") || part.contains("-") || part.contains("*") || part.contains("/")) {
						value = calculate(value);
						Interpreter.log(sysoutin + "value: " + value);
					} else if (part.contains("=") || part.contains(">") || part.contains("<") || part.contains("!")) {
						value = booleanate(value);
						Interpreter.log(sysoutin + "value: " + value);
					} else
						Interpreter.log(sysoutin + "value: " + value);
					variable.changeValue(value);
				} else {// var definition
					Interpreter.log(sysoutin + "datatype: " + type);
					if (StrUtils.parts(StrUtils.part(part,'=',0),' ',true).size() > 2)
						throw new VariableDeclarationExeption(part,-1);
					String name = StrUtils.part(part,' ',1);
					Interpreter.log(sysoutin + "name: " + name);
					if (checkname(name))
						throw new UnsupportetVariableNameExeption(name,-1);
					if (part.contains("=")) {
						String value = StrUtils.part(part,'=',1);
						Variable<?> set = variables.get(value);
						Method met = methods.get(StrUtils.first(value,'('));
						if (set != null) {
							value = set.getValue().toString();
							Interpreter.log(sysoutin + "value from variable: " + set);
						} else if (met != null) {
							Interpreter.log(sysoutin + "method: " + met);
							String parameters = StrUtils.getSection(part,'(',')');
							ArrayList<String> params = StrUtils.parts(parameters,',',true);
							ArrayList<Object> newparams = new ArrayList<>();
							if (params.size() != met.getParametersize())
								throw new MethodParametersExeption(params.size(),met.getParametersize(),-1);
							for (String param : params) {
								if (param.contains("+") || param.contains("-") || param.contains("*")
									|| param.contains("/")) {
									newparams.add(params.indexOf(param),calculate(param));
								} else if (param.contains("=") || param.contains(">") || param.contains("<")
									|| param.contains("!")) {
									newparams.add(params.indexOf(param),booleanate(param));
								} else if (variables.get(param) != null) {
									newparams.add(params.indexOf(param),variables.get(param));
								} else {
									newparams.add(params.indexOf(param),param);
								}
							}
							Interpreter.log(newparams);
							// value = met.execute(newparams).getValue().toString();
						} else if (part.contains("+") || part.contains("-") || part.contains("*")
							|| part.contains("/")) {
							value = calculate(value);
							Interpreter.log(sysoutin + "value: " + value);
						} else if (part.contains("=") || part.contains(">") || part.contains("<")
							|| part.contains("!")) {
							value = booleanate(value);
							Interpreter.log(sysoutin + "value: " + value);
						} else
							Interpreter.log(sysoutin + "value: " + value);
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
							case MY_double:
								variable = new MY_double(value,name);
							break;
						}
						variables.addVariable(variable);
					} else {
						Interpreter.log(sysoutin + "value: " + "no value");
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
							case MY_double:
								variable = new MY_double(name);
							break;
						}
						variables.addVariable(variable);
					}
				}

			}
		}
		Interpreter.log(sysoutin + "##################\n");
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
					}
				}
				// Interpreter.log(solution);
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
			} catch (NumberFormatException e) {
			}
		} catch (NumberFormatException e) {
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
			} catch (NumberFormatException e) {
				throw new CalculationExeption(f + "-" + l,"-",Datatypes.MY_double,Datatypes.MY_String,line);
			}
		} catch (NumberFormatException e) {
			try {
				double li = Double.parseDouble(l);
				return f.substring(0,(int) (f.length() - li));
			} catch (NumberFormatException e2) {
				throw new CalculationExeption(f + "-" + l,"-",Datatypes.MY_String,Datatypes.MY_String,line);
			}
		}
	}

	private static String mulitiply(String f,String l) throws CalculationExeption {
		try {
			double fi = Double.parseDouble(f); // f int
			try {
				double li = Integer.parseInt(l); // l int
				return String.valueOf(fi * li);
			} catch (NumberFormatException e) { // f int l not
				if (l.startsWith("\"") && l.endsWith("\""))
					l = f.substring(1,l.length() - 1);
				String build = "";
				for (int i = 0; i < fi; i++) {
					build += l;
				}
				return build;
			}
		} catch (NumberFormatException e) { // f no int
			try {
				double li = Double.parseDouble(l); // l int f not
				if (f.startsWith("\"") && f.endsWith("\""))
					f = f.substring(1,f.length() - 1);
				String build = "";
				for (int i = 0; i < li; i++) {
					build += f;
				}
				return build;
			} catch (NumberFormatException e1) { // l and f no int
				throw new CalculationExeption(f + "*" + l,"*",Datatypes.MY_String,Datatypes.MY_String,line);
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
				throw new CalculationExeption(f + "/" + l,"/",Datatypes.MY_double,Datatypes.MY_String,line);
			}
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(l);
				throw new CalculationExeption(f + "/" + l,"/",Datatypes.MY_String,Datatypes.MY_double,line);
			} catch (NumberFormatException e2) {
				throw new CalculationExeption(f + "/" + l,"/",Datatypes.MY_String,Datatypes.MY_String,line);
			}
		}
	}

	private static String booleanate(String inp) throws CalculationExeption {
		inp = inp.replace(" ","");
		String builder = "";
		String operator = "";
		String solution = "";
		for (int i = 0; i < inp.length(); i++) {
			char c = inp.charAt(i);
			if (c == '=' || c == '>' || c == '<' || c == '!' || i == inp.length() - 1) {
				if (i == inp.length() - 1)
					builder += c;
				Variable<?> variable = variables.get(builder);
				if (variable != null) {
					builder = variable.getValue().toString();
				}
				// System.out.print(solution + operator + builder + "=");
				if (operator == "") {
					solution = builder;
				} else {
					if (operator.equals(">")) {
						solution = bigger(solution,builder);
					} else if (operator.equals("<")) {
						solution = smaller(solution,builder);
					} else if (operator.equals("=>")) {
						solution = biggerequals(solution,builder);
					} else if (operator.equals("=<")) {
						solution = smallerequals(solution,builder);
					} else if (operator.equals("==")) {
						solution = equals(solution,builder);
					} else if (operator.equals("=!")) {
						solution = notequals(solution,builder);
					} else if (operator.equals("!")) {
						solution = not(builder);
					} else {
						operator += c;
					}
				}
				// Interpreter.log(solution);
				builder = "";
				operator = "" + c;
			} else {
				builder += c;
			}
		}
		return solution;
	}

	private static String equals(String f,String l) {
		if (f.startsWith("\"") && f.endsWith("\""))
			f = f.substring(1,f.length() - 1);
		if (l.startsWith("\"") && l.endsWith("\""))
			l = l.substring(1,l.length() - 1);
		return String.valueOf(f == l);
	}

	private static String notequals(String f,String l) {
		if (f.startsWith("\"") && f.endsWith("\""))
			f = f.substring(1,f.length() - 1);
		if (l.startsWith("\"") && l.endsWith("\""))
			l = l.substring(1,l.length() - 1);
		return String.valueOf(f != l);
	}

	private static String bigger(String f,String l) {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi > li);
			} catch (NumberFormatException e) {
				return String.valueOf(fi > l.length());
			}
		} catch (NumberFormatException e) {
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(f.length() > li);
			} catch (NumberFormatException e2) {
				return String.valueOf(f.length() > l.length());
			}
		}
	}

	private static String smaller(String f,String l) {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi < li);
			} catch (NumberFormatException e) {
				return String.valueOf(fi < l.length());
			}
		} catch (NumberFormatException e) {
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(f.length() < li);
			} catch (NumberFormatException e2) {
				return String.valueOf(f.length() < l.length());
			}
		}

	}

	private static String smallerequals(String f,String l) {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi <= li);
			} catch (NumberFormatException e) {
				return String.valueOf(fi <= l.length());
			}
		} catch (NumberFormatException e) {
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(f.length() <= li);
			} catch (NumberFormatException e2) {
				return String.valueOf(f.length() <= l.length());
			}
		}

	}

	private static String biggerequals(String f,String l) {
		try {
			double fi = Double.parseDouble(f);
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(fi >= li);
			} catch (NumberFormatException e) {
				return String.valueOf(fi >= l.length());
			}
		} catch (NumberFormatException e) {
			try {
				double li = Double.parseDouble(l);
				return String.valueOf(f.length() >= li);
			} catch (NumberFormatException e2) {
				return String.valueOf(f.length() >= l.length());
			}
		}

	}

	private static String not(String l) throws CalculationExeption {
		try {
			return String.valueOf(!Boolean.parseBoolean(l));
		} catch (NumberFormatException e) {
			throw new CalculationExeption("!" + l,"!",Datatypes.notype,Datatypes.MY_String,line);
		}
	}

	private static boolean checkname(String name) {
		return Keywords.contains(name) != null || Datatypes.convert(name) != null || name.contains("+")
			|| name.contains("-") || name.contains("*") || name.contains("/");
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
			return document.getText(0,document.getLength());
		} catch (BadLocationException e) {
			return "";
		}
	}

	public static String fullstrip(String part) {
		part = part.strip();
		char[] chars = new char[part.length()];
		part.getChars(0,part.length(),chars,0);
		String out = "";
		for (char ch : chars) {
			if (!(ch == '\n' || ch == '\t'))
				out += ch;
			else
				out += " ";
		}
		return out;
	}

	public static String removespace(String part) {
		part = fullstrip(part);
		char[] chars = new char[part.length()];
		part.getChars(0,part.length(),chars,0);
		String out = "";
		int spacecounter = 0;
		for (char ch : chars) {
			if (ch == ' ') {
				spacecounter++;
				if (!(spacecounter > 1)) {
					out += ch;
					spacecounter = 0;
				}
			} else {
				out += ch;
			}
		}
		return out;
	}

	public static boolean startswith(String text,Keywords...keywords) {
		for (Keywords keyword : keywords) {
			if (text.startsWith(Keywords.convert(keyword)))
				return true;
		}
		return false;
	}

	public static String removetext(String text,String ret) {
		return text.strip().substring(Math.min(ret.strip().length(),text.strip().length()),text.strip().length());
	}

	public static String first(String text,char split) {
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		String out = "";
		for (char ch : chars) {
			if (ch != split)
				out += ch;
			else
				return fullstrip(out);
		}
		return fullstrip(text);
	}

	public static ArrayList<String> parts(String text,char split,boolean strip) {
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		String build = "";
		ArrayList<String> parts = new ArrayList<>();
		for (char ch : chars) {
			if (ch != split)
				build += ch;
			else {
				if (build.length() > 0)
					if (strip)
						parts.add(fullstrip(build));
					else
						parts.add(build);
				build = "";
			}
		}
		if (strip)
			parts.add(fullstrip(build));
		else
			parts.add(build);
		build = "";
		return parts;
	}

	public static String part(String text,char split,int ind) {
		return parts(text,split,true).get(ind);
	}

	public static String getSection(String text,char open,char close) throws BracketExeption {
		int bracketcounter = 0;
		boolean firstfound = false;
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		String builder = "";
		for (char ch : chars) {
			if (ch == open) {
				bracketcounter++;
				if (!firstfound) {
					firstfound = true;
					continue;
				}
			} else if (ch == close) {
				bracketcounter--;
			}
			if (firstfound && bracketcounter == 0)
				return builder;
			if (firstfound)
				builder += ch;

		}
		throw new BracketExeption(bracketcounter,Interpreter.line);
	}

	public static int count(String text,String split) {
		int count = 0;
		char[] chars = new char[text.length()];
		text.getChars(0,text.length(),chars,0);
		char[] test = new char[split.length()];
		split.getChars(0,split.length(),test,0);
		int testcounter = 0;
		for (char ch : chars) {
			if (ch == test[testcounter]) {
				testcounter++;
				if (testcounter == test.length) {
					count++;
					testcounter = 0;
				}
			}
		}
		return count;
	}

}
