package CustomExceptions;

public class InsufficientAccessRightsException extends RuntimeException {
    public InsufficientAccessRightsException(String message) {
        super(message);
    }
}
