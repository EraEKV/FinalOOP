package Research;

import java.util.Date;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResearchPaper r = (ResearchPaper) o;

        if (pages != r.pages) return false;

        if (name != null) {
            if (!name.equals(r.getName())) return false;
        } else if (r.getName() != null) {
            return false;
        }

        if (researchJournal != null) {
            if (!researchJournal.equals(r.getResearchJournal())) return false;
        } else if (r.getResearchJournal() != null) {
            return false;
        }

        if (citations != null) {
            if (!citations.equals(r.getCitations())) return false;
        } else if (r.getCitations() != null) {
            return false;
        }

        if (DOI != null) {
            if (!DOI.equals(r.getDOI())) return false;
        } else if (r.getDOI() != null) {
            return false;
        }

        if (date != null) {
            if (!date.equals(r.getDate())) return false;
        } else if (r.getDate() != null) {
            return false;
        }

        if (authors != null) {
            if (!authors.equals(r.getAuthors())) return false;
        } else if (r.getAuthors() != null) {
            return false;
        }

        return true;
    }



    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pages;
        result = 31 * result + (researchJournal != null ? researchJournal.hashCode() : 0);
        result = 31 * result + (citations != null ? citations.hashCode() : 0);
        result = 31 * result + (DOI != null ? DOI.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResearchPaper = " + "name= " + name   + ", pages=" + pages + ", researchJournal=" + researchJournal + ", citations=" + citations + ", DOI= " + DOI + ", date=" + date + ", authors=" + authors;
    }
}
