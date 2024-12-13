package Users;
import Enums.Speciality;
import Enums.Faculty;

public class MasterStudent extends GradStudent {
	private String thesisTopic;

	public MasterStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, String thesisTopic, Teacher teacher) {
		super(id, firstname, lastname, faculty, speciality, teacher);
		this.thesisTopic = thesisTopic;
	}

	public String getThesisTopic() {
		return thesisTopic;
	}

	public void setThesisTopic(String thesisTopic) {
		this.thesisTopic = thesisTopic;
	}

	@Override
	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on Master's thesis about " + thesisTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Thesis Topic: " + thesisTopic;
	}
}
