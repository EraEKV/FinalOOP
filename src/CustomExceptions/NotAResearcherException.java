package CustomExceptions;

public class NotAResearcherException extends RuntimeException {
    public NotAResearcherException(String message) {
        super(message);
    }
}

