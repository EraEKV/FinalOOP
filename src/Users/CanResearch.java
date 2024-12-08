package Users;

import Academic.Journal;
import Research.ResearchPaper;
import Enums.Faculty;
import System.Request;
import java.util.Vector;

public interface CanResearch {

	int calculateHIndex();
	int calculateCitations();
	Vector<ResearchPaper> printPapers(Request request);
	void publishPaper(ResearchPaper paper, Journal journal);
	void topCitedSchoolResearcher(Faculty faculty);
	void topCitedResearcher();
}
