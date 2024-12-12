package Research;

import Enums.Faculty;
import java.util.Comparator;
import java.util.List;

public interface CanResearch {
	int calculateHIndex();
	int calculateCitations();
	void printPapers(Comparator c);
	void publishPaper(ResearchPaper paper, ResearchJournal journal);
	void topCitedSchoolResearcher(Faculty faculty);
	void topCitedResearcher();
}
