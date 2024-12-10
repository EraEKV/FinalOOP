package System;

import Enums.Status;
import Enums.Urgency;
import Users.Student;
import Users.User;

import java.util.Date;

public class Complaint extends Notification {
    private Urgency urgency;
    private Student student;
    private Status status;


    public Complaint() {

    }

    public Complaint(User author, Student student, String text, Urgency urgency) {
        super(author, text);
        this.student = student;
        this.urgency = urgency;
        this.status = Status.DELIVERED;
    }



//  accessors

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public Urgency getUrgency() {
        return urgency;
    }



    @Override
    public boolean equals(Object o) {
        if(!super.equals(o)) return false;
        Complaint c = (Complaint) o;
        return urgency == c.urgency
                && status == c.status;
    }

    @Override
    public int hashCode() {
        int res = super.hashCode();
        res = 31 * res + (urgency != null ? urgency.hashCode() : 0);
        res = 31 * res + (status != null ? status.hashCode() : 0);

        return res;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", urgency=" + urgency
                + ", status=" + status;
    }
}
