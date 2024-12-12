package System;

import Users.User;

import java.util.Date;

public class Log {
    private String method;
    private Date date;
    private User user;


//  constructors

    public Log() {

    }

    public Log(String method, User user) {
        this.method = method;
        this.date = new Date();
        this.user = user;
    }



//  accessors

    public String getMethod() {
        return method;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }



    @Override
    public String toString() {
        return "Log{" + "method=" + method + ", date=" + date + ", user=" + user.getFirstname() + " " + user.getLastname() + '}';
    }
}
