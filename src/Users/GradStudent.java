package Users;

import Academic.Journal;
import Research.CanResearch;
import Research.ResearchJournal;
import Research.ResearchPaper;
import Enums.Faculty;
import Enums.Speciality;
import System.Request;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class GradStudent extends Student implements CanResearch {
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

	public GradStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality) {
		super(id, firstname, lastname, faculty, speciality);
		this.publications = new Vector<>();
		this.papers = new List<>();
	}

	public GradStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, Teacher teacher) {
		super(id, firstname, lastname, faculty, speciality);
		this.publications = new Vector<>();
		this.researchPapers = new Vector<>();
		this.teacher = teacher;
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
	@Override
	public int calculateHIndex() {
		return publications.size(); // simplified for now
	}

	@Override
	public int calculateCitations() {
		int totalCitations = 0;
		for (ResearchPaper paper : researchPapers) {
			totalCitations += paper.getCitations().size(); // Using citations count instead of actual citation data
		}
		return totalCitations;
	}

	public int calculateHIndex() {
		return 0;
	}

	public int calculateCitations() {
		int totalCitations = 0;
		for (ResearchPaper paper : papers) {
			totalCitations += paper.getCitations().size();
		}
		return totalCitations;
	}

	public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
		papers.add(paper);
		Vector<ResearchPaper> prev = journal.getResearchPapers();
		prev.add(paper);
		journal.setResearchPapers(prev);
	}

	public void printPapers(Comparator comparator) {
		papers.sort(comparator);
		for (ResearchPaper paper : papers) {
			System.out.println(paper);
		}
	}

	@Override
	public void topCitedSchoolResearcher(Faculty faculty) {
		System.out.println("Finding top-cited researcher in " + faculty);
	}

	@Override
	public void topCitedResearcher() {
		System.out.println("Finding the top-cited researcher.");
	}


	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on " + researchTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Research Topic: " + researchTopic;
	}
}

