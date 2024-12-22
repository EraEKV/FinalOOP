package Users;

import Enums.Faculty;
import Enums.Speciality;
import java.util.Vector;

public class GradStudent extends Student  {
	private String researchTopic;
	private Vector<String> publications;
	private Teacher teacher;

	{
		super.beResearcher();
	}

	public GradStudent() {
	}

	public GradStudent(String id) {
		super(id);
	}

	public GradStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, Teacher teacher) {
		super(id, firstname, lastname, faculty, speciality);
		this.publications = new Vector<>();
		this.teacher = teacher;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public String getResearchTopic() {
		return researchTopic;
	}

	public void setResearchTopic(String researchTopic) {
		this.researchTopic = researchTopic;
	}

	public Vector<String> getPublications() {
		return publications;
	}

	public void addPublication(String publication) {
		publications.add(publication);
	}

	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on " + researchTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Research Topic: " + researchTopic;
	}
}

