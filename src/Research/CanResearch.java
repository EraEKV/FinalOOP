package Research;

import Academic.Journal;
import Enums.Faculty;
import System.Request;

import java.util.Comparator;
import java.util.Vector;

public interface CanResearch {
	int calculateHIndex();
	int calculateCitations();
	Vector<ResearchPaper> printPapers(Comparator c);
	void publishPaper(ResearchPaper paper, Journal journal);
	void topCitedSchoolResearcher(Faculty faculty);
	void topCitedResearcher();
}
