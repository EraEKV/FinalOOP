package Research;

import Database.Database;
import Enums.Faculty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import Comparators.CitationComparator;

public class Researcher implements CanResearch{
    private String pceudoname;
    private int citations;
    List<ResearchPaper> papers;
    {
        papers = new ArrayList<>();
    }
    public Researcher() {
    }

    public Researcher(String pceudoname) {
        this.pceudoname = pceudoname;
    }

    public String getPceudoname() {
        return pceudoname;
    }

    public void setPceudoname(String pceudoname) {
        this.pceudoname = pceudoname;
    }

    public int calculateHIndex() {
        CitationComparator citationComparator = new CitationComparator();
        papers.sort(citationComparator);

        int hIndex = 0;
        for (int i = 0; i < papers.size(); i++) {
            int citations = papers.get(i).getCitations().size();
            if (citations >= (i + 1)) {
                hIndex = i + 1;
            } else {
                break;
            }
        }

        return hIndex;
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

    public static void topCitedSchoolResearcher(Faculty faculty) {
        int maxCitations = 0;
        Researcher topCitedResearcher = new Researcher();
        Vector<ResearchJournal> journals =  Database.getInstance().getResearchJournals();
        for(ResearchJournal journal : journals) {
            Vector<ResearchPaper> rp = journal.getResearchPapers();
            for (ResearchPaper paper : rp) {
                String pseudoname = paper.getAuthor().getPceudoname();
                int citations = paper.getCitations().size();
                if (pseudoname.equals(faculty)) {
                    if (citations > maxCitations) {
                        maxCitations = citations;
                        topCitedResearcher = paper.getAuthor();
                    }
                }
            }
        }
        System.out.println(topCitedResearcher);
    }

    public void topCitedResearcher() {
        int maxCitations = 0;
        Researcher topCitedResearcher = new Researcher();
        Vector<ResearchJournal> journals =  Database.getInstance().getResearchJournals();
        for(ResearchJournal journal : journals) {
            Vector<ResearchPaper> rp = journal.getResearchPapers();
            for (ResearchPaper paper : rp) {
                String pseudoname = paper.getAuthor().getPceudoname();
                int citations = paper.getCitations().size();
                if (citations > maxCitations) {
                    maxCitations = citations;
                    topCitedResearcher = paper.getAuthor();
                }
            }
        }
        System.out.println(topCitedResearcher);
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
