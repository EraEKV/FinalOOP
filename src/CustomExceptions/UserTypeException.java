package CustomExceptions;

public class UserTypeException extends RuntimeException {
    public <T> UserTypeException() {
        super("Ошибка с типом пользователя");
    }
}
