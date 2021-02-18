package Programming;


enum Datatypes {

	notype,all,MY_int,MY_long,MY_String,MY_boolean;

	static Datatypes contains(String type) {
		for (Datatypes datatype : Datatypes.values()) {
			if (datatype == convert(type)) {
				return datatype;
			}
		}
		return null;
	}

	static Datatypes convert(String str) {
		switch (str) {
			case "String":
				return MY_String;
			case "boolean":
				return MY_boolean;
			case "int":
				return MY_int;
			case "long":
				return MY_long;
		}
		return null;
	}

}



abstract class Variable<T> {

	protected Datatypes type;

	private T value;

	protected String name;

	public Variable(String name,Datatypes type) {
		this.name = name;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public void changeValue(Object object) {
		try {
			if (checkvalue(object))
				value = (T) object;
		} catch (InvalidValueException | WrongTypeException e) {
			e.printStackTrace();
		}
	}

	abstract boolean checkvalue(Object test) throws InvalidValueException,WrongTypeException;

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Name:" + name + " type: " + type + " value:" + getValue();
	}

	public String getName() {
		return name;
	}

}



class MY_String extends Variable<String> {

	public MY_String(String name) {
		this("",name);
	}

	public MY_String(Object value,String name) {
		super(name,Datatypes.MY_String);
		changeValue(value);
	}

	@Override
	public boolean checkvalue(Object test) throws InvalidValueException,WrongTypeException {
		if (test instanceof String) {
			if (((String) test).length() >= 0 && ((String) test).length() < 2147483647)
				return true;
			throw new InvalidValueException(0,2147483647,((String) test).length(),3);
		}
		throw new WrongTypeException(type,test,3);

	}

}



class MY_boolean extends Variable<Boolean> {

	public MY_boolean(String name) {
		this(false,name);
	}

	public MY_boolean(Object value,String name) {
		super(name,Datatypes.MY_boolean);
		changeValue(value);
	}

	@Override
	public boolean checkvalue(Object test) throws WrongTypeException,InvalidValueException {
		if (test instanceof Boolean) {
			if (((Boolean) test) == true || ((Boolean) test) == false)
				return true;
			throw new InvalidValueException(false,true,test,3);
		}
		throw new WrongTypeException(type,test,3);
	}

}



class MY_int extends Variable<Integer> {

	public MY_int(String name) {
		this(0,name);
	}

	public MY_int(Object value,String name) {
		super(name,Datatypes.MY_int);
		changeValue(value);
	}

	@Override
	public boolean checkvalue(Object test) throws InvalidValueException,WrongTypeException {
		if (test instanceof Integer) {
			if (((Integer) test) > Integer.MIN_VALUE && ((Integer) test) < Integer.MAX_VALUE)
				return true;
			throw new InvalidValueException(Integer.MIN_VALUE,Integer.MAX_VALUE,test,3);
		}
		throw new WrongTypeException(type,test,3);

	}

}



class MY_long extends Variable<Long> {

	public MY_long(String name) {
		this((long) 0,name);
	}

	public MY_long(Object value,String name) {
		super(name,Datatypes.MY_long);
		changeValue(value);
	}

	@Override
	public boolean checkvalue(Object test) throws InvalidValueException,WrongTypeException {
		try {
			Long longg = (long) test;
			if (longg > Long.MIN_VALUE && longg < Long.MAX_VALUE)
				return true;
			throw new InvalidValueException(Long.MIN_VALUE,Long.MAX_VALUE,test,3);
		} catch (ClassCastException e) {
			throw new WrongTypeException(type,test,3);
		}
	}

}
