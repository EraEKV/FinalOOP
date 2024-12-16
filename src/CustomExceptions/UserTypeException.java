package CustomExceptions;

import java.io.Serializable;

public class UserTypeException extends RuntimeException implements Serializable {
    public <T> UserTypeException() {
        super("Ошибка с типом пользователя");
    }
}
