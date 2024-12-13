package Users;

import Research.CanResearch;
import Research.ResearchJournal;
import Research.ResearchPaper;
import Enums.Faculty;
import Enums.Speciality;

import java.util.List;
import java.util.Vector;

public class GradStudent extends Student  {
	private String researchTopic;
	private Vector<String> publications;
	private Teacher teacher;

	private int citations;
	List<ResearchPaper> papers;

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




//	Researcher
//	@Override
//	public int calculateHIndex() {
//		return publications.size(); // simplified for now
//	}
//
//	@Override
//	public int calculateCitations() {
//		int totalCitations = 0;
//		for (ResearchPaper paper : researchPapers) {
//			totalCitations += paper.getCitations().size(); // Using citations count instead of actual citation data
//		}
//		return totalCitations;
//	}
//
//	public int calculateHIndex() {
//		return 0;
//	}
//
//	public int calculateCitations() {
//		int totalCitations = 0;
//		for (ResearchPaper paper : papers) {
//			totalCitations += paper.getCitations().size();
//		}
//		return totalCitations;
//	}
//
//	public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
//		papers.add(paper);
//		Vector<ResearchPaper> prev = journal.getResearchPapers();
//		prev.add(paper);
//		journal.setResearchPapers(prev);
//	}
//
//	public void printPapers(Comparator comparator) {
//		papers.sort(comparator);
//		for (ResearchPaper paper : papers) {
//			System.out.println(paper);
//		}
//	}



	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on " + researchTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Research Topic: " + researchTopic;
	}
}

