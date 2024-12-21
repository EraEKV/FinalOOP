package Research;

import Database.Database;
import Enums.Faculty;

import java.io.Serializable;
import java.util.*;

public class Researcher implements CanResearch, Serializable, Subscriber {
    private String pseudonym;
    private int citations;
    private List<ResearchPaper> papers = new ArrayList<>();
    private Faculty faculty;

    public Researcher() {}

    public Researcher(String pseudonym, Faculty faculty) {
        this.pseudonym = pseudonym;
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public int getCitations() {
        return citations;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public int calculateHIndex() {
        papers.sort((p1, p2) -> Integer.compare(p2.getCitations().size(), p1.getCitations().size()));

        int hIndex = 0;
        for (int i = 0; i < papers.size(); i++) {
            if (papers.get(i).getCitations().size() >= i + 1) {
                hIndex = i + 1;
            } else {
                break;
            }
        }
        return hIndex;
    }

    @Override
    public int calculateCitations() {
        return papers.stream().mapToInt(paper -> paper.getCitations().size()).sum();
    }


    @Override
    public void printPapers(Comparator c) {
        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }

    public void updateCitations() {
        citations = 0;
        for (ResearchPaper paper : papers) {
            citations += paper.getCitations().size();
        }
    }

    public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
        papers.add(paper);
        journal.getResearchPapers().add(paper);
        updateCitations();
    }

    public void printPapers() {
        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }

    public static void topCitedSchoolResearcher(Faculty faculty) {
        Researcher topResearcher = null;
        int maxCitations = 0;

        for (ResearchJournal journal : Database.getInstance().getResearchJournals()) {
            for (ResearchPaper paper : journal.getResearchPapers()) {
                if (paper.getMainAuthor().getFaculty().equals(faculty)) {
                    int paperCitations = paper.getCitations().size();
                    if (paperCitations > maxCitations) {
                        maxCitations = paperCitations;
                        topResearcher = paper.getMainAuthor();
                    }
                }
            }
        }

        if (topResearcher != null) {
            System.out.println(topResearcher);
        } else {
            System.out.println("No researchers found for the given faculty.");
        }
    }

    public void topCitedResearcher() {
        Researcher topResearcher = null;
        int maxCitations = 0;

        for (ResearchJournal journal : Database.getInstance().getResearchJournals()) {
            for (ResearchPaper paper : journal.getResearchPapers()) {
                int paperCitations = paper.getCitations().size();
                if (paperCitations > maxCitations) {
                    maxCitations = paperCitations;
                    topResearcher = paper.getMainAuthor();
                }
            }
        }

        if (topResearcher != null) {
            System.out.println(topResearcher);
        } else {
            System.out.println("No researchers found.");
        }
    }

    @Override
    public String toString() {
        return "Researcher{" +
                "pseudonym='" + pseudonym + '\'' +
                ", citations=" + citations +
                ", papers=" + papers.size() +
                '}';
    }

    @Override
    public void update() {

    }
}
