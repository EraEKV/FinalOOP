package Academic;

import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import Enums.Mark;
import Users.Teacher;
import Enums.Status;
import Research.ResearchPaper;

public class DiplomaProject {
	private String topic;
	private Teacher supervisor;
	private Date endDate;
	private Status status;
	private Mark grade;
	private Vector<ResearchPaper> researchPapers;

	public DiplomaProject() {
		this.researchPapers = new Vector<>();
	}

	public DiplomaProject(String topic, Teacher supervisor, Date endDate) {
		this.topic = topic;
		this.supervisor = supervisor;
		this.endDate = endDate;
		this.grade = null;
		this.researchPapers = new Vector<>();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Teacher getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Teacher supervisor) {
		this.supervisor = supervisor;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Mark getGrade() {
		return grade;
	}

	public void setGrade(Mark grade) {
		this.grade = grade;
	}

	public Vector<ResearchPaper> getResearchPapers() {
		return researchPapers;
	}

	public void addResearchPaper(ResearchPaper paper) {
		this.researchPapers.add(paper);
	}

	public void submit() {
		this.status = Status.DELIVERED;
		System.out.println("Diploma project submitted.");
	}

	public void evaluate(boolean passed, Mark grade) {
		this.grade = grade;
		this.status = passed ? Status.DONE : Status.REJECTED;
		System.out.println("Diploma project evaluated with grade: " + grade);
	}

	@Override
	public String toString() {
		return "DiplomaProject [topic=" + topic + ", supervisor=" + supervisor + ", endDate=" + endDate +
				", status=" + status + ", grade=" + grade +
				", researchPapers=" + researchPapers + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(topic, supervisor, endDate, status, grade, researchPapers);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		DiplomaProject that = (DiplomaProject) obj;
		return Objects.equals(topic, that.topic) &&
				Objects.equals(supervisor, that.supervisor) &&
				Objects.equals(endDate, that.endDate) &&
				status == that.status &&
				grade == that.grade &&
				Objects.equals(researchPapers, that.researchPapers);
	}
}