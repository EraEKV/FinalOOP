package Research;

import Enums.Faculty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Researcher implements CanResearch{
    private int citations;
    List<ResearchPaper> papers;

    public Researcher() {
        papers = new ArrayList<>();
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

    public void topCitedSchoolResearcher(Faculty faculty) {

    }

    public void topCitedResearcher() {

    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }


}
