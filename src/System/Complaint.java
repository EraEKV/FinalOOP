package System;

import Enums.Status;
import Enums.Urgency;
import Users.Student;

public class Complaint extends Notification {
    private Urgency urgency;
    private Student student;
    private Status status;

    public Complaint() {

    }

    public Complaint(Student student, Urgency urgency) {
        this.student = student;
        this.urgency = urgency;
        this.status = Status.DELIVERED;
    }




    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
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
