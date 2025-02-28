package System;

import Users.Employee;
import Users.User;

import java.io.Serializable;
import java.util.Objects;

public class Message extends Notification implements Serializable {
    private boolean isWatched;


//  constructors

    public Message(User author) {
        super(author);
    }

    public Message(User author, String text) {
        super(author, text);
        isWatched = false;
    }


//  accessors

    public void setWatched() {
        isWatched = true;
    }

    public boolean getWatched() {
        return isWatched;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o) && o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;

        return Objects.equals(isWatched, message.isWatched);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isWatched);
    }

    @Override
    public String toString() {
        return super.toString()
                + ", isWatched=" + isWatched;
    }
}
