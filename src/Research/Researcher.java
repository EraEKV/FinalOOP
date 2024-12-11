package Research;

import Enums.Faculty;
import Users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Researcher {

    List<ResearchPaper> papers;

    public Researcher() {
        papers = new ArrayList<>();
    }

    public int calculateHIndex() {
        return 0;
    }

//    public int calculateCitations() {
//        return 0;
//    }

    public void publishPaper(ResearchPaper paper, ResearchJournal journal) {
        papers.add(paper);
        Vector<ResearchPaper> prev = journal.getResearchPapers();
        prev.add(paper);
        journal.setResearchPapers(prev);
    }

    public List<ResearchPaper> printPapers(Comparator<ResearchPaper> comparator) {
        papers.sort(comparator);
        return papers;
    }

//    public void topCitedSchoolResearcher(Faculty faculty) {
//
//    }
//
//    public void topCitedResearcher() {
//
//    }
}
