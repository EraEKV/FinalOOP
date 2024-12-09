package Academic ;


import java.util.Date;
import java.util.Vector;

import Enums.Mark;
import Users.Student;
import Users.Teacher;
import Enums.Status;
import Research.ResearchPaper;
import Enums.Speciality;
import Enums.Faculty;


public class DiplomaProject extends Student {
	private String topic;
	private Teacher supervisor;
	private Date endDate;
	private Status status;
	private boolean isPassed;
	private Mark grade;
	private Vector<ResearchPaper> researchPapers;

	public DiplomaProject(String id, Faculty faculty, int startYear, Speciality speciality,
						  String topic, Teacher supervisor, Date endDate) {
		super(id, faculty, startYear, speciality);
		this.topic = topic;
		this.supervisor = supervisor;
		this.endDate = endDate;
		this.status = Status.IN_PROGRESS; //
		this.isPassed = false;
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

	public boolean isPassed() {
		return isPassed;
	}

	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
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
		this.status = Status.SUBMITTED;
		System.out.println("Diploma project submitted.");
	}

	public void evaluate(boolean passed, Mark grade) {
		this.isPassed = passed;
		this.grade = grade;
		this.status = passed ? Status.APPROVED : Status.REJECTED;
		System.out.println("Diploma project evaluated with grade: " + grade);
	}

	@Override
	public String toString() {
		return "DiplomaProject [topic=" + topic + ", supervisor=" + supervisor + ", endDate=" + endDate +
				", status=" + status + ", isPassed=" + isPassed + ", grade=" + grade +
				", researchPapers=" + researchPapers + "]";
	}
}