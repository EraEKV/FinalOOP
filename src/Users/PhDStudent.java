package Users;

public class PhDStudent extends GradStudent {
	private String dissertationTopic;

	public PhDStudent(String name, int age, String researchTopic, String dissertationTopic) {
		super(name, age, researchTopic);
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
		System.out.println(getName() + " is conducting PhD research on " + dissertationTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Dissertation Topic: " + dissertationTopic;
	}
}
