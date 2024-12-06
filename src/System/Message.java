package System;

import Users.Employee;
import Users.User;
import java.util.Objects;

public class Message extends Notification {
    private Employee receiver;
    public Message(User author){
        super(author);
    }
    public Message(User author, String text){
        super(text, author);
    }
    public Message(User author, String text,Employee receiver) {
        super(text, author);
        this.receiver = receiver;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public void setReceiver(Employee receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Message message = (Message) o;
        return Objects.equals(receiver, message.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), receiver);
    }

    @Override
    public String toString() {
        return "Message{" +
                "receiver=" + receiver +
                '}';
    }
}
