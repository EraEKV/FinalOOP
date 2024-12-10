package Users;

import java.util.Vector;
import System.Complaint;
import System.Organization;

public class DisciplinaryCommittee extends Employee {

//    private int countOfMembers;

    private Vector<Complaint> complaints;



//    constructors

    public DisciplinaryCommittee() {
        Vector<Complaint> complaints = new Vector<>();
    }

    public DisciplinaryCommittee(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public DisciplinaryCommittee(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public DisciplinaryCommittee(String firstname, String lastname, int salary) {
        super(firstname, lastname, salary);
    }

    public DisciplinaryCommittee(String firstname, String lastname, String email, int salary) {
        super(firstname, lastname, email, salary);
    }



//    accessor

    public Vector<Complaint> getComplaints() {
        return complaints;
    }



//    methods

    public void makeWarning(Student student, Complaint complaint) {

    }

    public void kick(Student student) {

    }

    public void reviewCase(Complaint complaint) {

    }

    public void assignPenalty(Student student, Complaint complaint) {

    }

    public void deleteOrganization(Organization organization) {

    }


    @Override
    public String toString() {
        return "DisciplinaryCommittee{" +
                "complaints=" + complaints +
                '}';
    }
}
