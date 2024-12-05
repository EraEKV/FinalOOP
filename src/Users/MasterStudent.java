package Users;

public class MasterStudent extends GradStudent {
	private String thesisTopic;

	public MasterStudent(String name, int age, String researchTopic, String thesisTopic) {
		super(name, age, researchTopic);
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
		System.out.println(getName() + " is conducting research on Master's thesis about " + thesisTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Thesis Topic: " + thesisTopic;
	}
}
