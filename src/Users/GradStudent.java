package Users;

import Academic.Journal;
import Research.CanResearch;
import Research.ResearchPaper;
import Enums.Faculty;
import Enums.Speciality;
import System.Request;
import java.util.Vector;

public class GradStudent extends Student implements CanTeach, CanResearch {
	private String researchTopic;
	private Vector<String> publications;
	private Vector<ResearchPaper> researchPapers;

	public GradStudent(String id, String firstname, String lastname, Faculty faculty, Speciality speciality, String researchTopic) {
		super(id, firstname, lastname, faculty, speciality);
		this.researchTopic = researchTopic;
		this.publications = new Vector<>();
		this.researchPapers = new Vector<>();
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

	@Override
	public Vector<ResearchPaper> printPapers(Request request) {
		Vector<ResearchPaper> filteredPapers = new Vector<>();
		String requestedTopic = request.getTopic();

		// Фильтрация по теме запроса
		for (ResearchPaper paper : researchPapers) {
			if (paper.getName().contains(requestedTopic)) {
				filteredPapers.add(paper);
			}
		}

		if (filteredPapers.isEmpty()) {
			System.out.println("No papers match the request.");
		}

		return filteredPapers;
	}

	@Override
	public void publishPaper(ResearchPaper paper, Journal journal) {
//		paper.setResearchJournal(journal);
//		journal.addPaper(paper);
//		researchPapers.add(paper);
	}

	@Override
	public void topCitedSchoolResearcher(Faculty faculty) {
		System.out.println("Finding top-cited researcher in " + faculty);
	}

	@Override
	public void topCitedResearcher() {
		System.out.println("Finding the top-cited researcher.");
	}

	@Override
	public void teach() {
		System.out.println(getFirstname() + " " + getLastname() + " is teaching.");
	}

	public void research() {
		System.out.println(getFirstname() + " " + getLastname() + " is conducting research on " + researchTopic);
	}

	@Override
	public String toString() {
		return super.toString() + ", Research Topic: " + researchTopic;
	}
}

