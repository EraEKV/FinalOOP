package Users;
import Enums.Speciality;
import Enums.Faculty;

/**
 * The MasterStudent class represents a graduate student pursuing a Master's degree.
 * It extends the GradStudent class, inheriting properties and methods related to the student's ID, name, faculty, speciality, and assigned teacher.
 * The class overrides the `research` method to represent the Master's thesis research and provides a `toString` method to return the student’s details.
 */
public class MasterStudent extends GradStudent {

	public MasterStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, Teacher teacher) {
		super(id, firstname, lastname, faculty, speciality, teacher);
	}

	/**
	 * Overrides the research method to define the research activity specific to Master's students.
	 * Prints a message indicating that the student is conducting research for their Master's thesis.
	 */

	@Override
	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on Master's thesis about ");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
