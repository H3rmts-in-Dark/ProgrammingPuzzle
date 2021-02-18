package Programming;


class CustomExeption extends Throwable {

	public CustomExeption(String exeption,int line) {
		super(exeption + "  in Line: " + line);
	}

}



class InvalidValueException extends CustomExeption {

	public InvalidValueException(Object min,Object max,Object object,int line) {
		super(object + " was out of Range:  " + min + " - " + max,line);
	}

}



class InvalidDatatypeException extends CustomExeption {

	public InvalidDatatypeException(Object object,int line) {
		super(object + "was no valid Datatype",line);
	}

}



class WrongTypeException extends CustomExeption {

	public WrongTypeException(Datatypes type,Object object,int line) {
		super(object.toString() + " did not match " + type,line);
	}

}
