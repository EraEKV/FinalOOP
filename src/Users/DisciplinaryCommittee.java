package Users;

import java.util.PriorityQueue;
import java.util.Vector;

import Comparators.ComplaintComparator;
import Database.Database;
import System.Complaint;
import System.Organization;


/**
 * Represents a Disciplinary Committee that handles complaints and disciplinary actions
 * for students and organizations within a university system. This class follows the Singleton pattern
 * and ensures that only one instance of the Disciplinary Committee exists.
 */
public class DisciplinaryCommittee extends Employee {

    private static DisciplinaryCommittee INSTANCE;

    private PriorityQueue<Complaint> complaints;

    private DisciplinaryCommittee() {
        super("Disciplinary", "Committee", "committee@kbtu.kz");
        this.complaints = new PriorityQueue<>(new ComplaintComparator());
    }

    public static DisciplinaryCommittee getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DisciplinaryCommittee();
        }
        return INSTANCE;
    }

    // Accessors
    public PriorityQueue<Complaint> getComplaints() {
        return complaints;
    }



    // Methods

    /**
     * Issues a warning to a student for a specific complaint.
     *
     * @param student The student receiving the warning.
     * @param complaint The complaint for which the warning is issued.
     */
    public void makeWarning(Student student, Complaint complaint) {
        student.getWarnings().add(complaint);
        System.out.println(student + " was got warning for " + complaint);
    }

    /**
     * Kicks a student out of the university and removes them from the student database.
     *
     * @param student The student to be kicked out.
     */

    public void kick(Student student) {
        Vector<Student> students = Database.getInstance().getStudents();
        if (students.remove(student)) {
            System.out.println(student + " was kicked from the university.");
        } else {
            System.out.println("Student not found.");
        }
    }

    /**
     * Removes a student organization from the university system.
     *
     * @param organization The organization to be deleted.
     */

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
