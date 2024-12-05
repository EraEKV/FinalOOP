package CustomExceptions;

public class UserTypeException extends RuntimeException {
    public UserTypeException(String message) {
        super(message);
    }
}
