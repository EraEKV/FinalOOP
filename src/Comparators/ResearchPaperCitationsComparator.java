package Comparators;

import java.util.Comparator;
import Research.ResearchPaper;

public class ResearchPaperCitationsComparator implements Comparator<ResearchPaper> {
	public int compare(ResearchPaper r1, ResearchPaper r2) {
		return Integer.compare(r1.getCitations().size(), r2.getCitations().size());
	}
}

