package Comparators;

import Research.ResearchPaper;
import java.util.Comparator;

public class CitationComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2) {
        return Integer.compare(p2.getCitations().size(), p1.getCitations().size());
    }
};
