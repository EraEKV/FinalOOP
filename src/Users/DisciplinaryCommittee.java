package Users;

import java.util.Vector;

import Database.Database;
import System.Complaint;
import System.Organization;

public class DisciplinaryCommittee extends Employee {

    private static DisciplinaryCommittee INSTANCE;

    private Vector<Complaint> complaints;

    private DisciplinaryCommittee() {
        this.complaints = new Vector<>();
    }

    public static DisciplinaryCommittee getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DisciplinaryCommittee();
        }
        return INSTANCE;
    }

    // Accessors
    public Vector<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(Vector<Complaint> complaints) {
        this.complaints = complaints;
    }

    // Methods

    /**
     * @author Abumuslim Abakarov
     * @param student
     * @param complaint
     */
    public void makeWarning(Student student, Complaint complaint) {
        student.getWarnings().add(complaint);
        System.out.println(student + " was got warning for " + complaint);
    }

    public void kick(Student student) {
        Vector<Student> students = Database.getInstance().getStudents();
        if (students.remove(student)) {
            System.out.println(student + " was kicked from the university.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void reviewCase(Complaint complaint) {
        System.out.println("Reviewing complaint: " + complaint);
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
