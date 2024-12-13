package Users;

import java.util.Vector;

import Database.Database;
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

    public void setComplaints(Vector<Complaint> complaints) {
        this.complaints = complaints;
    }

    //    methods
    public void makeWarning(Student student, Complaint complaint) {
        Vector<Complaint> currentComplaints = student.getWarnings();
        currentComplaints.add(complaint);
        student.setWarnings(currentComplaints);
        System.out.println(student + " was got warning for " + complaint);
    }

    public void kick(Student student) {
        Vector<Student> students = Database.getInstance().getStudents();
        students.remove(student);
        student = null;
        System.out.println(student + " was kicked");
    }

    public void reviewCase(Complaint complaint) {
        System.out.println(complaint);
    }

    public void deleteOrganization(Organization organization) {
        Vector<Organization> organizations = Database.getInstance().getStudentOrganizations();

        if (organizations.remove(organization)) {
            System.out.println("Organization " + organization + " removed successfully.");
        } else {
            System.out.println("Organization not found.");
        }
    }



    @Override
    public String toString() {
        return "DisciplinaryCommittee{" +
                "complaints=" + complaints +
                '}';
    }
}
