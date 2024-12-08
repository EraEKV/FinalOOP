package CustomExceptions;

public class UserTypeException extends RuntimeException {
    public <T> UserTypeException(T type) {
        super("Ошибка с типом пользователя " + type);
    }
}
