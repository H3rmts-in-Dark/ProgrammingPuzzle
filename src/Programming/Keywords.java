package Programming;

public enum Keywords {

	MY_if, MY_function;

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
			return MY_if;
		case "function":
			return MY_function;
		default:
			break;
		}
		return null;
	}

	static String convert(Keywords keyword) {
		switch (keyword) {
		case MY_if:
			return "if";
		case MY_function:
			return "function";
		default:
			break;
		}
		return null;
	}

}
