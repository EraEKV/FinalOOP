package Users;
import Enums.Speciality;
import Enums.Faculty;

public class PhDStudent extends GradStudent {
	private String dissertationTopic;

	public PhDStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, String researchTopic, String dissertationTopic) {
		super(id, firstname, lastname, faculty, speciality, researchTopic);
		this.dissertationTopic = dissertationTopic;
	}

	public String getDissertationTopic() {
		return dissertationTopic;
	}

	public void setDissertationTopic(String dissertationTopic) {
		this.dissertationTopic = dissertationTopic;
	}

	@Override
	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting PhD research on " + dissertationTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Dissertation Topic: " + dissertationTopic;
	}
}

