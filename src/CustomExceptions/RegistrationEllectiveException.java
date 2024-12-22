package CustomExceptions ;

public class RegistrationEllectiveException extends Exception {
	public RegistrationEllectiveException(int count) { super("On registration you cant register for " + count + " elective courses"); }
}

