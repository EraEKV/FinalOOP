package Users;
import Enums.Speciality;
import Enums.Faculty;

/**
 * The PhDStudent class represents a graduate student pursuing a Ph.D. degree.
 * It extends the GradStudent class, inheriting properties and methods related to the student's ID, name, faculty, speciality, and assigned teacher.
 * The class overrides the `research` method to represent the Ph.D. research and provides a `toString` method to return the student’s details.
 */
public class PhDStudent extends GradStudent {

	public PhDStudent(String id) {
		super(id);
	}

	public PhDStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, Teacher teacher) {
		super(id, firstname, lastname, faculty, speciality, teacher);
	}


	@Override
	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting PhD research on ");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}

