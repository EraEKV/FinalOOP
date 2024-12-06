package Research;

import Users.Researcher;

import java.util.*;

public class ResearchProject {
	private String title;
	private Date dateOfWritting;
	private Date publishDate;
	private Vector<Researcher> authors;
	private Vector<ResearchPaper> papers;
	private Researcher superVisor;

	public ResearchProject() {
	}

	public ResearchProject(String title, Date dateOfWritting, Date publishDate, Vector<Researcher> authors, Vector<ResearchPaper> papers, Researcher superVisor) {
		this.title = title;
		this.dateOfWritting = dateOfWritting;
		this.publishDate = publishDate;
		this.authors = authors;
		this.papers = papers;
		this.superVisor = superVisor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateOfWritting() {
		return dateOfWritting;
	}

	public void setDateOfWritting(Date dateOfWritting) {
		this.dateOfWritting = dateOfWritting;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Vector<Researcher> getAuthors() {
		return authors;
	}

	public void setAuthors(Vector<Researcher> authors) {
		this.authors = authors;
	}

	public Vector<ResearchPaper> getPapers() {
		return papers;
	}

	public void setPapers(Vector<ResearchPaper> papers) {
		this.papers = papers;
	}

	public Researcher getSuperVisor() {
		return superVisor;
	}

	public void setSuperVisor(Researcher superVisor) {
		this.superVisor = superVisor;
	}

	@Override
	public String toString() {
		return "title= " + title + ", dateOfWritting=" + dateOfWritting + ", publishDate=" + publishDate + ", authors=" + authors + ", papers=" + papers + ", superVisor=" + superVisor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ResearchProject r = (ResearchProject) o;

		if (title != r.getTitle()) return false;
		if (dateOfWritting != r.dateOfWritting) return false;
		if (publishDate != r.getPublishDate()) return false;
		if (authors != r.getAuthors()) return false;
		if (papers != r.getPapers()) return false;
		return superVisor == r.getSuperVisor();
	}

	@Override
	public int hashCode() {
		int res = title != null ? title.hashCode() : 0;
		res = 31 * res + (dateOfWritting != null ? dateOfWritting.hashCode() : 0);
		res = 31 * res + (publishDate != null ? publishDate.hashCode() : 0);
		res = 31 * res + (authors != null ? authors.hashCode() : 0);
		res = 31 * res + (papers != null ? papers.hashCode() : 0);
		res = 31 * res + (superVisor != null ? superVisor.hashCode() : 0);
		return res;
	}
}
