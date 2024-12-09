package Research;


import Academic.Journal;
import Enums.Faculty;

import java.util.Vector;

public class Researcher implements CanResearch {

    public Researcher() {

    }

    @Override
    public int calculateHIndex() {
        return 0;
    }

    @Override
    public int calculateCitations() {
        return 0;
    }

    @Override
    public void publishPaper(ResearchPaper paper, Journal journal) {

    }

    @Override
    public void topCitedSchoolResearcher(Faculty faculty) {

    }

    @Override
    public void topCitedResearcher() {

    }

    @Override
    public Vector<ResearchPaper> printPapers(System.Request request) {
        return null;
    }
}
