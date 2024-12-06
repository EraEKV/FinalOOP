package Research;

import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import Enums.CitationFormat;
import Users.Researcher;

public class ResearchPaper {
    private String name;
    private int pages;
    private ResearchJournal researchJournal;
    private Vector<ResearchPaper> citations;
    private String DOI;
    private Date date;
    private Vector<Researcher> authors;
    {
        this.citations = new Vector<>();
        this.authors = new Vector<>();
    }
    public ResearchPaper() {
    }

    public ResearchPaper(String name, int pages, ResearchJournal researchJournal, String DOI, Date date) {
        this.name = name;
        this.pages = pages;
        this.researchJournal = researchJournal;
        this.DOI = DOI;
        this.date = date;
    }

    public ResearchPaper(String name, int pages, String DOI, Date date) {
        this.name = name;
        this.pages = pages;
        this.DOI = DOI;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ResearchJournal getResearchJournal() {
        return researchJournal;
    }

    public void setResearchJournal(ResearchJournal researchJournal) {
        this.researchJournal = researchJournal;
    }

    public Vector<ResearchPaper> getCitations() {
        return citations;
    }

    public void setCitations(Vector<ResearchPaper> citations) {
        this.citations = citations;
    }

    public String getDOI() {
        return DOI;
    }

    public void setDOI(String DOI) {
        this.DOI = DOI;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Vector<Researcher> getAuthors() {
        return authors;
    }

    public void setAuthors(Vector<Researcher> authors) {
        this.authors = authors;
    }

    public String getCitation(CitationFormat format) {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResearchPaper that = (ResearchPaper) o;
        return pages == that.pages && Objects.equals(name, that.name) && Objects.equals(researchJournal, that.researchJournal) && Objects.equals(citations, that.citations) && Objects.equals(DOI, that.DOI) && Objects.equals(date, that.date) && Objects.equals(authors, that.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pages, researchJournal, citations, DOI, date, authors);
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                ", researchJournal=" + researchJournal +
                ", citations=" + citations +
                ", DOI='" + DOI + '\'' +
                ", date=" + date +
                ", authors=" + authors +
                '}';
    }
}
