package CustomExceptions;

public class InvalidAuthDataException extends RuntimeException {
    public InvalidAuthDataException() {
        super("Invalid email or password");
    }
}
