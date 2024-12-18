package Users;
import Enums.Speciality;
import Enums.Faculty;

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

