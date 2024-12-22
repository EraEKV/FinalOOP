package Research;

import java.util.Date;
import java.util.Objects;
import java.util.Vector;

import Database.Database;
import Enums.CitationFormat;

public class ResearchPaper {
    private String name;
    private int pages;
    private Researcher mainAuthor;
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

    public ResearchPaper(String name, String journalName) {
        this.name = name;

        Vector<ResearchJournal> journals = Database.getInstance().getResearchJournals();

        ResearchJournal foundJournal = null;

        for (ResearchJournal journal : journals) {
            if (journal.getName().equals(journalName)) {
                foundJournal = journal;
                break;
            }
        }

        if (foundJournal != null) {
            this.researchJournal = foundJournal;
        } else {
            throw new IllegalArgumentException("Journal not found in the database: " + journalName);
        }
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

    public Researcher getMainAuthor() {
        return mainAuthor;
    }

    public void setMainAuthor(Researcher mainAuthor) {
        this.mainAuthor = mainAuthor;
    }

    public String getCitation(CitationFormat format) {
        StringBuilder citation = new StringBuilder();

        switch (format) {
            case PLAIN_TEXT:
                citation.append("Title: ").append(name).append("\n")
                        .append("Authors: ");
                for (int i = 0; i < authors.size(); i++) {
                    citation.append(authors.get(i));
                    if (i < authors.size() - 1) citation.append(", ");
                }
                citation.append("\n")
                        .append("Journal: ").append(researchJournal.getName()).append("\n")
                        .append("DOI: ").append(DOI).append("\n")
                        .append("Date: ").append(date.toString()).append("\n")
                        .append("Pages: ").append(pages);
                break;

            case BIBTEX:
                citation.append("@article{").append(DOI.replaceAll("[^a-zA-Z0-9]", "")).append(",\n") // Use DOI as the citation key
                        .append("  mainAuthor = {");
                for (int i = 0; i < authors.size(); i++) {
                    citation.append(authors.get(i));
                    if (i < authors.size() - 1) citation.append(" and ");
                }
                citation.append("},\n")
                        .append("  title = {").append(name).append("},\n")
                        .append("  journal = {").append(researchJournal.getName()).append("},\n")
                        .append("  year = {").append(date.getYear() + 1900).append("},\n")
                        .append("  pages = {").append(pages).append("},\n")
                        .append("  doi = {").append(DOI).append("}\n")
                        .append("}");
                break;

            default:
                throw new IllegalArgumentException("Unsupported citation format: " + format);
        }

        return citation.toString();
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
