package Users;
import Enums.Speciality;
import Enums.Faculty;

public class MasterStudent extends GradStudent {

	public MasterStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, Teacher teacher) {
		super(id, firstname, lastname, faculty, speciality, teacher);
	}

	@Override
	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on Master's thesis about ");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
