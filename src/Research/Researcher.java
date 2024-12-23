package Research;

import Database.Database;
import Enums.Faculty;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a researcher who can conduct research, publish papers, and calculate metrics like h-index and citation count.
 * This class interacts with a research database, journals, and papers.
 */
public class Researcher implements CanResearch, Serializable {
    private int citationsCount;
    private List<ResearchPaper> papers = new ArrayList<>();
    private CanBeResearcher academicContributor;

    public Researcher() {}

    public Researcher(CanBeResearcher academicContributor) {
        this.academicContributor = academicContributor;
        Database.getInstance().getResearchers().add(this);
    }

    public int getCitationsCount() {
        return citationsCount;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    /**
     * Calculates the researcher's h-index.
     * The h-index is the maximum value such that the researcher has at least h papers with h or more citations.
     *
     * @return the h-index of the researcher
     */
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

    /**
     * Calculates the total number of citations for all the researcher's papers.
     *
     * @return the total number of citations
     */
    @Override
    public int calculateCitations() {
        return papers.stream().mapToInt(paper -> paper.getCitations().size()).sum();
    }

    public CanBeResearcher getAcademicContributor() {
        return academicContributor;
    }

    /**
     * Prints all research papers sorted using the provided comparator.
     *
     * @param c the comparator to sort the papers
     */
    @Override
    public void printPapers(Comparator c) {
        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }

    /**
     * Updates the total citation count for the researcher by recalculating it from the papers.
     */
    public void updateCitations() {
        citationsCount = 0;
        for (ResearchPaper paper : papers) {
            citationsCount += paper.getCitations().size();
        }
    }

    /**
     * Publishes a research paper in a specified journal and updates the citation count.
     *
     * @param paper   the research paper to publish
     * @param journal the journal in which the paper is published
     */
    public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
        papers.add(paper);
        journal.getResearchPapers().add(paper);
        updateCitations();
    }

    /**
     * Prints all research papers authored by the researcher.
     */
    public void printPapers() {
        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }

    /**
     * Finds and prints the top-cited researcher within a specific faculty.
     *
     * @param faculty the faculty to filter researchers
     */
    public static void topCitedSchoolResearcher(Faculty faculty) {
        Researcher topResearcher = null;
        int maxCitations = 0;

        for (ResearchJournal journal : Database.getInstance().getResearchJournals()) {
            for (ResearchPaper paper : journal.getResearchPapers()) {
                if (paper.getMainAuthor().getAcademicContributor().getFaculty().equals(faculty)) {
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

    /**
     * Finds and prints the top-cited researcher among all researchers.
     */
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

    /**
     * Returns a string representation of the Researcher object.
     *
     * @return a string containing details about the researcher
     */
    @Override
    public String toString() {
        return "Researcher{" +
                "citationsCount=" + citationsCount +
                ", papers=" + papers +
                ", academicContributor=" + academicContributor +
                '}';
    }
}
