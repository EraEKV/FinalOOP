package Users;

import Enums.Faculty;
import Enums.Speciality;
import java.util.Vector;

/**
 * The GradStudent class represents a graduate student who is pursuing research
 * under the guidance of a teacher. It extends the Student class and includes
 * additional attributes and methods related to the research topic and publications.
 */
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
		this.publications = new Vector<>(); // Initialize the publications vector.
		this.teacher = teacher; // Set the teacher for the graduate student.
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

	/**
	 * Adds a publication to the list of publications for the graduate student.
	 * @param publication A string representing the publication to be added.
	 */
	public void addPublication(String publication) {
		publications.add(publication);
	}

	/**
	 * Simulates the research activity of the graduate student by printing a message.
	 * The message includes the student's name and research topic.
	 */
	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on " + researchTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Research Topic: " + researchTopic;
	}
}
